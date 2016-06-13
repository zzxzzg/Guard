package com.zzxzzg.coordinatorlayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;


/**
 * Created by yxwang on 16/6/12.
 */
public class ToolBarTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_test_activity);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView lv= (RecyclerView) findViewById(R.id.recycler);
        lv.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter adapter=new TestAdapter(this);
        lv.setAdapter(adapter);
    }
}
