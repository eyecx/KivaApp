package com.codepath.kivaapp.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.kivaapp.Adapters.SwipePagerAdapter;
import com.codepath.kivaapp.Models.Loan;
import com.codepath.kivaapp.R;

import java.util.ArrayList;

/**
 * Created by edmund_ye on 3/18/15.
 */
public class SwipeLoansWrapperFragment extends Fragment{

    private int currentLoan;
    private ArrayList<Loan> loans;

    public static SwipeLoansWrapperFragment newInstance(ArrayList<Loan> loans, int pos){
        SwipeLoansWrapperFragment f = new SwipeLoansWrapperFragment();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putParcelableArrayList("loans", loans);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        loans = (ArrayList<Loan>)(ArrayList<?>) getArguments().getParcelableArrayList("loans");
        currentLoan = getArguments().getInt("pos");
        View v = inflater.inflate(R.layout.fragment_swipe_loans_wrapper, parent, false);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.vpSwipe);
        viewPager.setAdapter(new SwipePagerAdapter(getActivity().getSupportFragmentManager(), loans));
        viewPager.setCurrentItem(currentLoan);

        return v;
    }
}
