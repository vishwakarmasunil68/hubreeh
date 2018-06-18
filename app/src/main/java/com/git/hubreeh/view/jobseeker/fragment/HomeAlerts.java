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
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.AlertAdapter;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.jobseeker.AlertBean;
import com.git.hubreeh.utility.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/19/2017.
 */

public class HomeAlerts extends Fragment {
    View view;
    AlertAdapter mAdapter;
    List<AlertBean.ResultBean> alertBeans = new ArrayList<>();
    ProgressView progressView;
    @BindView(R.id.recycler_profile)
    RecyclerView recyclerProfile;
    @BindView(R.id.root)
    LinearLayout root;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_alerts, container, false);
        progressView = new ProgressView(getActivity());
        initialize();

        recyclerProfile.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new AlertAdapter(getActivity(), alertBeans);
        recyclerProfile.setAdapter(mAdapter);
//        getAlerts();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void getAlerts() {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        Call<AlertBean> call = apiInterface.get_all_notification(MyApplication.readStringPref(PrefData.PREF_JOBID));

        call.enqueue(new Callback<AlertBean>() {
            @Override
            public void onResponse(Call<AlertBean> call, Response<AlertBean> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    alertBeans.clear();
                    alertBeans.addAll(response.body().getResult());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), getActivity());
                }
            }

            @Override
            public void onFailure(Call<AlertBean> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }

    private void initialize() {
        recyclerProfile = view.findViewById(R.id.recycler_profile);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}