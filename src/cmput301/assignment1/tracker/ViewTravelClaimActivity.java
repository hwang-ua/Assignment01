package cmput301.assignment1.tracker;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTravelClaimActivity extends Activity {

	TClaim tc = ClaimIndex.get_tc();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_travel_claim);
		if (tc.has_content()) {
			
			// show all information on screen
			TextView des = (TextView) findViewById(R.id.viewTcDesSpace);
			TextView start = (TextView) findViewById(R.id.viewStartSpace);
			TextView end = (TextView) findViewById(R.id.viewEndSpace);
			TextView spend = (TextView) findViewById(R.id.viewTotalCurrencySpace);
			
			des.setText(tc.get_description());
			start.setText(tc.get_start_date());
			end.setText(tc.get_end_date());
			spend.setText(tc.total_expense());
		}
		
		if (tc.get_status()=="Approved") {
			Toast.makeText(this, "this claim is approved, \n" +
					"no change will be allowed", Toast.LENGTH_LONG).show();
			Button button = (Button) findViewById(R.id.ReturnClaimButton);
			button.setText("unable to return");
		}
		
		// initialize manager
		TClaimListManager.initManager(this.getApplicationContext());
		
		// get access to expense item list view
		ListView listView = (ListView) findViewById(R.id.viewEIList);

		// get the expense item list and show it on screen
		Collection<EItem> eis = TClaimListController.getTClaimList().getCurrentTClaim().get_eiList();
		final ArrayList<EItem> list = new ArrayList<EItem>(eis);
		final ArrayAdapter<EItem> eiAdapter = new ArrayAdapter<EItem>(this, android.R.layout.simple_list_item_1, list);
		listView.setAdapter(eiAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_travel_claim, menu);
		return true;
	}
	
	// This method is called when "Return" button is clicked
	public void returnTClaim(View v) {
		if (tc.get_status()=="Approved") {
			Toast.makeText(this, "this claim cannot be reurned", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "this claim is returned, \n" +
					"editing is allowed now", Toast.LENGTH_LONG).show();
			tc.change_status("Returned");
			Intent intent = new Intent(ViewTravelClaimActivity.this, TravelClaimActivity.class);
			startActivity(intent);
		}
	}

	public void approveTClaim(View v) {
		Toast.makeText(this, "this claim is approved, \n" +
				"no change will be allowed", Toast.LENGTH_LONG).show();
		tc.change_status("Approved");
		Button button = (Button) findViewById(R.id.ReturnClaimButton);
		button.setText("unable to return");
	}
	
	public void backToList(View v) {
		Toast.makeText(this, "back to main page", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(ViewTravelClaimActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
	// This method is called when "email this claim" menu is clicked
	public void emailTClaim(MenuItem menu) {
		Toast.makeText(this, "prepare email page", Toast.LENGTH_SHORT).show();
		// transfer to email page
		Intent intent = new Intent(ViewTravelClaimActivity.this, EmailTClaimActivity.class);
		startActivity(intent);
	}
}
