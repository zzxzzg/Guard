package com.guard.ainimationdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by yxwang on 15-12-11.
 */
public class MainActivity extends Activity {
    private ListView mListView;

    private static String[] FUNCTION=new String[]{"Crossfading"};
    private static Class[] CLASSES=new Class[]{CrossfadingActivity.class};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mListView= (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        adapter.addAll(FUNCTION);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setComponent(new ComponentName(MainActivity.this,CLASSES[position]));
                startActivity(intent);
            }
        });

    }
}
