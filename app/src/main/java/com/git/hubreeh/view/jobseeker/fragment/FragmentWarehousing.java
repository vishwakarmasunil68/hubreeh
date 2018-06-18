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
import com.git.hubreeh.adapter.jobseeker.WarehousingAdapter;
import com.git.hubreeh.view.jobseeker.activity.SignUpJobSeekerActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 12/16/2017.
 */

public class FragmentWarehousing extends Fragment {
    View view;
    @BindView(R.id.recycler_warehousing)
    RecyclerView recyclerWarehousing;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_warehousing, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerWarehousing.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        recyclerWarehousing.setHasFixedSize(true);
        recyclerWarehousing.setAdapter(new WarehousingAdapter(getActivity(), SignUpJobSeekerActivity.warehousingBeans));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
