package com.guard.puremvc.standard.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFacade.getInstance().registerMediator(new MainMediator(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainFacade.getInstance().removeMediator(MainMediator.NAME);
    }
}
