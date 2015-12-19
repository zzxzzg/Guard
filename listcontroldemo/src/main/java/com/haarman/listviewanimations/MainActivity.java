/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haarman.listviewanimations;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.haarman.listviewanimations.appearance.AppearanceExamplesActivity;
import com.haarman.listviewanimations.googlecards.GoogleCardsActivity;
import com.haarman.listviewanimations.gridview.GridViewActivity;
import com.haarman.listviewanimations.itemmanipulation.ItemManipulationsExamplesActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private static final String URL_GITHUB_IO = "http://nhaarman.github.io/ListViewAnimations?ref=app";

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_github:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(URL_GITHUB_IO));
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void onGoogleCardsExampleClicked(final View view) {
        Intent intent = new Intent(this, GoogleCardsActivity.class);
        startActivity(intent);
    }

    public void onGridViewExampleClicked(final View view) {
        Intent intent = new Intent(this, GridViewActivity.class);
        startActivity(intent);
    }

    public void onAppearanceClicked(final View view) {
        Intent intent = new Intent(this, AppearanceExamplesActivity.class);
        startActivity(intent);
    }

    public void onItemManipulationClicked(final View view) {
        Intent intent = new Intent(this, ItemManipulationsExamplesActivity.class);
        startActivity(intent);
    }

    public void onSLHClicked(final View view) {
        Intent intent = new Intent(this, StickyListHeadersActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



}
