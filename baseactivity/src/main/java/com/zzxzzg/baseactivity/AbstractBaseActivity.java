package com.zzxzzg.baseactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yxwang on 16/6/22.
 */
public abstract class AbstractBaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onStartData();
    }

    /**
     * load data in onCreate
     */
    abstract protected void onCreateData();


    /**
     * load data in onStart
     */
    abstract protected void onStartData();
}
