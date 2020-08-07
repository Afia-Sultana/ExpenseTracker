package com.example.expensetracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomTransactionList extends BaseAdapter
{
    private Activity context;
    ArrayList<TransactionData> ctl;


    public CustomTransactionList(Activity context, ArrayList ctl) {
        // super(context, R.layout.row_item, countries);
        this.context = context;
        this.ctl = ctl;

    }
    public static class ViewHolder
    {
        TextView textViewId;
        TextView textViewamount;
        TextView textViewcatagory;
        TextView textViewdate;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;

        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if(convertView==null) {
            vh=new ViewHolder();
            row = inflater.inflate(R.layout.row_item, null, true);
            vh.textViewId = (TextView) row.findViewById(R.id.id);
            vh.textViewamount= (TextView) row.findViewById(R.id.amounttextview);
            vh.textViewcatagory= (TextView) row.findViewById(R.id.catagorytextview);
            vh.textViewdate= (TextView) row.findViewById(R.id.datetextview);
            // store the holder with the view.
            row.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        String nid=String.valueOf(ctl.get(position).getId());
        vh.textViewId.setText(nid);
        vh.textViewamount.setText("Rs."+ctl.get(position).getamount());
        vh.textViewcatagory.setText(ctl.get(position).getcatagory());
        vh.textViewdate.setText(ctl.get(position).getdate());

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

