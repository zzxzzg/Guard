package it.sephiroth.android.library.imagezoom.test;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import it.sephiroth.android.library.imagezoom.test.utils.DecodeUtils;

/**
 * Created by yxwang on 15-12-1.
 */
public class MainActivity extends Activity {

    private ImageViewTouch mImageViewTouch;
    private Button mButton1;
    CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mImageViewTouch= (ImageViewTouch) findViewById(R.id.image);
        mButton1= (Button) findViewById(R.id.button);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRandomImage(mCheckBox.isChecked());
            }
        });
        mImageViewTouch.setSingleTapListener(
                new ImageViewTouch.OnImageViewTouchSingleTapListener() {

                    @Override
                    public void onSingleTapConfirmed() {
                        Log.d("sss", "onSingleTapConfirmed");
                    }
                }
        );

        mImageViewTouch.setDoubleTapListener(
                new ImageViewTouch.OnImageViewTouchDoubleTapListener() {

                    @Override
                    public void onDoubleTap() {
                        Log.d("sss", "onDoubleTap");
                    }
                }
        );
    }

    public void selectRandomImage(boolean small) {
        Cursor c = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (c != null) {
            int count = c.getCount();
            //int position = (int) (Math.random() * count);
            int position = 0;
            if (c.moveToPosition(position)) {
                long id = c.getLong(c.getColumnIndex(MediaStore.Images.Media._ID));
                Uri imageUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + id);

                Log.d("image", imageUri.toString());

                final DisplayMetrics metrics = getResources().getDisplayMetrics();
                int size = (int) (Math.min(metrics.widthPixels, metrics.heightPixels) / 0.55);

                if (small) {
                    size /= 3;
                }

                Bitmap bitmap = DecodeUtils.decode(this, imageUri, size, size);

                if (null != bitmap) {


                    mImageViewTouch.setImageBitmap(bitmap, null, - 1, - 1);
                    Bitmap overlay= BitmapFactory.decodeResource(getResources(),R.drawable.contour_chin);
                    mImageViewTouch.addOverlayPointF(new PointF(100,100),overlay);

                    mImageViewTouch.setOnDrawableChangedListener(
                            new ImageViewTouchBase.OnDrawableChangeListener() {
                                @Override
                                public void onDrawableChanged(final Drawable drawable) {
                                    Log.v("sss", "image scale: " + mImageViewTouch.getScale() + "/" + mImageViewTouch.getMinScale());
                                    Log.v("sss", "scale type: " + mImageViewTouch.getDisplayType() + "/" + mImageViewTouch.getScaleType());

                                }
                            }
                    );

                }
                else {
                    Toast.makeText(this, "Failed to load the image", Toast.LENGTH_LONG).show();
                }
            }
            c.close();
            return;
        }
    }


}
