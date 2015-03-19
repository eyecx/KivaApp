package com.codepath.kivaapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.kivaapp.Adapters.LoansArrayAdapter;
import com.codepath.kivaapp.Clients.KivaClient;
import com.codepath.kivaapp.Listeners.EndlessScrollListener;
import com.codepath.kivaapp.Models.Loan;
import com.codepath.kivaapp.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by edmund_ye on 3/18/15.
 */
public class ListLoanFragment extends Fragment {
    private ArrayList<Loan> loans;
    private LoansArrayAdapter aLoans;
    private ListView lvLoans;
    private ListLoanFragmentListener mCallback;
    private KivaClient kc;

    public interface ListLoanFragmentListener {
        public void listLoanClicked(ArrayList<Loan> loans, int pos);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Fragment parent = getParentFragment();

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ListLoanFragmentListener) parent;
        } catch (ClassCastException e) {
            throw new ClassCastException(parent.toString()
                    + " must implement ListLoanFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loans = (ArrayList<Loan>)(ArrayList<?>) getArguments().getParcelableArrayList("loans");
        aLoans = new LoansArrayAdapter(getActivity(), loans);
        kc = new KivaClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_loan, parent, false);
        lvLoans = (ListView) v.findViewById(R.id.lvLoans);
        lvLoans.setAdapter(aLoans);
        lvLoans.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemCount) {
                loadMoreLoans(page+1);
            }
        });
        setupListViewListener();
        return v;
    }

    private void loadMoreLoans(int page) {
        kc.getLoansSearch(page, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    loans.addAll(Loan.fromJSONArray(response.getJSONArray("loans")));
                    aLoans.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupListViewListener() {
        lvLoans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.listLoanClicked(loans, position);
            }
        });
    }

}