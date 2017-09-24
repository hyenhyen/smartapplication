package com.monad.kpu.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.monad.kpu.Model.ResultScoreModel;
import com.monad.kpu.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by temp on 2017. 5. 19..
 */

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ViewHolder> {
    private ArrayList<ResultScoreModel> mDataset;
    private static ClickListener clickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string in this case
        public TextView title, my_score, difference, diagnose, content;
        public LinearLayout temp;

        public ViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            title = (TextView)view.findViewById(R.id.item_title);
            my_score = (TextView)view.findViewById(R.id.item_my_score);
            difference = (TextView)view.findViewById(R.id.item_difference);
            diagnose = (TextView)view.findViewById(R.id.item_diagnose);
            content = (TextView)view.findViewById(R.id.item_content);
            temp = (LinearLayout) view.findViewById(R.id.temp);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public ResultRecyclerAdapter(ArrayList<ResultScoreModel> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ResultRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.difference.setText(""+mDataset.get(position).getDifference());
        holder.diagnose.setText(mDataset.get(position).getDiagnose());
        holder.my_score.setText(""+mDataset.get(position).getMyScore());
        holder.content.setText(mDataset.get(position).getContent());

        if(position % 2 == 0) {
            holder.temp.setBackgroundColor(Color.parseColor("#F4F4F4"));
            holder.title.setBackgroundColor(Color.parseColor("#F4F4F4"));
            holder.difference.setBackgroundColor(Color.parseColor("#F4F4F4"));
            holder.diagnose.setBackgroundColor(Color.parseColor("#F4F4F4"));
            holder.my_score.setBackgroundColor(Color.parseColor("#F4F4F4"));
            holder.content.setBackgroundColor(Color.parseColor("#F4F4F4"));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ResultRecyclerAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
