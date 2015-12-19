package com.guard.animationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;

import com.guard.animationlib.ViewUtils;

/**
 * Created by yxwang on 15-12-16.
 */
public class RipplePreLActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ripple_pre_l_activity);
        Button button1= (Button) findViewById(R.id.button1);
        Button button2= (Button) findViewById(R.id.button2);
        Button button3= (Button) findViewById(R.id.button3);
        Button button4= (Button) findViewById(R.id.button4);

        ViewUtils.setRippleBackground(getResources(), getTheme(), button1, R.drawable.ripple1);

        ViewUtils.setRippleBackground(getResources(), getTheme(), button2, R.drawable.ripple1);
        ViewUtils.setDrawableHotspotTouch(button2);


    }
}
