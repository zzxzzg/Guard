package com.guard.pinnedlistdemo;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guard.pinnedlistlib.pinnedlist.CompositeBaseAdapter;
import com.guard.pinnedlistlib.pinnedlist.PinnedHeaderBaseAdapter;
import com.guard.pinnedlistlib.pinnedlist.PinnedHeaderListView;

public class MainActivity extends Activity {

	PinnedHeaderListView mPinnedHeaderListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPinnedHeaderListView=(PinnedHeaderListView) findViewById(R.id.lv);
		for(int i=0;i<10;i++){
			CompositeBaseAdapter.Partition partition=new CompositeBaseAdapter.Partition(true, true);
			List<Object> object=new ArrayList<Object>();
			for(int j=0;j<10-i;j++){
				object.add(j+"");
			}
			partition.mObject=object;
			adapter.addPartition(partition);
		}
		
		mPinnedHeaderListView.setAdapter(adapter);
	}
	
	private MyAdapter adapter=new MyAdapter(this);
	
	public class MyAdapter extends PinnedHeaderBaseAdapter {

		public MyAdapter(Context context, int initialCapacity) {
			super(context, initialCapacity);
			setPinnedPartitionHeadersEnabled(true);
		}

		public MyAdapter(Context context) {
			super(context);
			setPinnedPartitionHeadersEnabled(true);
		}

		@Override
		protected View newView(Context context, int partition,
				List<Object> objects, int position, ViewGroup parent) {
			LayoutInflater inflater=LayoutInflater.from(context);
			
			return inflater.inflate(R.layout.list_item, null);
		}

		@Override
		protected void bindView(View v, int partition, List<Object> objects,
				int position) {
			if(v!=null){
			TextView tv=(TextView) v.findViewById(R.id.item);
			tv.setText((String)objects.get(position));		
			}
		}
		
		@Override
		protected View newHeaderView(Context context, int partition,
				List<Object> objects, ViewGroup parent) {
			LayoutInflater inflater=LayoutInflater.from(context);
			return inflater.inflate(R.layout.list_header, null);
		}
		
		@Override
		protected void bindHeaderView(View view, int partition,
				List<Object> objects) {
			if(view !=null){
			TextView tv=(TextView) view.findViewById(R.id.header);
			tv.setText(partition+"");
			}
		}
		
	}

}
