package com.yalantis.phoenix;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;

/**
 * Created by yxwang on 16/5/17.
 */
public class PulltoRefreshRecyclerView extends BaseRefreshView {

    private RecyclerView mRecyclerView;


    private ViewStub mProgress;
    private ViewStub mEmpty;

    private RecyclerView.Adapter mAdapter;

    private int mListLayout;

    public PulltoRefreshRecyclerView(Context context) {
        super(context);
    }

    public PulltoRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PulltoRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PulltoRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initAttrs(AttributeSet attrs) {
        super.initAttrs(attrs);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseRefreshView);
        try {
            mListLayout=a.getResourceId(R.styleable.BaseRefreshView_listViewLayout,R.layout.recyclerview_layout);
        }finally {
            a.recycle();
        }
    }

    @Override
    protected void init(){
        super.init();
        mRecyclerView= (RecyclerView) LayoutInflater.from(getContext()).inflate(mListLayout,null);
        getPullToRefreshView().addView(mRecyclerView);

        mEmpty= (ViewStub) findViewById(R.id.empty);
        mProgress=(ViewStub)findViewById(R.id.progress);

        //TODO
        mEmpty.setLayoutResource(mEmptyLayoutId);
        mEmpty.inflate();
        mProgress.setLayoutResource(mProgressLayoutId);
        mProgress.inflate();

        mEmpty.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!isNeedLoad()){
                    return;
                }
                if(!isLoadingMore()){
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = getLastVisibleItemPosition(layoutManager);
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();

                    if (((totalItemCount - lastVisibleItemPosition) <= CACHE_COUNT ||
                            (totalItemCount - lastVisibleItemPosition) == 0 && totalItemCount > visibleItemCount)) {
                        loadMore();
                    }
                }
            }
        });
    }

    protected LAYOUT_MANAGER_TYPE layoutManagerType;
    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration){
        mRecyclerView.addItemDecoration(decoration);
    }

    private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = -1;
        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                lastVisibleItemPosition = caseStaggeredGrid(layoutManager);
                break;
        }
        return lastVisibleItemPosition;
    }

    private int[] lastScrollPositions;
    private int caseStaggeredGrid(RecyclerView.LayoutManager layoutManager) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        if (lastScrollPositions == null)
            lastScrollPositions = new int[staggeredGridLayoutManager.getSpanCount()];

        staggeredGridLayoutManager.findLastVisibleItemPositions(lastScrollPositions);
        return findMax(lastScrollPositions);
    }

    private int findMax(int[] lastPositions) {
        int max = Integer.MIN_VALUE;
        for (int value : lastPositions) {
            if (value > max)
                max = value;
        }
        return max;
    }

    public void setAdapter(final RecyclerView.Adapter adapter){

        if(adapter==null){
            mEmpty.setVisibility(View.VISIBLE);
            getPullToRefreshView().setVisibility(View.GONE);
            mProgress.setVisibility(View.GONE);
        }else{
            mAdapter=new WapperRecyclerViewAdapter(adapter);
            mProgress.setVisibility(View.GONE);
            if(adapter.getItemCount()==0){
                getPullToRefreshView().setVisibility(View.GONE);
                mEmpty.setVisibility(View.VISIBLE);
            }else{
                getPullToRefreshView().setVisibility(View.VISIBLE);
                mEmpty.setVisibility(View.GONE);
            }
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                private void modifyView(){
                    mProgress.setVisibility(View.GONE);
                    if(adapter.getItemCount()==0){
                        getPullToRefreshView().setVisibility(View.GONE);
                        mEmpty.setVisibility(View.VISIBLE);
                    }else{
                        getPullToRefreshView().setVisibility(View.VISIBLE);
                        mEmpty.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onChanged() {
                    super.onChanged();
                    modifyView();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                    mAdapter.notifyItemRangeChanged(positionStart,itemCount);
                    modifyView();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                    super.onItemRangeChanged(positionStart, itemCount, payload);
                    mAdapter.notifyItemRangeChanged(positionStart,itemCount,payload);
                    modifyView();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    mAdapter.notifyItemRangeInserted(positionStart,itemCount);
                    modifyView();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    mAdapter.notifyItemRangeRemoved(positionStart,itemCount);
                    modifyView();
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                    mAdapter.notifyItemRangeRemoved(fromPosition, toPosition);
                    modifyView();
                }
            });

        }

        mRecyclerView.setAdapter(mAdapter);
    }

    public RecyclerView.Adapter getAdapter(){
        return mAdapter;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        mRecyclerView.setLayoutManager(layoutManager);
    }

    class WapperRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private static final int TYPE_LOADMORE= 0x7777;

        private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

        class LoadMoreHolder extends RecyclerView.ViewHolder{

            public LoadMoreHolder(View itemView) {
                super(itemView);
                itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadMoreClick();
                    }
                });
            }
        }

        public WapperRecyclerViewAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter){
            mAdapter=adapter;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType==TYPE_LOADMORE){
                return new LoadMoreHolder(getFootView());
            }
            return mAdapter.onCreateViewHolder(parent,viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof LoadMoreHolder){

            }else{
                mAdapter.onBindViewHolder(holder,position);
            }
        }

        @Override
        public int getItemCount() {
            if(mAdapter.getItemCount()==0){
                return 0;
            }
            return mAdapter.getItemCount()+1;
        }

        @Override
        public int getItemViewType(int position) {
            if(position==mAdapter.getItemCount()){
                return TYPE_LOADMORE;
            }
            return mAdapter.getItemViewType(position);
        }
    }


}
