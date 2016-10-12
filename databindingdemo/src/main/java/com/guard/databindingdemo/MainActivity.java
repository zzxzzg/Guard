package com.guard.databindingdemo;

import android.app.Activity;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.guard.databindingdemo.databinding.ActivityMainBinding;
// https://developer.android.com/tools/data-binding/guide.html
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding=DataBindingUtil.setContentView(this,R.layout.activity_main,new User.MyDatabindingComponent());
        User user = new User("Test", "User");
        binding.setUser(user);
        binding.setHandlers(new MyHandlers(user));
        //binding.executePendingBindings(); 请求binding立即生效,有时会在adapter中使用
        Log.d("sss","MainActivity.onCreate");
    }
}
