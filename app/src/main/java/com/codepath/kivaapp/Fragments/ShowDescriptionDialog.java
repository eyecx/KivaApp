package com.codepath.kivaapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.codepath.kivaapp.R;

/**
 * Created by eyecx on 3/25/15.
 */
public class ShowDescriptionDialog extends DialogFragment {
    public static ShowDescriptionDialog newInstance(String description) {
        ShowDescriptionDialog frag = new ShowDescriptionDialog();
        Bundle args = new Bundle();
        args.putString("desc", description);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.fragment_show_description, container);
        TextView tvDescription = (TextView) v.findViewById(R.id.tvDescription);
        String description = getArguments().getString("desc");
        tvDescription.setText(description);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return v;

    }
}
