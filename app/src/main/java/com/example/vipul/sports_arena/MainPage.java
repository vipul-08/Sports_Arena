package com.example.vipul.sports_arena;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TextView;


import java.util.List;
import java.util.Vector;

@SuppressWarnings("deprecation")
public class MainPage extends AppCompatActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    Fragment indoorFragment,outdoorFragment,lanFragment;

    //private TabsPagerAdapter myTabsAdapter;
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

    Toolbar toolbar;
    //Toolbar toolbar;
    SharedPreferences.Editor editor;
    TextView textView;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        i++;
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.mymenu);
        toolbar.getMenu().findItem(R.id.sharedPrefName).setTitle(sharedPreferences.getString("name","0"));

        toolbar.getMenu().findItem(R.id.myBookings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(MainPage.this,MyBookings.class));
                return true;
            }
        });

        toolbar.getMenu().findItem(R.id.logOut).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                editor.putString("login","0");
                editor.putString("name",null);
                editor.putString("email",null);
                editor.putString("username",null);
                editor.putString("password",null);
                editor.apply();
                startActivity(new Intent(MainPage.this,MainActivity.class));

                return true;
            }
        });
        //toolbar.setTitle("Sports_Arena");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);


        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        indoorFragment=new Indoor();
        outdoorFragment=new Outdoor();
        lanFragment=new Lan();
        adapter.addFragment(outdoorFragment,"OUTDOOR");
        adapter.addFragment(indoorFragment,"INDOOR");
        adapter.addFragment(lanFragment,"LAN");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(R.mipmap.football).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                    }
                }).setNegativeButton("No", null).show();
    }
}