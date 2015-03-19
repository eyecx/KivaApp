package com.codepath.kivaapp.Applications;

import android.app.Application;

import com.codepath.kivaapp.Models.Loan;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by edmund_ye on 3/18/15.
 */
public class ParseApplication extends Application {
    public static final String APPLICATION_ID = "DLv6g4Japg8Z862X4DwjaLcCFa2lWHB7VVoagpG4";
    public static final String CLIENT_KEY = "4Xryg7ZxMvF7eeb8Yi846CMdbrDmGJlwuBxtgcX2";

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Loan.class);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
    }
}
