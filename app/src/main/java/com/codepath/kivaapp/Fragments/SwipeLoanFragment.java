package com.codepath.kivaapp.Fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.kivaapp.Models.Loan;
import com.codepath.kivaapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by edmund_ye on 3/18/15.
 */
public class SwipeLoanFragment extends Fragment {
    private ImageView ivLoan;
    private TextView tvName;
    private TextView tvUse;
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
        tvUse = (TextView) v.findViewById(R.id.tvUse);
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
        tvUse.setText(currentLoan.getUse());
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