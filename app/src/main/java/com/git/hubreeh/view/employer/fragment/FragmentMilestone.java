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
import com.git.hubreeh.view.jobseeker.fragment.FragmentCreateMilestone;

/**
 * Created by Hp on 1/26/2018.
 */
public class FragmentMilestone extends Fragment {
    View view;
    TabLayout tabsMilestone;
    ViewPager viewPagerMilestone;
    ViewPagerAdapter vpAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_milestone, container, false);

        initialize();

        vpAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerMilestone.setAdapter(vpAdapter);
        tabsMilestone.setupWithViewPager(viewPagerMilestone);


        return view;
    }

    private void initialize() {
        viewPagerMilestone=view.findViewById(R.id.viewPager_mileston);
        tabsMilestone=view.findViewById(R.id.tabs_milstone);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentViewMilestone();
            } else if (position == 1) {
                fragment = new FragmentCreateMilestone();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "View Milestone";
            } else if (position == 1) {
                title = "Create Milestone";
            }
            return title;
        }
    }


}
