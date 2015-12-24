package com.guard.pinnedlistdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guard.pinnedlistlib.pinnedlist.pinnedexpendablelist.AnimationExpandableListView;
import com.guard.pinnedlistlib.pinnedlist.pinnedexpendablelist.PinnedHeaderExpandableListView;
import com.guard.pinnedlistlib.pinnedlist.pinnedexpendablelist.StickyLayout;

public class PinnedExpendableActivity extends Activity {
	PinnedHeaderExpandableListView mExpandableListView;
	MyAnimatorAdapter mMyExpandableAdapter;
	private StickyLayout stickyLayout;
	String []group=new String[]{"1","2","3","4","5"};
	String [][]child=new String[][]{{"a","b","c","d","e","f","g","h","i","j","k","l"},
			{"m","o","p","q","r","s","t","u","v","w","x","y","z"},
			{"a","b","c","d","e","f","g","h","i","j","k","l"},
			{"a","b","c","d","e","f","g","h","i","j","k","l"},
			{"a","b","c","d","e","f","g","h","i","j","k","l"}};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pinned_expendable_activity);
		mExpandableListView=(PinnedHeaderExpandableListView) findViewById(R.id.expandablelist);
		mMyExpandableAdapter=new MyAnimatorAdapter();
		mExpandableListView.setAdapter(mMyExpandableAdapter);

		stickyLayout = (StickyLayout)findViewById(R.id.sticky_layout);

		mExpandableListView.setOnHeaderUpdateListener(new PinnedHeaderExpandableListView.OnHeaderUpdateListener() {
			@Override
			public View getPinnedHeader() {
				View headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.list_header, null);
				headerView.setLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				((ViewGroup)headerView).getChildAt(0).setBackgroundColor(Color.BLUE);
				return headerView;
			}

			@Override
			public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
				String str=(String)mMyExpandableAdapter.getGroup(firstVisibleGroupPos);

				TextView textView = (TextView) headerView.findViewById(R.id.header);
				textView.setText(str);

			}
		});

		stickyLayout.setOnGiveUpTouchEventListener(new StickyLayout.OnGiveUpTouchEventListener() {
			@Override
			public boolean giveUpTouchEvent(MotionEvent event) {
				if (mExpandableListView.getFirstVisiblePosition() == 0) {
					View view = mExpandableListView.getChildAt(0);
					if (view != null && view.getTop() >= 0) {
						return true;
					}
				}
				return false;
			}
		});

		mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				// We call collapseGroupWithAnimation(int) and
				// expandGroupWithAnimation(int) to animate group
				// expansion/collapse.
				if (mExpandableListView.isGroupExpanded(groupPosition)) {
					mExpandableListView.collapseGroupWithAnimation(groupPosition);
				} else {
					mExpandableListView.expandGroupWithAnimation(groupPosition);
				}
				return true;
			}

		});
	}

	public class MyAnimatorAdapter extends AnimationExpandableListView.AnimatedExpandableListAdapter{

		@Override
		public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			if(convertView==null){
				LayoutInflater inflater=LayoutInflater.from(PinnedExpendableActivity.this);
				convertView= inflater.inflate(R.layout.list_item, null);
			}
			TextView tv=(TextView) convertView.findViewById(R.id.item);
			tv.setText(child[groupPosition][childPosition]);
			return convertView;
		}

		@Override
		public int getRealChildrenCount(int groupPosition) {
			return child[groupPosition].length;
		}

		@Override
		public int getGroupCount() {
			return group.length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return group[groupPosition];
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			if(convertView==null){
				LayoutInflater inflater=LayoutInflater.from(PinnedExpendableActivity.this);
				convertView= inflater.inflate(R.layout.list_header, null);
			}
			TextView tv=(TextView) convertView.findViewById(R.id.header);
			tv.setText(group[groupPosition]);
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
	}
	
	public class MyExpandableAdapter extends BaseExpandableListAdapter{

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if(convertView==null){
                 LayoutInflater inflater=LayoutInflater.from(PinnedExpendableActivity.this);
                 convertView= inflater.inflate(R.layout.list_item, null);
			}
			TextView tv=(TextView) convertView.findViewById(R.id.item);
			tv.setText(child[groupPosition][childPosition]);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return child[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return group[groupPosition];
		}

		@Override
		public int getGroupCount() {
			return group.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if(convertView==null){
                LayoutInflater inflater=LayoutInflater.from(PinnedExpendableActivity.this);
                convertView= inflater.inflate(R.layout.list_header, null);
			}
			TextView tv=(TextView) convertView.findViewById(R.id.header);
			tv.setText(group[groupPosition]);
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
	}
}
