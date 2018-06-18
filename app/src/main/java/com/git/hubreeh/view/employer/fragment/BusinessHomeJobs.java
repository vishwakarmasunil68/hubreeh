package com.git.hubreeh.view.employer.fragment;

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
import com.git.hubreeh.adapter.employer.BusinessHomeAdapter;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.employer.BusinessHomeModel;
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
 * Created by Hp on 1/23/2018.
 */

public class BusinessHomeJobs extends Fragment {
    View view;
    @BindView(R.id.recycler_business_home)
    RecyclerView recyclerBusinessHome;
    BusinessHomeAdapter mAdapter;
    List<BusinessHomeModel.ResultBean> businessHomeModels;

    Unbinder unbinder;
    ProgressView progressView;
    ApiInterface apiInterface;
    @BindView(R.id.root)
    LinearLayout root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_business_post_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        businessHomeModels = new ArrayList<>();
        progressView = new ProgressView(getActivity());
        apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
        recyclerBusinessHome.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new BusinessHomeAdapter(getActivity(), businessHomeModels);
        recyclerBusinessHome.setAdapter(mAdapter);


        getData();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void getData() {
        progressView.showLoader();
        Call<BusinessHomeModel> call = apiInterface.get_jobs(MyApplication.readStringPref(PrefData.PREFNUSINESSID));
        call.enqueue(new Callback<BusinessHomeModel>() {
            @Override
            public void onResponse(Call<BusinessHomeModel> call, Response<BusinessHomeModel> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {

                    businessHomeModels.clear();
                    businessHomeModels.addAll(response.body().getResult());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), getActivity());
                }
            }

            @Override
            public void onFailure(Call<BusinessHomeModel> call, Throwable t) {
                progressView.hideLoader();
                t.printStackTrace();
            }
        });
    }

}
