package com.git.hubreeh.view.employer.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.git.hubreeh.R;
import com.git.hubreeh.view.employer.fragment.BusinessHomeJobs;
import com.git.hubreeh.view.employer.fragment.BusinessTransactions;
import com.git.hubreeh.view.employer.fragment.PostNewJobFragment;
import com.git.hubreeh.view.employer.fragment.SettingsFragment;
import com.git.hubreeh.view.jobseeker.fragment.HomeMessages;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeHandlerBusiness extends AppCompatActivity {

    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_three_dots)
    ImageView ivThreeDots;
    @BindView(R.id.tv_status)
    AppCompatTextView tvStatus;
    @BindView(R.id.toolbar_homescreen)
    Toolbar toolbar;
    @BindView(R.id.tabs_business_home_handler)
    TabLayout tabsBusinessHomeHandler;
    @BindView(R.id.viewPager_business)
    ViewPager viewPagerBusiness;

    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_handler_business);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        tvTitle.setText("My Created Jobs");

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerBusiness.setAdapter(viewPagerAdapter);
        setupTabs();
        tabsBusinessHomeHandler.setupWithViewPager(viewPagerBusiness);
    }


    private void setupTabs() {
        tabsBusinessHomeHandler.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabsBusinessHomeHandler.getTabAt(0).setIcon(R.drawable.ic_event_black_24dp);
                tabsBusinessHomeHandler.getTabAt(1).setIcon(R.drawable.ic_customer);
                tabsBusinessHomeHandler.getTabAt(2).setIcon(R.drawable.ic_chat_bubble_black_24dp);
                tabsBusinessHomeHandler.getTabAt(3).setIcon(R.drawable.ic_transaction);
                tabsBusinessHomeHandler.getTabAt(4).setIcon(R.drawable.ic_settings);

                if (tabsBusinessHomeHandler.getSelectedTabPosition() == 0) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                } else if (tabsBusinessHomeHandler.getSelectedTabPosition() == 1) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                } else if (tabsBusinessHomeHandler.getSelectedTabPosition() == 2) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                } else if (tabsBusinessHomeHandler.getSelectedTabPosition() == 3) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                } else if (tabsBusinessHomeHandler.getSelectedTabPosition() == 4) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            if (position == 0) {
                fragment = new BusinessHomeJobs();
            } else if (position == 1) {
                fragment = new PostNewJobFragment();
            } else if (position == 2) {
                fragment = new HomeMessages();
            } else if (position == 3) {
                fragment = new BusinessTransactions();
            } else if (position == 4) {
                fragment = new SettingsFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }
}
