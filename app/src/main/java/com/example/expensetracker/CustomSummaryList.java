package com.example.expensetracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomSummaryList extends BaseAdapter {
    private Activity context;
    ArrayList<TransactionData> ctl;


    public CustomSummaryList(Activity context, ArrayList ctl) {
        // super(context, R.layout.row_item, countries);
        this.context = context;
        this.ctl = ctl;

    }
    public static class ViewHolder
    {

        TextView textViewamount;
        TextView textViewcatagory;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;

        LayoutInflater inflater = context.getLayoutInflater();
        CustomSummaryList.ViewHolder vh;
        if(convertView==null) {
            vh=new CustomSummaryList.ViewHolder();
            row = inflater.inflate(R.layout.summary_row, null, true);

            vh.textViewamount= (TextView) row.findViewById(R.id.summaryamount);
            vh.textViewcatagory= (TextView) row.findViewById(R.id.summarycategory);

            // store the holder with the view.
            row.setTag(vh);
        }
        else {
            vh = (CustomSummaryList.ViewHolder) convertView.getTag();
        }


        vh.textViewamount.setText("Rs."+ctl.get(position).getamount());
        vh.textViewcatagory.setText(ctl.get(position).getcatagory());

        return  row;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {

        if(ctl.size()<=0)
            return 1;
        return ctl.size();
    }
}