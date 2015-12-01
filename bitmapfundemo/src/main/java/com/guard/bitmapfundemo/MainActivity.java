package com.guard.bitmapfundemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guard.bitmapfun.ImageCache;
import com.guard.bitmapfun.ImageFetcher;
import com.guard.srclib.NetPicture;

import java.util.Random;


public class MainActivity extends Activity {

    private ImageFetcher mImageFetcher;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCache(this);

        mRecyclerView= (RecyclerView) findViewById(R.id.content);
        mGridLayoutManager=new GridLayoutManager(this,2){
            //预加载
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 1000;
            }
        };

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        RecyclerView.OnScrollListener scrollListener=new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    mImageFetcher.setPauseWork(true);
                } else {
                    mImageFetcher.setPauseWork(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);
        ContentAdapter mAdapter=new ContentAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    public class ContentAdapter extends RecyclerView.Adapter{

        public class ContentHolder extends RecyclerView.ViewHolder{
            public ImageView mImage;
            public int mPosition;
            public ContentHolder(View itemView,int position) {
                super(itemView);
                mPosition=position;
                mImage= (ImageView) itemView.findViewById(R.id.image);
            }
        }

        @Override
        public ContentHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v=getLayoutInflater().inflate(R.layout.store_item, null);
            ContentHolder holder=new ContentHolder(v,i);
            return holder;
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ((ContentHolder)viewHolder).mPosition=i;
            final ImageView imageView=((ContentHolder)viewHolder).mImage;
            final int position=i;
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    int width=(int)(Math.random()*300+100);
                    int height=(int)(Math.random()*300+100);
//                    int width=imageView.getWidth();
//                    int height=imageView.getHeight();
                    mImageFetcher.setImageSize(width,height);
                    mImageFetcher.loadImage(NetPicture.H_PICTURE_JPG[position%NetPicture.H_PICTURE_JPG.length],imageView);
                }
            });

            //loadImage(mItemList.get(i).getStoreImage(),((ContentHolder)viewHolder).mImage);
        }

        @Override
        public int getItemCount() {
            return NetPicture.H_PICTURE_JPG.length*100;
        }
    }

    private void initCache(Context context) {
        // 创建一个类，用来存放图片缓存参数
        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(
                context, getImageCacheDir());
        //如果是JPGE那么将会丢失透明色
        cacheParams.compressFormat= Bitmap.CompressFormat.PNG;
        // 设置内存缓存大小，参数表示应用从程序运行环境最大内存的百分比。
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of
        // app memory

        // The ImageFetcher takes care of loading images into our ImageView
        // children asynchronously
        // 创建一个图片加载器，并且设置要加载的图片长和宽都是mImageThumbSize。
        mImageFetcher = new ImageFetcher(context, 0);
        // 设置图片加载时的占位图片
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        // 向该图片加载器中添加图片缓存（包括内存缓存和硬盘缓存）
        mImageFetcher.addImageCache(getFragmentManager(), cacheParams);
    }

    public String getImageCacheDir() {
        return "/cache";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
