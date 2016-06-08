package com.kbeanie.multipicker.sample;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kbeanie.multipicker.sample.prefs.AppPreferences;

/**
 * Created by kbibek on 2/18/16.
 */
public class AbActivity extends AppCompatActivity {

    protected AppPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(this instanceof HomeActivity)) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = new AppPreferences(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    public final static String GALAXY_TAB = "6B7B033AC9940497E369C02B714E9483";
    public final static String NEXUS_S = "55958F02BF66EEC31424761A58B1733B";
    public final static String TEST_DEVICE_ID_2 = "79B7F70DBE55777CD06F8FE2EBEB92A1";
    public final static String TEST_GALAXY_NEXUS = "E83F73F907EE7CBDDE5F97BD3A901D4A";
    public final static String TEST_OPO = "4D770FE855ACB535BBC528C73DDB872A";
    public final static String TEMP_DEVICE = "F60A56ABBAEF39154F47C43E1CFA32F8";
    public final static String XIAOMI = "EEAEE535D859D6F87E56B88B7A65D984";

    private final static String[] TEST_DEVICES = {GALAXY_TAB, NEXUS_S, TEST_DEVICE_ID_2, TEST_GALAXY_NEXUS, TEST_OPO, TEMP_DEVICE, XIAOMI};


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
