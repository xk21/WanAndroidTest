package com.cmy.wanandroidtest.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cmy.wanandroidtest.R;
import com.cmy.wanandroidtest.base.BaseActivity;
import com.cmy.wanandroidtest.base.BasePresenter;
import com.cmy.wanandroidtest.ui.fragment.HomePageFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private final int FRAGMENT_HOME = 0;
    private final int FRAGMENT_KNOWLEDGE = 1;
    private final int FRAGMENT_NAVIGATION = 2;
    private final int FRAGMENT_PROJECT = 3;
    private ArrayList<Fragment> fragments;
    private int lastIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showFragment(FRAGMENT_HOME);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {
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
        initFragment();
        selectFragment(FRAGMENT_HOME);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void showFragment(int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(HomePageFragment.getInstance());
//        fragments.add(KnowledgeFragment.getInstance());
//        fragments.add(ProjectFragment.getInstance());
//        fragments.add(GankFragment.getInstance());
//        fragments.add(PersonalFragment.getInstance());
    }

    /**
     * 切换Fragment
     *
     * @param position
     */
    private void selectFragment(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragments.get(position);
        Fragment lastFragment = fragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.container, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
//        presenter.setCurrentPage(position);
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
                showFragment(FRAGMENT_HOME);
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
