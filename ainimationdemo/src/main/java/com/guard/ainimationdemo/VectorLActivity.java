package com.guard.ainimationdemo;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by yxwang on 15-12-17.
 */
public class VectorLActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vector_l_activity);
        ImageView imageView= (ImageView) findViewById(R.id.img);
        imageView.setImageResource(R.drawable.vecotr1);

        final ImageView imageView2= (ImageView) findViewById(R.id.img2);
        imageView2.setImageResource(R.drawable.vector_anim1);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = imageView2.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            }
        });

        final ImageView imageView3= (ImageView) findViewById(R.id.img3);
        imageView3.setImageResource(R.drawable.vector_anim2);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = imageView3.getDrawable();
                if (drawable instanceof Animatable) {
                    Log.d("sss", "start anim");
                    ((Animatable) drawable).start();
                }
            }
        });


    }
}
