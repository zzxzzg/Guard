package com.guard.animationdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.guard.animationdemo.xmeetting.LoginButtonActivity;

/**
 * Created by yxwang on 15-12-11.
 */
public class MainActivity extends Activity {
    private ListView mListView;

    private static String[] FUNCTION=new String[]{"Crossfading","CirculAnimator_PRE_L","CirculAnimator_L","ViewPagerActivity",
    "CardFlipActivity","ZoomActivity","RippleL","RipplePreL","vector_L","vector_pre_L","XMEETING_LoginButton","WaveView"};
    private static Class[] CLASSES=new Class[]{CrossfadingActivity.class,CirculAnimatorPreL.class,CirculAnimatorL.class,ViewpagerActivity.class,CardFlipActivity.class,ZoomActivity.class,
    RippleLActivity.class,RipplePreLActivity.class, VectorLActivity.class,VectorPreLActivity.class,LoginButtonActivity.class,WaveActivity.class};

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
