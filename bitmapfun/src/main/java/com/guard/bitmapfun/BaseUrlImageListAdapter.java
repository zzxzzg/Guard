package com.guard.bitmapfun;




import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class BaseUrlImageListAdapter extends BaseAdapter{
    private  ImageFetcher mImageFetcher;
	public  void initCache(Context context){
		 //创建一个类，用来存放图片缓存参数
        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(context, "image");
         //设置内存缓存大小，参数表示应用从程序运行环境最大内存的百分比。
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        //创建一个图片加载器，并且设置要加载的图片长和宽都是mImageThumbSize。
        mImageFetcher = new ImageFetcher(context,0);
        //设置图片加载时的占位图片
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        //向该图片加载器中添加图片缓存（包括内存缓存和硬盘缓存）
        mImageFetcher.addImageCache(((Activity)context).getFragmentManager(), cacheParams);
	}
	
    public void onResume() {
        mImageFetcher.setExitTasksEarly(false);
        notifyDataSetChanged();
    }

    public void onPause() {
        mImageFetcher.setPauseWork(false);
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    } 
    
    public void onDestroy() {
        mImageFetcher.closeCache();
    }
    
    public void setPauseWork(boolean b){
    	mImageFetcher.setPauseWork(b);
    }

   
	
	public BaseUrlImageListAdapter(Context context){
		if(! (context instanceof Activity)){
			try {
				throw new Exception("You Should use Activity context!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(mImageFetcher==null){
			initCache(context);
		}
	}
	
	public void loadImage(String data,RecyclingImageView imageView){
		mImageFetcher.setImageSize(imageView.getWidth(), imageView.getHeight());
		mImageFetcher.loadImage(data, imageView);
	}
}
