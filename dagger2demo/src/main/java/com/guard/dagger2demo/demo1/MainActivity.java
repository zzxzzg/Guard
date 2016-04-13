package com.guard.dagger2demo.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.guard.dagger2demo.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    //@Inject
    OSHelp mOSHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityComponent component=DaggerActivityComponent.builder().appComponent(((MyApplication)getApplication()).getAppComponent()).build();
        component.inject(this);

        mOSHelp=component.getOSHelp();
        mOSHelp.getDeviceBrand();
    }
}
