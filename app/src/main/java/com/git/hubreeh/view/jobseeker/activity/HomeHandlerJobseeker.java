package com.git.hubreeh.view.jobseeker.activity;

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
import android.view.View;
import android.widget.ImageView;

import com.git.hubreeh.R;
import com.git.hubreeh.view.jobseeker.fragment.HomeAlerts;
import com.git.hubreeh.view.jobseeker.fragment.HomeMessages;
import com.git.hubreeh.view.jobseeker.fragment.HomeMyJobs;
import com.git.hubreeh.view.jobseeker.fragment.HomeProfile;
import com.git.hubreeh.view.jobseeker.fragment.HomeSearch;
import com.git.hubreeh.view.jobseeker.fragment.HomeTransactions;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class HomeHandlerJobseeker extends AppCompatActivity {
    Toolbar toolbar;
    public static TabLayout tabsHomeHandler;
    public static AppCompatTextView tv_title, tv_status;
    public static ImageView icons, dots;

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_handler);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);

        initialize();
        setSupportActionBar(toolbar);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        setupTabs();
        tabsHomeHandler.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tv_title.setText("My Jobs");
                    tv_status.setVisibility(View.GONE);
                    icons.setVisibility(View.GONE);
                    dots.setVisibility(View.GONE);
                } else if (position == 1) {
                    HomeHandlerJobseeker.tv_title.setText("Browse");
                    tv_status.setVisibility(View.GONE);
                    icons.setVisibility(View.GONE);
                    dots.setVisibility(View.GONE);
                } else if (position == 2) {
                    HomeHandlerJobseeker.tv_title.setText("Messages");
                    tv_status.setVisibility(View.GONE);
                    icons.setVisibility(View.VISIBLE);
                    icons.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black));
                    icons.setPaddingRelative(10, 10, 50, 10);
                    dots.setVisibility(View.GONE);
                } else if (position == 3) {
                    HomeHandlerJobseeker.tv_title.setText("Alert");
                    icons.setVisibility(View.GONE);
                    tv_status.setVisibility(View.VISIBLE);
                    dots.setVisibility(View.GONE);
                } else if (position == 4) {
                    HomeHandlerJobseeker.tv_title.setText("Transactions");
                    icons.setVisibility(View.GONE);
                    tv_status.setVisibility(View.GONE);
                    dots.setVisibility(View.GONE);
                } else if (position == 5) {
                    HomeHandlerJobseeker.tv_title.setText("Profile");
                    tv_status.setVisibility(View.GONE);
                    icons.setVisibility(View.GONE);
                    dots.setVisibility(View.VISIBLE);
                    dots.setImageDrawable(getResources().getDrawable(R.drawable.three_dots));
                    dots.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(HomeHandlerJobseeker.this, CompanyPageActivity.class));
                            overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initialize() {
        toolbar = findViewById(R.id.toolbar_homescreen);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setVisibility(View.VISIBLE);
        tv_status = findViewById(R.id.tv_status);
        icons = findViewById(R.id.iv_icon);
        dots = findViewById(R.id.iv_three_dots);

        tabsHomeHandler = findViewById(R.id.tabs_home_handler);
        viewPager = findViewById(R.id.viewPager);
    }

    private void setupTabs() {
        tabsHomeHandler.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabsHomeHandler.getTabAt(0).setIcon(R.drawable.ic_event_black_24dp);
                tabsHomeHandler.getTabAt(1).setIcon(R.drawable.ic_search);
                tabsHomeHandler.getTabAt(2).setIcon(R.drawable.ic_chat_bubble_black_24dp);
                tabsHomeHandler.getTabAt(3).setIcon(R.drawable.ic_notifications_black_24dp);
                tabsHomeHandler.getTabAt(4).setIcon(R.drawable.ic_transaction);
                tabsHomeHandler.getTabAt(5).setIcon(R.drawable.ic_person_black_24dp);

                if (tabsHomeHandler.getSelectedTabPosition() == 0) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                } else if (tabsHomeHandler.getSelectedTabPosition() == 1) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                } else if (tabsHomeHandler.getSelectedTabPosition() == 2) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                } else if (tabsHomeHandler.getSelectedTabPosition() == 3) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                } else if (tabsHomeHandler.getSelectedTabPosition() == 4) {
                    tab.getIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                } else if (tabsHomeHandler.getSelectedTabPosition() == 5) {
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
                fragment = new HomeMyJobs();
            } else if (position == 1) {
                fragment = new HomeSearch();
            } else if (position == 2) {
                fragment = new HomeMessages();
            } else if (position == 3) {
                fragment = new HomeAlerts();
            } else if (position == 4) {
                fragment = new HomeTransactions();
            } else if (position == 5) {
                fragment = new HomeProfile();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 6;
        }

    }
}
