package com.git.hubreeh.view.jobseeker.fragment;

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
import android.widget.LinearLayout;

import com.git.hubreeh.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 12/19/2017.
 */

public class HomeMessages extends Fragment {
    View view;


    ViewPagerAdapter vpAdapter;
    @BindView(R.id.tabs_messages)
    TabLayout tabLayout;
    @BindView(R.id.viewPager_messages)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.root)
    LinearLayout root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_messages, container, false);
        unbinder = ButterKnife.bind(this, view);

        vpAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentMsgMessages();
            } else if (position == 1) {
                fragment = new FragmentMsgContacts();
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
                title = "Messages";
            } else if (position == 1) {
                title = "Contacts";
            }
            return title;
        }
    }




}