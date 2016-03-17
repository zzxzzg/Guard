package com.guard.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AMessage("haha"));
                EventBus.getDefault().postSticky(new AMessage(" ooooooo  "));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    //@Subscribe(threadMode = ThreadMode.POSTING)  call in the same thread.
    @Subscribe
    public void handlerAMessage(AMessage msg){

    }

    // Called in Android UI's main thread
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerBMessage(BMessage msg){

    }

    /* Called in the background thread If posting thread is not the main thread,
     event handler methods will be called directly in the posting thread.
     If the posting thread is the main thread,
     EventBus uses a single background thread that will deliver all its events sequentially.
     Event handlers using this mode should try to return quickly to avoid blocking the background thread.*/
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void handlerCMessage(CMessage msg){

    }

    // Called in a separate thread
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void handlerDMessage(DMessage msg){

    }
}
