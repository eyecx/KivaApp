package com.codepath.kivaapp.Fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.kivaapp.Clients.KivaClient;
import com.codepath.kivaapp.Models.Loan;
import com.codepath.kivaapp.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by edmund_ye on 3/18/15.
 */
public class SwipeLoanFragment extends Fragment {
    private ImageView ivLoan;
    private TextView tvName;
    private TextView tvDescription;
    private ImageView ivThumbsUp;
    private ImageView ivThumbsDown;
    private Loan currentLoan;

    public static SwipeLoanFragment newInstance(Loan loan){
        SwipeLoanFragment f = new SwipeLoanFragment();
        Bundle args = new Bundle();
        args.putParcelable("loan", loan);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_swipe_loan, parent, false);
        currentLoan = getArguments().getParcelable("loan");
        ivLoan = (ImageView) v.findViewById(R.id.ivLoan);
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvDescription = (TextView) v.findViewById(R.id.tvDescription);
        ivThumbsUp = (ImageView) v.findViewById(R.id.ivThumbsUp);
        ivThumbsDown = (ImageView) v.findViewById(R.id.ivThumbsDown);
        setLoanView(v);

        ivThumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onThumbsUp(v);
            }
        });
        ivThumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onThumbsDown(v);
            }
        });
        return v;
    }

    private void setLoanView(View v) {
        Picasso.with(v.getContext()).load(currentLoan.getImageUrl()).into(ivLoan);
        tvName.setText(currentLoan.getName());
        KivaClient kc = new KivaClient();
        kc.getLoanDescription(currentLoan.getKivaId(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject texts = response.getJSONArray("loans").getJSONObject(0).getJSONObject("description").getJSONObject("texts");
                    if (texts.has("en")){
                        final String desc = texts.getString("en");
                        tvDescription.setText(desc);
                        tvDescription.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                ShowDescriptionDialog d = ShowDescriptionDialog.newInstance(desc);
                                d.show(fm, "fragment_show_description");
                            }
                        });
                    } else {
                        tvDescription.setText(currentLoan.getUse());
                        }
                } catch (JSONException e) {}}
        });
        setThumbsColors();
    }

    private void setThumbsColors(){
        if (currentLoan.getLiked() == 1){
            ivThumbsUp.getDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
            ivThumbsDown.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        } else if (currentLoan.getLiked() == 2){
            ivThumbsUp.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            ivThumbsDown.getDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        } else {
            ivThumbsUp.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            ivThumbsDown.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        }
    }

    public void onThumbsUp(View v) {
        currentLoan.setLiked();
        currentLoan.saveInBackground();
        setThumbsColors();
    }

    public void onThumbsDown(View v) {
        currentLoan.setDisliked();
        currentLoan.saveInBackground();
        setThumbsColors();
    }

}