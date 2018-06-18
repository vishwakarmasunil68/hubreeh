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
import com.git.hubreeh.adapter.jobseeker.OfferedAdapter;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.jobseeker.JobModel;
import com.git.hubreeh.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

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

public class FragmentMyJobsOffered extends Fragment {
    View view;
    @BindView(R.id.recycler_booked)
    RecyclerView recyclerBooked;
    Unbinder unbinder;
    OfferedAdapter mAdapter;
    List<JobModel.OfferedBean> offeredBeans = new ArrayList<>();
    ProgressView progressView;
    @BindView(R.id.root)
    LinearLayout root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myjobs_offered, container, false);
        unbinder = ButterKnife.bind(this, view);
        progressView = new ProgressView(getActivity());
        recyclerBooked.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new OfferedAdapter(getActivity(), offeredBeans);
        recyclerBooked.setAdapter(mAdapter);
//        try {
////            getJobs();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getJobs() throws JSONException {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        JSONObject object1 = new JSONObject(MyApplication.readStringPref(PrefData.PREF_JOB_DATA));
        JSONObject object = object1.getJSONObject("result");
        Call<JobModel> call = apiInterface.get_jobs(object.getString("category_id"), MyApplication.readStringPref(PrefData.PREF_JOBID));
        call.enqueue(new Callback<JobModel>() {
            @Override
            public void onResponse(Call<JobModel> call, Response<JobModel> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    offeredBeans.clear();
                    offeredBeans.addAll(response.body().getOffered());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), getActivity());
                }
            }

            @Override
            public void onFailure(Call<JobModel> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }
}
