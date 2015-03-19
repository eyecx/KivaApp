package com.codepath.kivaapp.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.kivaapp.Fragments.SearchResultsLoanFragment;
import com.codepath.kivaapp.Clients.KivaClient;
import com.codepath.kivaapp.Models.Loan;
import com.codepath.kivaapp.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private SearchView svQuery;
    private KivaClient kc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        svQuery = (SearchView) findViewById(R.id.svQuery);
        svQuery.setQueryHint("Type in a query!");
        kc = new KivaClient();
        svQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override

            // Called when a user submits a new query
            public boolean onQueryTextSubmit(String query) {
                // Call the search api
                kc.setQuery(query);
                kc.getLoansSearch(1, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            // Read response and start new loan fragment
                            startSearchResultsLoanFragment(Loan.fromJSONArray(response.getJSONArray("loans")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void onProfileView(MenuItem mi) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    private void startSearchResultsLoanFragment(ArrayList<Loan> loans) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SearchResultsLoanFragment f = new SearchResultsLoanFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("loans", loans);
        f.setArguments(args);
        ft.replace(R.id.flLoan, f);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}