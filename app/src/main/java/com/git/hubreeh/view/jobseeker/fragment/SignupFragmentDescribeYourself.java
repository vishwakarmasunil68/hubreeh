package com.git.hubreeh.view.jobseeker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponseGetJob;
import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.HearUsAdapter;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.model.jobseeker.HearUsModel;
import com.git.hubreeh.pojo.user.JobUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.ToastClass;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.jobseeker.activity.SignUpJobSeekerActivity;
import com.git.hubreeh.webservice.WebServiceBase;
import com.git.hubreeh.webservice.WebServicesCallBack;
import com.git.hubreeh.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/16/2017.
 */

public class SignupFragmentDescribeYourself extends FragmentController {
    View view;

    @BindView(R.id.recycler_hearus)
    RecyclerView recyclerHearus;
    @BindView(R.id.et_signup_job_about_me)
    AppCompatEditText etSignupJobAboutMe;
    Unbinder unbinder;
    @BindView(R.id.root_about_page)
    LinearLayout rootAboutPage;

    ProgressView progressView;
    HearUsAdapter mAdapter;
    String hearUs = "";
    @BindView(R.id.btn_describe_continue)
    AppCompatButton btnContinue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_describe_yourself, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpView(getActivity(),this,view);
        progressView = new ProgressView(getActivity());
        etSignupJobAboutMe.setText(SignUpJobSeekerActivity.aboutMe);
        recyclerHearus.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for (int i = 0; i < SignUpJobSeekerActivity.hearUsModels.size(); i++) {
                    if (SignUpJobSeekerActivity.hearUsModels.get(i).isHearIsChecked()) {
                        hearUs = SignUpJobSeekerActivity.hearUsModels.get(i).getReasonHearUs();
                        break;
                    } else {
                        hearUs = "";
                    }
                }

                Log.e("ee", hearUs);

                if (Validation.nullValidator(etSignupJobAboutMe.getText().toString().trim())) {
                    Utils.showSnackBar(rootAboutPage, "Please enter the About Me content", etSignupJobAboutMe, getActivity());
                } else if (hearUs.isEmpty()) {
                    Utils.showSnackBar(rootAboutPage, "Please select from where you heard about us", etSignupJobAboutMe, getActivity());
                } else {
                    SignUpJobSeekerActivity.aboutMe = etSignupJobAboutMe.getText().toString().trim();
                    connectApiForAboutMe();
                }
            }
        });

        mAdapter = new HearUsAdapter(getActivity(), SignUpJobSeekerActivity.hearUsModels);
        recyclerHearus.setAdapter(mAdapter);
        prepareDataOther();


        if (getActivity().getIntent().hasExtra("direct2")) {
            try {
                JSONObject object = new JSONObject(getActivity().getIntent().getStringExtra("obj"));
                etSignupJobAboutMe.setText(object.getString("aboutMe"));
                for (int i = 0; i < SignUpJobSeekerActivity.hearUsModels.size(); i++) {
                    if (SignUpJobSeekerActivity.hearUsModels.get(i).getReasonHearUs().equalsIgnoreCase(object.getString("hearUs"))) {
                        SignUpJobSeekerActivity.hearUsModels.get(i).setHearIsChecked(true);
                    } else {
                        SignUpJobSeekerActivity.hearUsModels.get(i).setHearIsChecked(false);
                    }
                }
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return view;
    }


    private void connectApiForAboutMe() {
//        progressView.showLoader();
//        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
//        Call<ApiResponseGetJob> call = apiInterface.update_about_me(etSignupJobAboutMe.getText().toString().trim(), hearUs, MyApplication.readStringPref(PrefData.PREF_JOBID));
//        call.enqueue(new Callback<ApiResponseGetJob>() {
//            @Override
//            public void onResponse(Call<ApiResponseGetJob> call, Response<ApiResponseGetJob> response) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                if (response.body().getStatus() == 1) {
//                    ((SignUpJobSeekerActivity) getActivity()).changeFragment(new SignupFragmentTaxInformation(), "signuptax", R.id.frame_container);
//                } else {
//                    Utils.showSnackBar(rootAboutPage, response.body().getMessage(), getActivity());
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
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id",Constants.jobUserPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("aboutMe",etSignupJobAboutMe.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("hearUs",hearUs));

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
                        activityManager.startFragment(R.id.frame_container,new SignupFragmentTaxInformation());
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CREATE_USER",true).execute(WebServicesUrls.USER_REGISTER_6);
    }

    public void getHearUsData(){

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignUpJobSeekerActivity) getActivity()).setUpToolbar("Describe yourself", "7/8", true, true);
    }


    private void prepareDataOther() {
        if (SignUpJobSeekerActivity.hearUsModels.isEmpty()) {
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("TV/Radio", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Search on App Store", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Friends and Family", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Internet Search(Google or Bing)", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Newspaper/Online Articales", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Social Media/Blog Post", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Job Board - Caterer", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Job Board - indeed", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Job Board - Totaljob", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Job Board - Other", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("Flyer", false));
            SignUpJobSeekerActivity.hearUsModels.add(new HearUsModel("I can\'t remember", false));
        }
        mAdapter.notifyDataSetChanged();
    }


}
