package com.git.hubreeh.view.jobseeker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponseGetJob;
import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.DaysAdapter;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.model.jobseeker.DaysModel;
import com.git.hubreeh.pojo.user.JobUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.TagUtils;
import com.git.hubreeh.utility.ToastClass;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.jobseeker.activity.SignUpJobSeekerActivity;
import com.git.hubreeh.webservice.WebServiceBase;
import com.git.hubreeh.webservice.WebServicesCallBack;
import com.git.hubreeh.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/16/2017.
 */

public class SignupFragmentSetYourRate extends FragmentController {
    View view;


    @BindView(R.id.et_signup_job_rate)
    AppCompatEditText etSignupJobRate;
    @BindView(R.id.cb_work_48_hour_per_week)
    CheckBox cbWork48HourPerWeek;
    @BindView(R.id.cb_full_time)
    CheckBox cbFullTime;
    @BindView(R.id.recycler_days)
    RecyclerView recyclerDays;
    @BindView(R.id.tv_select_all)
    AppCompatTextView tvSelectAll;
    @BindView(R.id.root_rate_and_availability)
    LinearLayout rootRateAndAvailability;

    DaysAdapter mAdapter;
    ProgressView progressView;
    String isAgree48Hours = "", fullTime = "";
    boolean clicked = false;
    Unbinder unbinder;
    @BindView(R.id.btn_rate_continue)
    AppCompatButton btnContinue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_set_your_rate, container, false);
        setUpView(getActivity(),this,view);
        unbinder = ButterKnife.bind(this, view);
        etSignupJobRate.setText(SignUpJobSeekerActivity.rate);
        cbWork48HourPerWeek.setChecked(SignUpJobSeekerActivity.workhours);
        cbFullTime.setChecked(SignUpJobSeekerActivity.fullTime);

        progressView = new ProgressView(getActivity());
        recyclerDays.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new DaysAdapter(getActivity(), SignUpJobSeekerActivity.daysModels);
        recyclerDays.setAdapter(mAdapter);
        prepareData();
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbWork48HourPerWeek.isChecked()) {
                    isAgree48Hours = "1";
                    SignUpJobSeekerActivity.workhours = true;
                } else {
                    isAgree48Hours = "0";
                    SignUpJobSeekerActivity.workhours = false;
                }

                if (cbFullTime.isChecked()) {
                    fullTime = "1";
                    SignUpJobSeekerActivity.fullTime = true;
                } else {
                    fullTime = "0";
                    SignUpJobSeekerActivity.fullTime = false;
                }

                if (Validation.nullValidator(etSignupJobRate.getText().toString().trim())) {
                    Utils.showSnackBar(rootRateAndAvailability, "Please Enter the Rate", etSignupJobRate, getActivity());
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("rate", etSignupJobRate.getText().toString());
                    map.put("isAgree", isAgree48Hours);
                    map.put("isFull", fullTime);
                    map.put("user_id", MyApplication.readStringPref(PrefData.PREF_JOBID));
                    for (int i = 0; i < SignUpJobSeekerActivity.daysModels.size(); i++) {
                        String am = "0";
                        String pm = "0";
                        if (SignUpJobSeekerActivity.daysModels.get(i).isAmChecked()) {
                            am = "1";
                        }
                        if (SignUpJobSeekerActivity.daysModels.get(i).isPmChecked()) {
                            pm = "1";
                        }
                        map.put(SignUpJobSeekerActivity.daysModels.get(i).getDays().toLowerCase(), am + "," + pm);
                    }
                    SignUpJobSeekerActivity.rate = etSignupJobRate.getText().toString().trim();
                    connectApiToUpdateRateAndAvailability(map);
                }
            }
        });

        tvSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked) {
                    clicked = true;
                    for (int i = 0; i < SignUpJobSeekerActivity.daysModels.size(); i++) {
                        SignUpJobSeekerActivity.daysModels.get(i).setPmChecked(true);
                        SignUpJobSeekerActivity.daysModels.get(i).setAmChecked(true);
                    }
                } else {
                    clicked = false;
                    for (int i = 0; i < SignUpJobSeekerActivity.daysModels.size(); i++) {
                        SignUpJobSeekerActivity.daysModels.get(i).setPmChecked(false);
                        SignUpJobSeekerActivity.daysModels.get(i).setAmChecked(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });


        if (getActivity().getIntent().hasExtra("direct2")) {
            try {
                JSONObject object = new JSONObject(getActivity().getIntent().getStringExtra("obj"));
                etSignupJobRate.setText(object.getString("rate"));


                if (object.getString("isFull").equalsIgnoreCase("0")) {
                    cbFullTime.setChecked(false);
                } else {
                    cbFullTime.setChecked(true);
                }

                if (object.getString("isAgree").equalsIgnoreCase("0")) {
                    cbWork48HourPerWeek.setChecked(false);
                } else {
                    cbWork48HourPerWeek.setChecked(true);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return view;
    }


    private void connectApiToUpdateRateAndAvailability(Map<String, String> map) {
        Log.d(TagUtils.getTag(),"map:-"+map);
//        progressView.showLoader();
//        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
//        Call<ApiResponseGetJob> call = apiInterface.RateAndAvailability(map);
//        call.enqueue(new Callback<ApiResponseGetJob>() {
//            @Override
//            public void onResponse(Call<ApiResponseGetJob> call, Response<ApiResponseGetJob> response) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                if (response.body().getStatus() == 1) {
//                    ((SignUpJobSeekerActivity) getActivity()).changeFragment(new SignupFragmentWhatDoYouLike(), "fragmentlooklike", R.id.frame_container);
//                } else {
//                    Utils.showSnackBar(rootRateAndAvailability, response.body().getMessage(), getActivity());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseGetJob> call, Throwable t) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                t.printStackTrace();
//            }
//        });
//        activityManager.startFragment(R.id.frame_container,new SignupFragmentWhatDoYouLike());

        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("monday",map.get("monday").toString()));
        nameValuePairs.add(new BasicNameValuePair("tuesday",map.get("tuesday").toString()));
        nameValuePairs.add(new BasicNameValuePair("wednesday",map.get("wednesday").toString()));
        nameValuePairs.add(new BasicNameValuePair("thursday",map.get("thursday").toString()));
        nameValuePairs.add(new BasicNameValuePair("friday",map.get("friday").toString()));
        nameValuePairs.add(new BasicNameValuePair("saturday",map.get("saturday").toString()));
        nameValuePairs.add(new BasicNameValuePair("sunday",map.get("sunday").toString()));
        nameValuePairs.add(new BasicNameValuePair("rate_per_hour",map.get("rate").toString()));
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.jobUserPOJO.getUserId()));

        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optInt("status")==1){
                        Gson gson=new Gson();
                        JobUserPOJO jobUserPOJO =gson.fromJson(jsonObject.optJSONObject("result").toString(),JobUserPOJO.class);
                        Constants.jobUserPOJO=jobUserPOJO;
                        Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.JOB_USER_POJO,jsonObject.optJSONObject("result").toString());
                        activityManager.startFragment(R.id.frame_container,new SignupFragmentWhatDoYouLike());
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CREATE_USER",true).execute(WebServicesUrls.USER_REGISTER_4);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignUpJobSeekerActivity) getActivity()).setUpToolbar("Set your rate and availability", "5/8", true, true);
    }


    private void prepareData() {
        if (SignUpJobSeekerActivity.daysModels.isEmpty()) {
            SignUpJobSeekerActivity.daysModels.add(new DaysModel("Monday", false, false));
            SignUpJobSeekerActivity.daysModels.add(new DaysModel("Tuesday", false, false));
            SignUpJobSeekerActivity.daysModels.add(new DaysModel("Wednesday", false, false));
            SignUpJobSeekerActivity.daysModels.add(new DaysModel("Thursday", false, false));
            SignUpJobSeekerActivity.daysModels.add(new DaysModel("Friday", false, false));
            SignUpJobSeekerActivity.daysModels.add(new DaysModel("Saturday", false, false));
            SignUpJobSeekerActivity.daysModels.add(new DaysModel("Sunday", false, false));
        }
        mAdapter.notifyDataSetChanged();
    }


}
