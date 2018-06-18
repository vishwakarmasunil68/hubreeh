package com.git.hubreeh.view.jobseeker.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.git.hubreeh.R;
import com.git.hubreeh.view.jobseeker.fragment.MembershipBasicFragment;
import com.git.hubreeh.view.jobseeker.fragment.MembershipIntroFragment;
import com.git.hubreeh.view.jobseeker.fragment.MembershipPlusFragment;
import com.git.hubreeh.view.jobseeker.fragment.MembershipPremierFragment;
import com.git.hubreeh.view.jobseeker.fragment.MembershipProfessionalFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MembershipActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static TabLayout tabsMembership;

    ViewPager viewpagerMemership;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        initialize();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Membership");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpagerMemership.setAdapter(viewPagerAdapter);
        setupTabs();
        tabsMembership.setupWithViewPager(viewpagerMemership);


    }

    private void initialize() {
        toolbar=findViewById(R.id.toolbar);
        tabsMembership = findViewById(R.id.tab_membership);
        viewpagerMemership = findViewById(R.id.viewpager_membership);
    }

    private void setupTabs() {
        tabsMembership.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabsMembership.getTabAt(0).setIcon(R.drawable.radiobutton_non_selected);
                tabsMembership.getTabAt(1).setIcon(R.drawable.radiobutton_non_selected);
                tabsMembership.getTabAt(2).setIcon(R.drawable.radiobutton_non_selected);
                tabsMembership.getTabAt(3).setIcon(R.drawable.radiobutton_non_selected);
                tabsMembership.getTabAt(4).setIcon(R.drawable.radiobutton_non_selected);

                if (tabsMembership.getSelectedTabPosition() == 0) {
                    tabsMembership.getTabAt(0).setIcon(R.drawable.radiobutton_selected);
                } else if (tabsMembership.getSelectedTabPosition() == 1) {
                    tabsMembership.getTabAt(1).setIcon(R.drawable.radiobutton_selected);
                } else if (tabsMembership.getSelectedTabPosition() == 2) {
                    tabsMembership.getTabAt(2).setIcon(R.drawable.radiobutton_selected);
                } else if (tabsMembership.getSelectedTabPosition() == 3) {
                    tabsMembership.getTabAt(3).setIcon(R.drawable.radiobutton_selected);
                } else if (tabsMembership.getSelectedTabPosition() == 4) {
                    tabsMembership.getTabAt(4).setIcon(R.drawable.radiobutton_selected);
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
                fragment = new MembershipIntroFragment();
            } else if (position == 1) {
                fragment = new MembershipBasicFragment();
            } else if (position == 2) {
                fragment = new MembershipPlusFragment();
            } else if (position == 3) {
                fragment = new MembershipProfessionalFragment();
            } else if (position == 4) {
                fragment = new MembershipPremierFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "Intro";
            } else if (position == 1) {
                title = "Basic";
            } else if (position == 2) {
                title = "Plus";
            }else if (position == 3) {
                title = "Professional";
            }else if (position == 4) {
                title = "Premier";
            }
            return title;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
