package com.guard.recyclerview.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecycleView;

    class DataInfo{
        public DataInfo(String string,Class<?> mclass){
            this.name=string;
            this.mclass=mclass;
        }
        String name;
        Class<?> mclass;
    }

    List<DataInfo> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleView= (RecyclerView) findViewById(R.id.recycleview);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        data.add(new DataInfo("LinearLayoutManager ItemDecoration",LinerDecoration.class));
        MyRecyclerAdapter adapter=new MyRecyclerAdapter();
        adapter.setOnItemOptionListener(new OnItemOptionListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent=new Intent(MainActivity.this,data.get(pos).mclass);
                startActivity(intent);
            }
        });
        mRecycleView.setAdapter(adapter);

        mRecycleView.addItemDecoration(new );
    }


    interface OnItemOptionListener{
        void onItemClick(View v,int pos);
    }

    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
        private OnItemOptionListener mOnItemOptionListener;
        public void setOnItemOptionListener(OnItemOptionListener listener){
            mOnItemOptionListener=listener;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1,null);
            MyViewHolder holder=new MyViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemOptionListener!=null){
                        mOnItemOptionListener.onItemClick(v,mRecycleView.getChildAdapterPosition(v));
                    }
                }
            });
            return holder;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.text.setText(data.get(position).name);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            final TextView text;

            MyViewHolder(View view) {
                super(view);
                text = (TextView) view.findViewById(android.R.id.text1);
            }
        }
    }
}
