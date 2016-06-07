package com.yalantis.phoenix;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.yalantis.phoenix.refresh_view.BaseFrameView;

/**
 * Created by yxwang on 16/5/17.
 */
public abstract class BaseRefreshView extends FrameLayout {
    public static final int CACHE_COUNT=5;

    private View mFootView;
    private OnLoadMoreListener mOnLoadMoreListener;
    private PullToRefreshView.OnRefreshListener mOnRefreshListener;

    private ViewStub mEndView;
    private ViewStub mLoadMoreProgress;
    private ViewStub mClickLoadMore;

    private PullToRefreshView mPullToRefreshView;

    private boolean isLoadingMore=false;

    public int mProgressLayoutId;
    public int mEmptyLayoutId;
    private int mLoadMoreProgressId;
    private int mLoadMoreEndId;
    private int mLoadMoreClickId;

    public int mAnimType;

    public BaseRefreshView(Context context) {
        super(context);
        init();
    }

    public BaseRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    public BaseRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseRefreshView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs);
        init();
    }

    public PullToRefreshView getPullToRefreshView(){
        return mPullToRefreshView;
    }

    public void setRefreshEnable(boolean b){
        mPullToRefreshView.setEnabled(b);
    }

    protected void initAttrs(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseRefreshView);
        try {
            mProgressLayoutId=a.getResourceId(R.styleable.BaseRefreshView_progressLayout,R.layout.layout_progress);
            mEmptyLayoutId=a.getResourceId(R.styleable.BaseRefreshView_emptyLayout,R.layout.layout_empty);
            mLoadMoreProgressId=a.getResourceId(R.styleable.BaseRefreshView_loadMoreProgress,R.layout.layout_progress);
            mLoadMoreEndId=a.getResourceId(R.styleable.BaseRefreshView_loadMoreEnd,R.layout.layout_end);
            mLoadMoreClickId=a.getResourceId(R.styleable.BaseRefreshView_loadMoreClick,R.layout.layout_click_load);
            mAnimType=a.getInteger(R.styleable.BaseRefreshView_anim_type,PullToRefreshView.STYLE_FRAME);
        } finally {
            a.recycle();
        }
    }


    protected void init(){

        LayoutInflater.from(getContext()).inflate(R.layout.pulltorefresh_recyclerview,this);
        mPullToRefreshView= (PullToRefreshView) findViewById(R.id.refresh_view);

        mPullToRefreshView.setRefreshStyle(mAnimType);

        AnimationDrawable drawable=(AnimationDrawable) getResources().getDrawable(R.drawable.anim_accelerator);
        BaseFrameView frameView=new BaseFrameView(drawable,mPullToRefreshView);
        mPullToRefreshView.setRefreshFrame(frameView);

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                innerRefresh();
                if(getOnRefreshListener()!=null){
                    getOnRefreshListener().onRefresh();
                }
            }
        });


        mFootView= LayoutInflater.from(getContext()).inflate(R.layout.footer_layout,null);
        mEndView= (ViewStub) mFootView.findViewById(R.id.end);
        mLoadMoreProgress= (ViewStub) mFootView.findViewById(R.id.progress);
        mClickLoadMore= (ViewStub) mFootView.findViewById(R.id.click_load_more);

        mLoadMoreProgress.setLayoutResource(mLoadMoreProgressId);
        mEndView.setLayoutResource(mLoadMoreEndId);
        mClickLoadMore.setLayoutResource(mLoadMoreClickId);

        mLoadMoreProgress.inflate();
        mEndView.inflate();
        mClickLoadMore.inflate();
        mClickLoadMore.setVisibility(View.VISIBLE);
        mLoadMoreProgress.setVisibility(View.GONE);
        mEndView.setVisibility(View.GONE);
    }

    public boolean isNeedLoad(){
        if(mOnLoadMoreListener==null || !mOnLoadMoreListener.needLoadMore()){
            mClickLoadMore.setVisibility(View.GONE);
            mLoadMoreProgress.setVisibility(View.GONE);
            mEndView.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    public void loadMoreClick(){
        if(mOnLoadMoreListener!=null && mOnLoadMoreListener.needLoadMore()) {
            loadMore();
        }
    }

    private void innerRefresh(){
        if(isLoadingMore && mOnLoadMoreListener!=null){
            mOnLoadMoreListener.cancelLoadMore();
        }
        isLoadingMore=false;
        mClickLoadMore.setVisibility(View.VISIBLE);
        mEndView.setVisibility(View.GONE);
        mLoadMoreProgress.setVisibility(View.GONE);
    }

    public void loadMore(){
        if(!isLoadingMore) {
            isLoadingMore=true;
            mClickLoadMore.setVisibility(View.GONE);
            mEndView.setVisibility(View.GONE);
            mLoadMoreProgress.setVisibility(View.VISIBLE);
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void setRefreshing(boolean b){
        mPullToRefreshView.setRefreshing(b);
    }

    public void finishLoadMore(){
        isLoadingMore=false;
        if(mOnLoadMoreListener!=null && mOnLoadMoreListener.needLoadMore()) {
            mClickLoadMore.setVisibility(View.VISIBLE);
            mEndView.setVisibility(View.GONE);
            mLoadMoreProgress.setVisibility(View.GONE);
        }else{
            mClickLoadMore.setVisibility(View.GONE);
            mEndView.setVisibility(View.VISIBLE);
            mLoadMoreProgress.setVisibility(View.GONE);
        }
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public void setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    public View getFootView() {
        return mFootView;
    }

    public void setFootView(View footView) {
        mFootView = footView;
    }

    public PullToRefreshView.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void setOnRefreshListener(PullToRefreshView.OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
        boolean needLoadMore();
        void cancelLoadMore();
    }
}
