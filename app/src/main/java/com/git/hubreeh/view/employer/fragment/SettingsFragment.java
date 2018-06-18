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
import com.git.hubreeh.view.jobseeker.fragment.FragmentMsgContacts;
import com.git.hubreeh.view.jobseeker.fragment.FragmentMsgMessages;
import com.git.hubreeh.view.jobseeker.fragment.HomeMessages;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/25/2018.
 */

public class SettingsFragment extends Fragment {
    View view;
    @BindView(R.id.tabs_settings)
    TabLayout tabsSettings;
    @BindView(R.id.viewPager_settings)
    ViewPager viewPagerSettings;

    ViewPagerAdapter vpAdapter;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_business_settings, container, false);
        unbinder = ButterKnife.bind(this, view);

        vpAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerSettings.setAdapter(vpAdapter);
        tabsSettings.setupWithViewPager(viewPagerSettings);

        return view;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentBusinessProfile();
            } else if (position == 1) {
                fragment = new FragmentBusinessNotification();
            } else if (position == 2) {
                fragment = new FragmentBusinessMembership();
            } else if (position == 3) {
                fragment = new FragmentBusinessPayment();
            } else if (position == 4) {
                fragment = new FragmentBusinessTrust();
            } else if (position == 5) {
                fragment = new FragmentBusinessAccounts();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "Profile";
            } else if (position == 1) {
                title = "Notification";
            }
            else if (position == 2) {
                title = "Membership";
            }
            else if (position == 3) {
                title = "Payments & Financial";
            }
            else if (position == 4) {
                title = "Trust & Verification";
            }
            else if (position == 5) {
                title = "Account";
            }
            return title;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
