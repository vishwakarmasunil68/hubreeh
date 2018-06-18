package com.git.hubreeh.view.jobseeker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.HospitalityAdapter;
import com.git.hubreeh.view.jobseeker.activity.SignUpJobSeekerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 12/16/2017.
 */

public class FragmentHospitality extends Fragment {

    View view;
    HospitalityAdapter mAdapter;
    @BindView(R.id.recycler_hospitality)
    RecyclerView hospitalityRecycler;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hospitality, container, false);
        unbinder = ButterKnife.bind(this, view);
        hospitalityRecycler.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        hospitalityRecycler.setHasFixedSize(true);
        mAdapter = new HospitalityAdapter(getActivity(), SignUpJobSeekerActivity.hospitalityBeans);
        hospitalityRecycler.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
