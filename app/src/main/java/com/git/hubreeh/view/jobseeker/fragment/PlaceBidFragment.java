package com.git.hubreeh.view.jobseeker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.jobseeker.activity.ProjectDetailsActivity;
import com.google.gson.JsonObject;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/20/2017.
 */

public class PlaceBidFragment extends Fragment {
    View view;
    AppCompatTextView cancel;
    AppCompatButton btnPlaceBid;
    @BindView(R.id.jobName)
    AppCompatTextView jobName;
    @BindView(R.id.budget)
    AppCompatTextView budget;
    @BindView(R.id.amount)
    AppCompatEditText amount;
    @BindView(R.id.day)
    AppCompatEditText day;
    Unbinder unbinder;
    ProgressView progressView;
    @BindView(R.id.root)
    ScrollView root;
    @BindView(R.id.dec)
    AppCompatEditText dec;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_place_bid, container, false);
        progressView = new ProgressView(getActivity());
        unbinder = ButterKnife.bind(this, view);
        initialize();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectDetailsActivity.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        btnPlaceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bid = amount.getText().toString();
                String days = day.getText().toString();
                String des = dec.getText().toString();
                if (Validation.nullValidator(bid)) {
                    Utils.showSnackBar(root, "Enter Amount", amount, getActivity());
                } else if (Validation.nullValidator(days)) {
                    Utils.showSnackBar(root, "Enter Days", day, getActivity());
                } else if (Validation.nullValidator(des)) {
                    Utils.showSnackBar(root, "Enter Description", day, getActivity());
                } else {
                    placeBid(bid, days,des);
                }


            }
        });

        jobName.setText(getArguments().getString("name"));
        budget.setText(getArguments().getString("amount") + " AED");


        return view;
    }

    private void initialize() {

        cancel = view.findViewById(R.id.tv_place_bid_cancel);
        btnPlaceBid = view.findViewById(R.id.btn_sliding_place_bid);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void placeBid(String bid, String days,String des) {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.place_bid(MyApplication.readStringPref(PrefData.PREF_JOBID), getArguments().getString("jobID"), bid, days,des);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressView.hideLoader();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("status").equalsIgnoreCase("1")) {
                        ProjectDetailsActivity.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        Utils.showSnackBar(root, object.getString("message"), getActivity());
                    } else {
                        Utils.showSnackBar(root, object.getString("message"), getActivity());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }

}
