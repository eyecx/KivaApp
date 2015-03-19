package com.codepath.kivaapp.Activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.codepath.kivaapp.Adapters.LoansArrayAdapter;
import com.codepath.kivaapp.Models.Loan;
import com.codepath.kivaapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends ActionBarActivity {

    ListView lvLikedLoans;
    ArrayList<Loan> loans;
    LoansArrayAdapter aLoans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        lvLikedLoans = (ListView) findViewById(R.id.lvLoans);
        getLikedLoans();
    }

    public void getLikedLoans() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Loan");
        query.whereEqualTo("liked", 1);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    loansWereRetrievedSuccessfully(objects);
                } else {
                    assert true;
                }
            }
        });
    }

    public void loansWereRetrievedSuccessfully (List<ParseObject> objects){
        loans = (ArrayList<Loan>)(ArrayList<?>) objects;
        aLoans = new LoansArrayAdapter(this, loans);
        lvLikedLoans.setAdapter(aLoans);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
