package com.codepath.kivaapp.Clients;

import android.net.Uri;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by edmund_ye on 3/18/15.
 */
public class KivaClient extends AsyncHttpClient {

    private static String query;

    public static void setQuery(String query) {
        KivaClient.query = query;
    }

    public void getLoansSearch(int page, JsonHttpResponseHandler h) {
        String queryUrl = "http://api.kivaws.org/v1/loans/search.json";
        Uri.Builder builder = Uri.parse(queryUrl).buildUpon();
        builder.appendQueryParameter("q", query);
        builder.appendQueryParameter("page", Integer.toString(page));
        String finalUrl = builder.build().toString();
        get(finalUrl, null, h);
    }
}
