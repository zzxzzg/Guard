package com.guard.httpstest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Created by yxwang on 15-12-3.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        HttpsAsyncTash task=new HttpsAsyncTash();
        task.execute();

    }

    private class HttpsAsyncTash extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            HttpsConnectionTest connection=new HttpsConnectionTest();
            connection.connectAllTrust();
            return null;
        }


    }
}
