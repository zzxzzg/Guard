package com.guard.animationdemo.xmeetting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guard.animationdemo.R;
import com.guard.animationlib.TransitionUtil;
import com.guard.animationlib.ViewUtils;

/**
 * Created by yxwang on 15-12-17.
 */
public class LoginButtonActivity extends Activity {
    private RelativeLayout mLoginButton;
    private TextView mLoginText;
    private ProgressBar mLoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_button_activity);
        mLoginButton= (RelativeLayout) findViewById(R.id.login_button);
        mLoginText= (TextView) findViewById(R.id.login_txt);
        mLoginProgress= (ProgressBar) findViewById(R.id.login_progress);

        ViewUtils.setRippleBackground(getResources(), getTheme(), mLoginButton, R.drawable.ripple_login_button);

        ViewUtils.setDrawableHotspotTouch(mLoginButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLoginText.getVisibility()!=View.VISIBLE){
                    TransitionUtil.CrossFade(LoginButtonActivity.this, mLoginText,mLoginProgress);
                }else {
                    TransitionUtil.CrossFade(LoginButtonActivity.this, mLoginProgress, mLoginText);
                }
            }
        });

    }
}
