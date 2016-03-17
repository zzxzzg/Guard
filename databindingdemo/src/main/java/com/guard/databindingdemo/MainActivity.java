package com.guard.databindingdemo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.guard.databindingdemo.databinding.ActivityMainBinding;
// https://developer.android.com/tools/data-binding/guide.html
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        User user = new User("Test", "User");
        binding.setUser(user);
        binding.setHandlers(new MyHandlers(user));
    }
}
