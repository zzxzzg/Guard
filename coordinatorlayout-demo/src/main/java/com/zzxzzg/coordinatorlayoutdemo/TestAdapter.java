package com.zzxzzg.coordinatorlayoutdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by yxwang on 16/6/12.
 */
public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    public TestAdapter(Context context){
        mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).tv.setText(position+"");
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(android.R.id.text1);
        }
    }


}
