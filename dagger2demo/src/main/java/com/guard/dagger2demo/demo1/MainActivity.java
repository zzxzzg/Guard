package com.guard.dagger2demo.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.common.util.concurrent.ListenableFuture;
import com.guard.dagger2demo.R;
import com.guard.dagger2demo.demo2.Customer;
import com.guard.dagger2demo.demo3.Person;

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
        Log.d("sss",mOSHelp.getDeviceBrand());

        Customer customer=new Customer();
        Person person=new Person();

        com.guard.dagger2demo.demo6.User user=new com.guard.dagger2demo.demo6.User();


    }
}
