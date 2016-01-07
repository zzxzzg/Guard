package zzxzzg.com.guardlistdemo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import zzxzzg.com.guardlistdemo.swipemenulistview.SwipeMenu;
import zzxzzg.com.guardlistdemo.swipemenulistview.SwipeMenuItem;
import zzxzzg.com.guardlistdemo.swipemenulistview.SwipeMenuView;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private GuardAdapter mGuardAdapter=new GuardAdapter(MainActivity.this) {
        @Override
        public SwipeMenu getSwipeMenu(int position, SwipeMenu menu) {
            menu = new SwipeMenu(MainActivity.this);
		SwipeMenuItem item = new SwipeMenuItem(MainActivity.this);
		item.setTitle("Item 1");
		item.setBackground(new ColorDrawable(Color.RED));
		item.setWidth(300);
		menu.addMenuItem(item);

		item = new SwipeMenuItem(MainActivity.this);
		item.setTitle("Item 2");
		item.setBackground(new ColorDrawable(Color.GREEN));
		item.setWidth(300);
		menu.addMenuItem(item);
            menu.setOnSwipeItemClickListener(new SwipeMenu.OnSwipeItemClickListener() {
                @Override
                public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {

                }
            });
            return menu;
        }

        @Override
        public View getContentView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public View getExpandView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public int getCount() {
            return 100;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView= (ListView) findViewById(R.id.list);
        mListView.setAdapter(mGuardAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
