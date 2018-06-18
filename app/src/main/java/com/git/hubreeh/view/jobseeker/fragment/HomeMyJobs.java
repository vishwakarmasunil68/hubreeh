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
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.jobseeker.JobModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 12/19/2017.
 */

public class HomeMyJobs extends Fragment {
    View view;
    public TabLayout tabsLayout;
    public ViewPager myJobsViewPager;
    public  ViewPagerAdapter vpAdapter;
    ProgressView progressView;

    List<JobModel.AppliedBean> appliedBeans;
    List<JobModel.BookedBean> bookedBeans;
    List<JobModel.OfferedBean> offeredBeans;
    @BindView(R.id.root)
    LinearLayout root;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_myjobs, container, false);
        progressView = new ProgressView(getActivity());
        unbinder = ButterKnife.bind(this, view);
        initialize();

        vpAdapter = new ViewPagerAdapter(getChildFragmentManager());
        myJobsViewPager.setAdapter(vpAdapter);
        tabsLayout.setupWithViewPager(myJobsViewPager);

        return view;
    }

    private void initialize() {
        tabsLayout = view.findViewById(R.id.tabs_myjob_fragment);
        myJobsViewPager = view.findViewById(R.id.viewPager_myjobs_fragment);

        appliedBeans = new ArrayList<>();
        bookedBeans = new ArrayList<>();
        offeredBeans = new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public  class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new FragmentMyJobsBooked();
            } else if (position == 1) {
                fragment = new FragmentMyJobsOffered();
            } else if (position == 2) {
                fragment = new FragmentMyJobsApplied();
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
                title = "Booked";
            } else if (position == 1) {
                title = "Offered";
            } else if (position == 2) {
                title = "Applied";
            }
            return title;
        }
    }


}
