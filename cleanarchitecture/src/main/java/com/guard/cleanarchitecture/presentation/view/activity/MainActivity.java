package com.guard.cleanarchitecture.presentation.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.guard.cleanarchitecture.presentation.R;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  private Button mButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mButton= (Button) findViewById(R.id.btn_LoadData);
    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        MainActivity.this.navigator.navigateToUserList(MainActivity.this);
      }
    });
    ButterKnife.bind(this);
  }

  /**
   * Goes to the user list screen.
   */
  @OnClick(R.id.btn_LoadData)
  void navigateToUserList() {
    Log.d("sss","navigateToUserList");
    this.navigator.navigateToUserList(this);
  }
}
