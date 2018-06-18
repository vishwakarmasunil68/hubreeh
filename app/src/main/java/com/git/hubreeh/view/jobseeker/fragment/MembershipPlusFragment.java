package com.git.hubreeh.view.jobseeker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.MembershipPointsAdapter;
import com.git.hubreeh.model.jobseeker.MembershipPointsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/6/2018.
 */

public class MembershipPlusFragment extends Fragment {
    View view;

    RecyclerView recyclermembershipPoints;
    MembershipPointsAdapter mAdapter;
    List<MembershipPointsModel> membershipPointsModels=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_membership_plus,container,false);

        initialize();


        recyclermembershipPoints.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new MembershipPointsAdapter(getActivity(), membershipPointsModels);
        recyclermembershipPoints.setAdapter(mAdapter);

        prepareData();

        return view;
    }


    private void initialize() {
        recyclermembershipPoints=view.findViewById(R.id.recycler_membership_points);
    }


    private void prepareData() {
        MembershipPointsModel model=new MembershipPointsModel("80 Skills");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("Custom Cover Photo");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("10 Employer Followings");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("Free Project Extensions");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("Unblock Rewards");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("Free Sealed Project");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("Preferred Job Seeker");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("Project Bookmarks");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("100 Bid Per Month");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("5 External Invoicing");
        membershipPointsModels.add(model);
        model=new MembershipPointsModel("Daily Withdrawals");
        membershipPointsModels.add(model);
    }

}
