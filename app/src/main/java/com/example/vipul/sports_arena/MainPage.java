package com.example.vipul.sports_arena;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.vipul.sports_arena.adapter.TabsPagerAdapter;

import java.util.List;
import java.util.Vector;

@SuppressWarnings("deprecation")
public class MainPage extends AppCompatActivity implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener{

    private TabHost tabHost;
    private ViewPager viewPager;
    private TabsPagerAdapter myTabsAdapter;
    int i = 0;

    class FakeContent implements TabHost.TabContentFactory
    {
        private final Context mContext;
        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String s) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    SharedPreferences.Editor editor;
    TextView textView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        i++;

        //MenuItem menuItem = (MenuItem) findViewById(R.id.sharedPrefName);
        //menuItem.setTitle(sharedPreferences.getString("name","0"));
        this.initializeTabHost(savedInstanceState);
        this.initializeViewPager();

    }

    private void initializeViewPager() {
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(new Indoor());
        fragments.add(new Outdoor());
        fragments.add(new Lan());

        this.myTabsAdapter = new TabsPagerAdapter(getSupportFragmentManager(),fragments);
        this.viewPager = (ViewPager)super.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myTabsAdapter);
        this.viewPager.setOnPageChangeListener(this);

        onRestart();

    }

    private void initializeTabHost(Bundle args) {

        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("Indoor");
        tabSpec.setIndicator("Indoor");
        tabSpec.setContent(new FakeContent(this));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Outdoor");
        tabSpec.setIndicator("Outdoor");
        tabSpec.setContent(new FakeContent(this));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Lan");
        tabSpec.setIndicator("Lan");
        tabSpec.setContent(new FakeContent(this));
        tabHost.addTab(tabSpec);

        tabHost.setOnTabChangedListener(this);

    }

    @Override
    public void onTabChanged(String s) {
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.hScrollBarView);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()-(horizontalScrollView.getWidth()-tabView.getWidth())/2;
        horizontalScrollView.smoothScrollTo(scrollPos,0);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d("T","State:"+state);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        Log.d("T","State:"+position+positionOffset+positionOffsetPixels);

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("T","State Selected:"+position);
        this.tabHost.setCurrentTab(position);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(R.mipmap.football).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        getMenuInflater().inflate(R.menu.mymenu,menu);
        //menu.getItem(R.id.sharedPrefName).setTitle(sharedPreferences.getString("name","0"));

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        menu.findItem(R.id.sharedPrefName).setTitle(sharedPreferences.getString("name","0"));

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        sharedPreferences =getSharedPreferences("userInfo",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        int id = item.getItemId();
        switch (id) {

            case R.id.logOut :  editor.putString("login","0");
                                editor.putString("name",null);
                                editor.putString("email",null);
                                editor.putString("username",null);
                                editor.putString("password",null);
                                editor.apply();
                                startActivity(new Intent(MainPage.this,MainActivity.class));
        }
        return true;
    }
}