package com.codepath.kivaapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.kivaapp.Fragments.SwipeLoanFragment;
import com.codepath.kivaapp.Models.Loan;

import java.util.ArrayList;

/**
 * Created by edmund_ye on 3/18/15.
 */
public class SwipePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Loan> loans;

    public SwipePagerAdapter(FragmentManager fm, ArrayList<Loan> l) {
        super(fm);
        loans = l;
    }

    @Override
    public Fragment getItem(int position) {
        return SwipeLoanFragment.newInstance(loans.get(position));
    }

    @Override
    public int getCount() {
        return loans.size();
    }
}