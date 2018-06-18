package com.git.hubreeh.view.employer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.view.jobseeker.fragment.FragmentAllTransaction;

/**
 * Created by Hp on 1/26/2018.
 */

public class BusinessTransactions extends Fragment {
    View view;

    TabLayout tabsTransaction;
    ViewPager viewPagerTransaction;
    ViewPagerAdapter vpAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_business_transaction,container,false);

        initialize();

        vpAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerTransaction.setAdapter(vpAdapter);
        tabsTransaction.setupWithViewPager(viewPagerTransaction);

        return view;
    }

    private void initialize() {
        tabsTransaction=view.findViewById(R.id.tabs_transaction);
        viewPagerTransaction=view.findViewById(R.id.viewPager_transaction);

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentAllTransaction();
            } else if (position == 1) {
                fragment = new FragmentWithdrawalRequest();
            } else if (position == 2) {
                fragment = new FragmentViewMilestone();
            }




            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "All Transaction";
            }else if (position == 1) {
                title = "Withdrawal Request";
            }else if (position == 2) {
                title = "View Milestone";
            }

            return title;
        }
    }


}
