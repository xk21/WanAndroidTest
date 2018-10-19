package com.cmy.wanandroidtest.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cmy.wanandroidtest.R;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private final int FRAGMENT_HOME = 1;
    private final int FRAGMENT_KNOWLEDGE = 2;
    private final int FRAGMENT_NAVIGATION = 3;
    private final int FRAGMENT_PROJECT = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showFragment(FRAGMENT_HOME);
    }

    private void showFragment(int index) {

    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setTitle("玩安卓tool");

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_apps_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mNavigationView);
            }
        });
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:

                return true;
            case R.id.action_knowledge_system:

                return true;
            case R.id.action_navigation:

                return true;
            case R.id.action_project:

                return true;
        }
        return false;
    }
}
