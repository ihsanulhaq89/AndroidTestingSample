package com.app.myapplication.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.myapplication.R;
import com.app.myapplication.util.MyConstants;

import java.util.List;


public class SampleAdapter extends RecyclerView.Adapter {


    private final Context mContext;
    private List<String> mItems;
    private boolean grid;

    public SampleAdapter(Context context, List<String> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View v = LayoutInflater.from(mContext)
                    .inflate(R.layout.row, viewGroup, false);
            return new MyViewHolder(mContext, v);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((MyViewHolder) viewHolder).invalidate(mItems.get(i));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<String> items) {
        mItems = items;
    }

    public List<String> getItems() {
        return mItems;
    }

    public void setGrid(boolean grid) {
        this.grid = grid;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private String data;
        public MyViewHolder(Context mContext, View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            v.setOnClickListener(this);
        }

        public void invalidate(String s) {
            title.setText(s);
            data = s;
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString(MyConstants.B_DATA, data);
            Intent intent = new Intent(MyConstants.BROADCAST_SELECTION);
            intent.putExtras(bundle);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }
    }
}
