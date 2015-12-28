package com.guard.animationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.wnafee.vector.compat.AnimatedVectorDrawable;
import com.wnafee.vector.compat.VectorDrawable;

/**
 * Created by yxwang on 15-12-25.
 */
public class VectorPreLActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vector_l_activity);
        ImageView imageView= (ImageView) findViewById(R.id.img);
        VectorDrawable drawable1=VectorDrawable.create(getResources(),R.drawable.vecotr1);
        imageView.setImageDrawable(drawable1);

        final ImageView imageView3= (ImageView) findViewById(R.id.img3);
        final AnimatedVectorDrawable drawable2=AnimatedVectorDrawable.create(this,getResources(),R.drawable.vector_anim2);
        imageView3.setImageDrawable(drawable2);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable2.start();
            }
        });

        final ImageView imageView2= (ImageView) findViewById(R.id.img2);
        final AnimatedVectorDrawable drawable3=AnimatedVectorDrawable.create(this,getResources(),R.drawable.vector_anim1);
        imageView2.setImageDrawable(drawable3);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable3.start();
            }
        });

    }
}
