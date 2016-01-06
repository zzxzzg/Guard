package zzxzzg.com.guardlistdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.BitSet;

import zzxzzg.com.guardlistdemo.swipemenulistview.SwipeMenuLayout;

/**
 * Created by yxwang on 16-1-4.
 */
public abstract class GuardAdapter extends BaseAdapter {

    private Context mContext;

    private BitSet mModeSet=new BitSet();

    public GuardAdapter(Context context){
        mContext = context;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView!=null){
            holder= (ViewHolder) convertView.getTag();
        }
        if(holder == null){
            convertView = new GuardListItem(mContext);
            holder=new ViewHolder();
            holder.mContentView= (SwipeMenuLayout) ((GuardListItem)convertView).getContentLayout();
            holder.mExpandView=((GuardListItem)convertView).getExpandLayout();
        }

        ((GuardListItem)convertView).setOnModeChangeListener(new GuardListItem.OnModeChangeListener() {
            @Override
            public void onModeChange(int mode) {
                if(mode == GuardListItem.STATE_EXPANDED || mode == GuardListItem.STATE_EXPANDING){
                    mModeSet.set(position,true);
                }else{
                    mModeSet.set(position,false);
                }
            }
        });
        convertView.setTag(holder);

//        View content=getContentView(position,holder.mContentView.getChildAt(0),parent);
//        holder.mContentView.removeAllViews();
//        if(content!=null) {
//            holder.mContentView.addView(content);
//        }

        View expand=getExpandView(position,holder.mExpandView.getChildAt(0),parent);
        holder.mExpandView.removeAllViews();
        if(expand!=null) {
            holder.mExpandView.addView(expand);
        }

        if(mModeSet.get(position)){
            ((GuardListItem)convertView).setState(GuardListItem.STATE_EXPANDED);
        }else{
            ((GuardListItem)convertView).setState(GuardListItem.STATE_COLLAPSED);
        }

        return convertView;
    }

//    private void test(){
//        SwipeMenuLayout layout = null;
//        if (convertView == null) {
//            View contentView = mAdapter.getView(position, convertView, parent);
//            SwipeMenu menu = new SwipeMenu(mContext);
//            menu.setViewType(mAdapter.getItemViewType(position));
//            createMenu(menu);
//            SwipeMenuView menuView = new SwipeMenuView(menu,
//                    (SwipeMenuListView) parent);
//            menuView.setOnSwipeItemClickListener(this);
//            SwipeMenuListView listView = (SwipeMenuListView) parent;
//            layout = new SwipeMenuLayout(contentView, menuView,
//                    listView.getCloseInterpolator(),
//                    listView.getOpenInterpolator());
//            layout.setPosition(position);
//        } else {
//            layout = (SwipeMenuLayout) convertView;
//            layout.closeMenu();
//            layout.setPosition(position);
//            View view = mAdapter.getView(position, layout.getContentView(),
//                    parent);
//        }
//        return layout;
//    }

    public abstract View getContentView(int position,View convertView,ViewGroup parent);

    public abstract View getExpandView(int position,View convertView,ViewGroup parent);

    private class ViewHolder{
        SwipeMenuLayout mContentView;
        ViewGroup mExpandView;
    }
}
