package com.frenzdeveloper.manajerkeuangan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.frenzdeveloper.manajerkeuangan.R;
import com.frenzdeveloper.manajerkeuangan.database.entity.Report;
import com.frenzdeveloper.manajerkeuangan.model.ReportModel;

import java.util.List;

public class ReportAdapter extends BaseAdapter {

    private Activity activity;
    List<Report> report;
    LayoutInflater inflater;

    public ReportAdapter(Activity activity, List<Report> report) {
        this.activity = activity;
        this.report = report;
    }

    @Override
    public int getCount() {
        return report.size();
    }

    @Override
    public Object getItem(int i) {
        return report.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (view == null && inflater != null){
            view = inflater.inflate(R.layout.list_uang, null);
        }

        if (view != null){
            //TextView tvCategory = view.findViewById(R.id.tvCategory);
            TextView tvDate = view.findViewById(R.id.tvDate);
            TextView tvNote = view.findViewById(R.id.tvNote);
            TextView tvIncome = view.findViewById(R.id.tvIncome);
            TextView tvOutcome = view.findViewById(R.id.tvOutcome);


            //tvCategory.setText("Category");
            tvDate.setText(report.get(i).date+"");
            tvNote.setText(report.get(i).note+"");

            String income = report.get(i).income+"";
            String outcome = report.get(i).outcome+"";

            if (report.get(i).income == 0){
                tvIncome.setVisibility(View.GONE);
            }else{
                tvIncome.setText("+ Rp. "+income+"");
            }

            if (report.get(i).outcome == 0){
                tvOutcome.setVisibility(View.GONE);
            }else{
                tvOutcome.setText("- Rp. "+outcome+"");
            }
        }

        return view;
    }
}
