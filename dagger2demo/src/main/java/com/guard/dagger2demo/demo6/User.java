package com.guard.dagger2demo.demo6;

import android.util.Log;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by apple on 16/4/16.
 */
public class User {
    @Inject
    Map<String,Integer> map;

    @Inject
    Set<String> set;

    public User(){
        ParentComponent parentComponent=DaggerParentComponent.create();
        parentComponent.Inject(this);
        Log.d("sss",map.containsKey("one")+"   map containsKey");
        Log.d("sss",set.contains("a")+"   set containsKey");

    }
}
