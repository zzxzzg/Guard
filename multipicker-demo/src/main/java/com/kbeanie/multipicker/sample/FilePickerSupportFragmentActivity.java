package com.kbeanie.multipicker.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by kbibek on 2/19/16.
 */
public class FilePickerSupportFragmentActivity extends AbActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker_support_fragment);

        getSupportActionBar().setTitle("File Picker");
        getSupportActionBar().setSubtitle("Support Fragment example");
    }
}
