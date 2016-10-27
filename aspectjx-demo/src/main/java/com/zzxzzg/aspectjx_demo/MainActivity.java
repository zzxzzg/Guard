package com.zzxzzg.aspectjx_demo;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zzxzzg.aspectjx_demo.databinding.ActivityMainBinding;
import com.zzxzzg.aspectjx_lib.annotation.DebugTrace;

public class MainActivity extends Activity {

  private Button btnRelativeLayoutTest;
  private Button btnLinearLayoutTest;
  private Button btnFrameLayoutTest;
  ActivityMainBinding binding;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    binding.setBean(new TextBean());
//    setContentView(R.layout.activity_main);
    mapGUI();
    callTest();
  }

  public final void callTest(){
    Log.d("sss","hahahhaah");
  }

  /**
   * Maps Graphical User Interface
   */
  @DebugTrace
  private void mapGUI() {
//    btnRelativeLayoutTest= (Button) findViewById(R.id.btnRelativeLayout);
//    btnRelativeLayoutTest.setOnClickListener(new View.OnClickListener() {
//      @Override
//      @DebugTrace
//      public void onClick(View v) {
//
//      }
//    });
    binding.btnFrameLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      @DebugTrace
      public void onClick(View v) {

      }
    });


//    btnFrameLayoutTest= (Button) findViewById(R.id.btnFrameLayout);
//    btnFrameLayoutTest.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//
//      }
//    });
    binding.btnLinearLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });


//    btnLinearLayoutTest= (Button) findViewById(R.id.btnLinearLayout);
//    btnLinearLayoutTest.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//
//      }
//    });
    binding.btnRelativeLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
  }

//  private View.OnClickListener btnRelativeLayoutOnClickListener = new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//      openActivity(RelativeLayoutTestActivity.class);
//    }
//  };
//
//  private View.OnClickListener btnLinearLayoutOnClickListener = new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//      openActivity(LinearLayoutTestActivity.class);
//    }
//  };
//
//  private View.OnClickListener btnFrameLayoutOnClickListener = new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//      openActivity(FrameLayoutTestActivity.class);
//    }
//  };

  /**
   * Open and activity
   */
  private void openActivity(Class activityToOpen) {
    Intent intent = new Intent(this, activityToOpen);
    startActivity(intent);
  }
}
