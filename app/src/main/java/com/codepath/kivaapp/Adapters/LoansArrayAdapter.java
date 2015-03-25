package com.codepath.kivaapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.kivaapp.Models.Loan;
import com.codepath.kivaapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by edmund_ye on 3/18/15.
 */
public class LoansArrayAdapter extends ArrayAdapter<Loan> {
    private static class ViewHolder {
        ImageView ivLoan;
        TextView tvName;
        TextView tvUse;
    }
    public LoansArrayAdapter(Context context, List<Loan> loans) {
        super(context, R.layout.item_loan, loans);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Loan loan = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_loan, parent, false);
            viewHolder.ivLoan = (ImageView) convertView.findViewById(R.id.ivLoan);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvUse = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(loan.getName());
        viewHolder.tvUse.setText(loan.getUse());
        viewHolder.ivLoan.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(loan.getImageUrl()).into(viewHolder.ivLoan);
        return convertView;
    }
}