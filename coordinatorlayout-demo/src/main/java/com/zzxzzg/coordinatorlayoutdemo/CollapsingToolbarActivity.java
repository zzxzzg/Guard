package com.zzxzzg.coordinatorlayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by yxwang on 16/6/13.
 */
public class CollapsingToolbarActivity extends AppCompatActivity{
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapsing_toolbar_activity);
        mCollapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        mCollapsingToolbarLayout.setTitle("hahahahahaa");


//        RecyclerView lv= (RecyclerView) findViewById(R.id.recycler);
//        lv.setLayoutManager(new LinearLayoutManager(this));
//        TestAdapter adapter=new TestAdapter(this);
//        lv.setAdapter(adapter);

        WebView view= (WebView) findViewById(R.id.webview);
        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //return super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        WebView view= (WebView) findViewById(R.id.webview);
        view.loadUrl("http://xw.qq.com/index.htm");
    }
}
