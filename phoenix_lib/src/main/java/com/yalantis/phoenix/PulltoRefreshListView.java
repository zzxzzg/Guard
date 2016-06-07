package com.yalantis.phoenix;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by yxwang on 16/5/17.
 */
public class PulltoRefreshListView extends BaseRefreshView {

    private ListView mListView;
    private ListAdapter mAdapter;

    private ViewStub mProgress;
    private ViewStub mEmpty;

    private AdapterView.OnItemClickListener mOnItemClickListener;

    private int mListLayout;

    public PulltoRefreshListView(Context context) {
        super(context);
    }

    public PulltoRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PulltoRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PulltoRefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void initAttrs(AttributeSet attrs) {
        super.initAttrs(attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseRefreshView);
        try {
            mListLayout=a.getResourceId(R.styleable.BaseRefreshView_listViewLayout,R.layout.list_layout);
        }finally {
            a.recycle();
        }
    }


    @Override
    protected void init(){
        super.init();
        mListView= (ListView) LayoutInflater.from(getContext()).inflate(mListLayout,null);

        mEmpty= (ViewStub) findViewById(R.id.empty);
        mProgress=(ViewStub)findViewById(R.id.progress);
        getPullToRefreshView().addView(mListView);

        //TODO
        mEmpty.setLayoutResource(mEmptyLayoutId);
        mEmpty.inflate();
        mProgress.setLayoutResource(mProgressLayoutId);
        mProgress.inflate();

        mEmpty.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);

        mListView.addFooterView(getFootView());



        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(!isNeedLoad()){
                    return;
                }
                if(!isLoadingMore()){
                    int last = view.getLastVisiblePosition();
                    if (last > totalItemCount - CACHE_COUNT) {
                        loadMore();
                    }
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==mAdapter.getCount()){
                    loadMoreClick();
                }else{
                    if(mOnItemClickListener!=null){
                        mOnItemClickListener.onItemClick(parent,view,position,id);
                    }
                }
            }
        });

    }

    public void setAdapter(ListAdapter adapter) {
        mAdapter = adapter;
        if(mAdapter==null){
            mEmpty.setVisibility(View.VISIBLE);
            getPullToRefreshView().setVisibility(View.GONE);
            mProgress.setVisibility(View.GONE);
        }else{
            mProgress.setVisibility(View.GONE);
            if(mAdapter.getCount()==0){
                getPullToRefreshView().setVisibility(View.GONE);
                mEmpty.setVisibility(View.VISIBLE);
            }else{
                getPullToRefreshView().setVisibility(View.VISIBLE);
                mEmpty.setVisibility(View.GONE);
            }

            mAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    modifyView();
                }

                @Override
                public void onInvalidated() {
                    super.onInvalidated();
                    modifyView();
                }

                private void modifyView(){
                    mProgress.setVisibility(View.GONE);
                    if(mAdapter.getCount()==0){
                        getPullToRefreshView().setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                    }else{
                        getPullToRefreshView().setVisibility(View.VISIBLE);
                        mEmpty.setVisibility(View.GONE);
                    }
                }
            });
        }

        mListView.setAdapter(mAdapter);
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

//
//    public boolean removeFooterView(View v) {
//        if (mFooterViewInfos.size() > 0) {
//            boolean result = false;
//            if (mAdapter != null && ((HeaderViewListAdapter) mAdapter).removeFooter(v)) {
//                result = true;
//            }
//            removeFixedViewInfo(v, mFooterViewInfos);
//            return result;
//        }
//        return false;
//    }
//
//
//    public boolean removeHeaderView(View v) {
//        if (mHeaderViewInfos.size() > 0) {
//            boolean result = false;
//            if (mAdapter != null && ((HeaderViewListAdapter) mAdapter).removeHeader(v)) {
//                result = true;
//            }
//            removeFixedViewInfo(v, mHeaderViewInfos);
//            return result;
//        }
//        return false;
//    }
//
//    private void removeFixedViewInfo(View v, ArrayList<HeaderViewListAdapter.FixedViewInfo> where) {
//        int len = where.size();
//        for (int i = 0; i < len; ++i) {
//            HeaderViewListAdapter.FixedViewInfo info = where.get(i);
//            if (info.view == v) {
//                where.remove(i);
//                break;
//            }
//        }
//    }
//
//    public void addHeaderView(View v){
//        HeaderViewListAdapter.FixedViewInfo info = new HeaderViewListAdapter.FixedViewInfo();
//        info.view = v;
//        info.data = null;
//        info.isSelectable = false;
//        mHeaderViewInfos.add(info);
//
//        // Wrap the adapter if it wasn't already wrapped.
//        if (mAdapter != null) {
//            if (!(mAdapter instanceof HeaderViewListAdapter)) {
//                mAdapter = new HeaderViewListAdapter(mHeaderViewInfos, mFooterViewInfos, mAdapter);
//            }
//        }
//    }
//
//    public void addFooterView(View v){
//        HeaderViewListAdapter.FixedViewInfo info = new HeaderViewListAdapter.FixedViewInfo();
//        info.view = v;
//        info.data = null;
//        info.isSelectable = false;
//        mFooterViewInfos.add(info);
//
//        // Wrap the adapter if it wasn't already wrapped.
//        if (mAdapter != null) {
//            if (!(mAdapter instanceof HeaderViewListAdapter)) {
//                mAdapter = new HeaderViewListAdapter(mHeaderViewInfos, mFooterViewInfos, mAdapter);
//            }
//        }
//    }

}
