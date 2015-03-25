package com.codepath.kivaapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by edmund_ye on 3/18/15.
 */
@ParseClassName("Loan")
public class Loan extends ParseObject implements Parcelable {
    private int kivaId;
    private String name;
    private String use;
    private String postedDate;
    private String imageUrl;
    // liked will take on 3 values. 0 = Hasn't been determined, 1 = "liked", 2 = "disliked"
    private int liked;

    public Loan(){super();}

    public static Loan fromJSON (JSONObject json) {
        Loan j = new Loan();
        try {
            j.setKivaId(json.getInt("id"));
            j.setName(json.getString("name"));
            j.setUse(json.getString("use"));
            j.setPostedDate(json.getString("posted_date"));
            j.setLike(0);
            if (json.has("image")) {
                j.imageUrl = "http://www.kiva.org/img/s100/" + json.getJSONObject("image").getInt("id") + ".jpg";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }

    public String getName() {
        return name;
    }

    public String getUse() {
        return use;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public int getKivaId() {
        return kivaId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static ArrayList<Loan> fromJSONArray (JSONArray array) {
        ArrayList<Loan> results = new ArrayList<Loan>();
        for(int i = 0; i < array.length(); i++) {
            try {
                results.add(fromJSON(array.getJSONObject(i)));
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(kivaId);
        dest.writeStringArray(new String[]{name, use, postedDate, imageUrl});
    }

    public void setName(String n) {
        put("name", n);
        name = n;
    }

    public void setUse(String u){
        put("use", u);
        use = u;
    }

    public void setPostedDate(String d){
        put("postedDate", d);
        postedDate = d;
    }

    public void setKivaId(int id) {
        put("kivaId", kivaId);
        kivaId = id;
    }

    private void setLike(int likeValue){
        put("liked", likeValue);
        liked = likeValue;
    }

    public void setLiked(){ setLike(1); }

    public void setDisliked(){
        setLike(2);
    }

    public int getLiked() {
        return liked;
    }
}