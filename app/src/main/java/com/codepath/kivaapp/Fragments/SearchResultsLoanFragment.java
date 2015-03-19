package com.codepath.kivaapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.kivaapp.Models.Loan;
import com.codepath.kivaapp.R;

import java.util.ArrayList;

/**
 * Created by edmund_ye on 3/18/15.
 */
public class SearchResultsLoanFragment extends Fragment implements ListLoanFragment.ListLoanFragmentListener {

    private ArrayList<Loan> loans;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loans = (ArrayList<Loan>)(ArrayList<?>) getArguments().getParcelableArrayList("loans");
        startListLoanFragment(loans);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_results_loan, parent, false);
    }

    private void startListLoanFragment(ArrayList<Loan> loans) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ListLoanFragment f = new ListLoanFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("loans", loans);
        f.setArguments(args);
        ft.replace(R.id.flLoans, f);
        ft.commit();
    }

    private void startSwipeLoanFragment(ArrayList<Loan> loans, int pos) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        SwipeLoansWrapperFragment f = SwipeLoansWrapperFragment.newInstance(loans, pos);
        ft.replace(R.id.flLoans, f).commit();
    }

    @Override
    public void listLoanClicked(ArrayList<Loan> newLoans, int pos) {
        loans = newLoans;
        startSwipeLoanFragment(loans, pos);
    }
}
