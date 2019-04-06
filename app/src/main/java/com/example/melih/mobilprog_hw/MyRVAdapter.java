package com.example.melih.mobilprog_hw;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.MyViewHolder> {
    private String[] mDataset;
    private LayoutInflater mInflater;
    private static RecyclerViewClickListener itemListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.tvLessons);
            textView.setOnClickListener(this);
        }

        public void onClick(View v){
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }

    public MyRVAdapter(Context context, String[] myDataset, RecyclerViewClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        mDataset = myDataset;
        itemListener=listener;
    }

    public MyRVAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  mInflater.inflate(R.layout.my_textview_for_rv, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(mDataset[position]);
    }

    public int getItemCount() {
        return mDataset.length;
    }


}
