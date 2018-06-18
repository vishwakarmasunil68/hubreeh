package com.git.hubreeh.view.jobseeker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.jobseeker.CategoryBean;
import com.git.hubreeh.pojo.user.JobUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.TagUtils;
import com.git.hubreeh.utility.ToastClass;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.jobseeker.activity.HomeHandlerJobseeker;
import com.git.hubreeh.view.jobseeker.activity.SignUpJobSeekerActivity;
import com.git.hubreeh.webservice.WebServiceBase;
import com.git.hubreeh.webservice.WebServicesCallBack;
import com.git.hubreeh.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/16/2017.
 */

public class SignupFragmentWhatCanYouDo extends FragmentController {
    View view;
    @BindView(R.id.btn_signup_continue_four)
    AppCompatButton btnSignupContinueFour;
    Unbinder unbinder;

    ProgressView progressView;
    @BindView(R.id.root_choose_category)
    LinearLayout rootChooseCategory;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp_select_skills)
    ViewPager vpSkills;

    ViewPagerAdapter vpAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_what_you_can_do, container, false);
        setUpView(getActivity(), this, view);
        unbinder = ButterKnife.bind(this, view);
        progressView = new ProgressView(getActivity());


        connectApiToGetCategories();


        btnSignupContinueFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray array = new JSONArray();
                for (int i = 0; i < SignUpJobSeekerActivity.hospitalityBeans.size(); i++) {
                    if (SignUpJobSeekerActivity.hospitalityBeans.get(i).isSelect()) {
                        array.put(SignUpJobSeekerActivity.hospitalityBeans.get(i).getCategory_id());
                    }
                }
                for (int j = 0; j < SignUpJobSeekerActivity.warehousingBeans.size(); j++) {
                    if (SignUpJobSeekerActivity.warehousingBeans.get(j).isSeleted()) {
                        array.put(SignUpJobSeekerActivity.warehousingBeans.get(j).getCategory_id());
                    }
                }

                if (array.length() > 0) {
                    connectApiToUpdateCategoty(array);
                } else {
                    Utils.showSnackBar(rootChooseCategory, "Please Select At least 1 Job Role", getActivity());
                }
            }

        });

        return view;
    }

    private void connectApiToUpdateCategoty(JSONArray array) {
//        progressView.showLoader();
//        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
//        Call<ApiResponseGetJob> call = apiInterface.update_categories(array.toString().replace("[", "").replace("]", "").replaceAll("\"", ""), MyApplication.readStringPref(PrefData.PREF_JOBID));
//        call.enqueue(new Callback<ApiResponseGetJob>() {
//            @Override
//            public void onResponse(Call<ApiResponseGetJob> call, Response<ApiResponseGetJob> response) {
//                progressView.hideLoader();
//                if (response.body().getStatus() == 1) {
//                    ((SignUpJobSeekerActivity) getActivity()).changeFragment(new SignupFragmentSetYourRate(), "fragmentrate", R.id.frame_container);
//                } else {
//                    Utils.showSnackBar(rootChooseCategory, response.body().getMessage(), getActivity());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseGetJob> call, Throwable t) {
//                progressView.hideLoader();
//                t.printStackTrace();
//            }
//        });
//        activityManager.startFragment(R.id.frame_container,new SignupFragmentSetYourRate());


//        startActivity(new Intent(getActivity(), HomeHandlerJobseeker.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//        getActivity().finish();


        String categories = "";

        List<String> selectedIds = new ArrayList<>();
        for (int i = 0; i < SignUpJobSeekerActivity.hospitalityBeans.size(); i++) {
            CategoryBean.HospitalityBean hospitalityBean = SignUpJobSeekerActivity.hospitalityBeans.get(i);
            selectedIds.add(hospitalityBean.getCategory_id());
        }

        for (int i = 0; i < SignUpJobSeekerActivity.warehousingBeans.size(); i++) {
            CategoryBean.WarehousingBean warehousingBean = SignUpJobSeekerActivity.warehousingBeans.get(i);
            selectedIds.add(warehousingBean.getCategory_id());
        }

        for (int i = 0; i < selectedIds.size(); i++) {
            if (i == (selectedIds.size() - 1)) {
                categories += selectedIds.get(i);
            } else {
                categories += selectedIds.get(i) + ",";
            }
        }

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.jobUserPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("category_id", categories));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optBoolean("isSuccess")) {
                        Gson gson = new Gson();
                        JobUserPOJO jobUserPOJO = gson.fromJson(jsonObject.optJSONObject("Result").toString(), JobUserPOJO.class);
                        Constants.jobUserPOJO = jobUserPOJO;
                        Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.JOB_USER_POJO, jsonObject.optJSONObject("Result").toString());
                        startActivity(new Intent(getActivity(), HomeHandlerJobseeker.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        getActivity().finish();
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CREATE_USER", true).execute(WebServicesUrls.USER_REGISTER_3);

        Log.d(TagUtils.getTag(), "selected categories:-" + categories);
    }

    private void connectApiToGetCategories() {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        Call<CategoryBean> call = apiInterface.get_categories(MyApplication.readStringPref(PrefData.PREF_JOBID));

        call.enqueue(new Callback<CategoryBean>() {
            @Override
            public void onResponse(Call<CategoryBean> call, Response<CategoryBean> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    SignUpJobSeekerActivity.hospitalityBeans.clear();
                    SignUpJobSeekerActivity.warehousingBeans.clear();
                    SignUpJobSeekerActivity.hospitalityBeans.addAll(response.body().getHospitality());
                    SignUpJobSeekerActivity.warehousingBeans.addAll(response.body().getWarehousing());
                    List<String> ids = new ArrayList<>();
                    if (response.body().getIsSelected() != null) {
                        if (!response.body().getIsSelected().equalsIgnoreCase("")) {
                            ids = Arrays.asList(response.body().getIsSelected().split(","));
                        }
                    }

                    Log.e("ids", ids.toString());

                    setCategories(ids);
                    if (isAdded()) {
                        vpAdapter = new ViewPagerAdapter(getChildFragmentManager());
                        vpSkills.setAdapter(vpAdapter);
                        tabs.setupWithViewPager(vpSkills);
                    }
                } else {
                    Utils.showSnackBar(rootChooseCategory, response.body().getMessage(), getActivity());
                }
            }

            @Override
            public void onFailure(Call<CategoryBean> call, Throwable t) {
                progressView.hideLoader();
                t.printStackTrace();
            }
        });
    }

    private void setCategories(List<String> ids) {
        if (!ids.isEmpty()) {
            for (int i = 0; i < SignUpJobSeekerActivity.hospitalityBeans.size(); i++) {
                if (ids.contains(SignUpJobSeekerActivity.hospitalityBeans.get(i).getCategory_id())) {
                    SignUpJobSeekerActivity.hospitalityBeans.get(i).setSelect(true);
                } else {
                    SignUpJobSeekerActivity.hospitalityBeans.get(i).setSelect(false);
                }
            }

            for (int i = 0; i < SignUpJobSeekerActivity.warehousingBeans.size(); i++) {
                if (ids.contains(SignUpJobSeekerActivity.warehousingBeans.get(i).getCategory_id())) {
                    SignUpJobSeekerActivity.warehousingBeans.get(i).setSeleted(true);
                } else {
                    SignUpJobSeekerActivity.warehousingBeans.get(i).setSeleted(false);
                }
            }


        } else {
            for (int i = 0; i < SignUpJobSeekerActivity.hospitalityBeans.size(); i++) {
                SignUpJobSeekerActivity.hospitalityBeans.get(i).setSelect(false);
            }
            for (int i = 0; i < SignUpJobSeekerActivity.warehousingBeans.size(); i++) {
                SignUpJobSeekerActivity.warehousingBeans.get(i).setSeleted(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignUpJobSeekerActivity) getActivity()).setUpToolbar("What can you do?", "4/4", true, true);

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            if (position == 0) {
                fragment = new FragmentHospitality();
            } else if (position == 1) {
                fragment = new FragmentWarehousing();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title = null;
            if (position == 0) {
                title = "HOSPITALITY";
            } else if (position == 1) {
                title = "WAREHOUSING";
            }
            return title;
        }
    }
}
