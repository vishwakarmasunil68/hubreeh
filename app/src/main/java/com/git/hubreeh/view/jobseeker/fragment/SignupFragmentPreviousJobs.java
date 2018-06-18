package com.git.hubreeh.view.jobseeker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponseGetJob;
import com.git.hubreeh.ApiServices.ApiResponseJob;
import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.AddJobAdapter;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.pojo.AddedJobPOJO;
import com.git.hubreeh.pojo.ResponseListPOJO;
import com.git.hubreeh.pojo.user.JobUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.ToastClass;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.jobseeker.activity.SignUpJobSeekerActivity;
import com.git.hubreeh.webservice.ResponseListCallback;
import com.git.hubreeh.webservice.WebServiceBase;
import com.git.hubreeh.webservice.WebServiceBaseResponseList;
import com.git.hubreeh.webservice.WebServicesCallBack;
import com.git.hubreeh.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/16/2017.
 */

public class SignupFragmentPreviousJobs extends FragmentController {
    View view;

    @BindView(R.id.et_job_signup_company_name)
    AppCompatEditText etJobSignupCompanyName;
    @BindView(R.id.et_job_signup_job_role)
    AppCompatEditText etJobSignupJobRole;
    @BindView(R.id.et_job_signup_start_date)
    AppCompatEditText etJobSignupStartDate;
    @BindView(R.id.et_job_signup_end_date)
    AppCompatEditText etJobSignupEndDate;
    @BindView(R.id.et_job_signup_description)
    AppCompatEditText etJobSignupDescription;
    @BindView(R.id.btn_add_job)
    AppCompatButton btnAddJob;
    @BindView(R.id.root_job_previos)
    LinearLayout rootJobPrevios;

    AddJobAdapter adapter;
    ProgressView progressView;
    public static String oldDate = "";
    Unbinder unbinder;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.btn_signup_previousjob_continue)
    AppCompatButton btnSignupPreviousjobContinue;
    List<AddedJobPOJO> addJobModelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_previous_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpView(getActivity(),this,view);
        progressView = new ProgressView(getActivity());
        addJobModelList = new ArrayList<>();
        adapter = new AddJobAdapter(getActivity(), addJobModelList);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler.setAdapter(adapter);
        getJobs();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.et_job_signup_start_date, R.id.et_job_signup_end_date, R.id.btn_add_job, R.id.btn_signup_previousjob_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_job_signup_start_date:
                Utils.showDatePicker(getActivity(), etJobSignupStartDate);
                break;
            case R.id.et_job_signup_end_date:
                if (etJobSignupStartDate.getText().toString().equalsIgnoreCase("")) {
                    Utils.showSnackBar(rootJobPrevios, "Select Start Date First", getActivity());
                } else {
                    Utils.showDatePicker(getActivity(), etJobSignupEndDate, oldDate);
                }
                break;
            case R.id.btn_add_job:
                if (Validation.nullValidator(etJobSignupCompanyName.getText().toString().trim())) {
                    Utils.showSnackBar(rootJobPrevios, "Please Fill the Company Name", etJobSignupCompanyName, getActivity());
                } else if (Validation.nullValidator(etJobSignupJobRole.getText().toString().trim())) {
                    Utils.showSnackBar(rootJobPrevios, "Please Fill the Job Role", etJobSignupJobRole, getActivity());
                } else if (Validation.nullValidator(etJobSignupStartDate.getText().toString().trim())) {
                    Utils.showSnackBar(rootJobPrevios, "Please select the Joining Date", etJobSignupStartDate, getActivity());
                } else if (Validation.nullValidator(etJobSignupEndDate.getText().toString().trim())) {
                    Utils.showSnackBar(rootJobPrevios, "Please select the End Date", etJobSignupEndDate, getActivity());
                } else if (Validation.nullValidator(etJobSignupDescription.getText().toString().trim())) {
                    Utils.showSnackBar(rootJobPrevios, "Please fill the description", etJobSignupDescription, getActivity());
                } else {
                    addJob(etJobSignupCompanyName.getText().toString(), etJobSignupJobRole.getText().toString(), etJobSignupStartDate.getText().toString(), etJobSignupEndDate.getText().toString(), etJobSignupDescription.getText().toString(), 1);
                }
                break;
            case R.id.btn_signup_previousjob_continue:

                if (Validation.nullValidator(etJobSignupCompanyName.getText().toString().trim())
                        && Validation.nullValidator(etJobSignupJobRole.getText().toString().trim())
                        && Validation.nullValidator(etJobSignupStartDate.getText().toString().trim())
                        && Validation.nullValidator(etJobSignupEndDate.getText().toString().trim())
                        && Validation.nullValidator(etJobSignupDescription.getText().toString().trim())) {
                    ((SignUpJobSeekerActivity) getActivity()).changeFragment(new SignupFragmentWhatCanYouDo(), "signupwhatcanyoudo", R.id.frame_container);
                } else {

                    if (Validation.nullValidator(etJobSignupCompanyName.getText().toString().trim())) {
                        Utils.showSnackBar(rootJobPrevios, "Please Fill the Company Name", etJobSignupCompanyName, getActivity());
                    } else if (Validation.nullValidator(etJobSignupJobRole.getText().toString().trim())) {
                        Utils.showSnackBar(rootJobPrevios, "Please Fill the Job Role", etJobSignupJobRole, getActivity());
                    } else if (Validation.nullValidator(etJobSignupStartDate.getText().toString().trim())) {
                        Utils.showSnackBar(rootJobPrevios, "Please select the Joining Date", etJobSignupStartDate, getActivity());
                    } else if (Validation.nullValidator(etJobSignupEndDate.getText().toString().trim())) {
                        Utils.showSnackBar(rootJobPrevios, "Please select the End Date", etJobSignupEndDate, getActivity());
                    } else if (Validation.nullValidator(etJobSignupDescription.getText().toString().trim())) {
                        Utils.showSnackBar(rootJobPrevios, "Please fill the description", etJobSignupDescription, getActivity());
                    } else {
                        addJob(etJobSignupCompanyName.getText().toString(), etJobSignupJobRole.getText().toString(), etJobSignupStartDate.getText().toString(), etJobSignupEndDate.getText().toString(), etJobSignupDescription.getText().toString(), 0);
                    }
                }
                activityManager.startFragment(R.id.frame_container,new SignupFragmentSetYourRate());


                break;
        }
    }


    public void getJobs() {
//        progressView.showLoader();
//        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
//        Call<ApiResponseGetJob> call = apiInterface.get_previous_company(MyApplication.readStringPref(PrefData.PREF_JOBID));
//
//        call.enqueue(new Callback<ApiResponseGetJob>() {
//            @Override
//            public void onResponse(Call<ApiResponseGetJob> call, Response<ApiResponseGetJob> response) {
//                progressView.hideLoader();
//                if (response.body().getStatus() == 1) {
//                    addJobModelList.clear();
//                    addJobModelList.addAll(response.body().getResult());
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseGetJob> call, Throwable t) {
//                progressView.hideLoader();
//                t.printStackTrace();
//            }
//        });

        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id",Constants.jobUserPOJO.getUserId()));
        new WebServiceBaseResponseList<AddedJobPOJO>(nameValuePairs, getActivity(), new ResponseListCallback<AddedJobPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<AddedJobPOJO> responseListPOJO) {
                try{
                    addJobModelList.clear();
                    addJobModelList.addAll(responseListPOJO.getResultList());
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, AddedJobPOJO.class,"GET_JOBS", true).execute(WebServicesUrls.GET_PREVIOUS_JOBS);

    }

    public void addJob(String name, String role, String start, String end, String dec, final int flag) {


        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.jobUserPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("company_name", name));
        nameValuePairs.add(new BasicNameValuePair("job_role", role));
        nameValuePairs.add(new BasicNameValuePair("start_date", start));
        nameValuePairs.add(new BasicNameValuePair("end_date", end));
        nameValuePairs.add(new BasicNameValuePair("job_description", dec));
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

                        etJobSignupCompanyName.setText("");
                        etJobSignupJobRole.setText("");
                        etJobSignupStartDate.setText("");
                        etJobSignupEndDate.setText("");
                        etJobSignupDescription.setText("");
                        etJobSignupCompanyName.requestFocus();
                        if(flag==0){
                            activityManager.startFragment(R.id.frame_container,new SignupFragmentWhatCanYouDo());
                        }else{
                            getJobs();
                        }

                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "CREATE_USER", true).execute(WebServicesUrls.USER_REGISTER_2);

//        progressView.showLoader();
//        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
//        Call<ApiResponseJob> call = apiInterface.user_register2(name, role, start, end, dec, MyApplication.readStringPref(PrefData.PREF_JOBID));
//        call.enqueue(new Callback<ApiResponseJob>() {
//            @Override
//            public void onResponse(Call<ApiResponseJob> call, Response<ApiResponseJob> response) {
//                progressView.hideLoader();
//                if (response.body().getStatus() == 1) {
//                    if (flag == 0) {
//
//                        etJobSignupCompanyName.setText("");
//                        etJobSignupJobRole.setText("");
//                        etJobSignupStartDate.setText("");
//                        etJobSignupEndDate.setText("");
//                        etJobSignupDescription.setText("");
//                        etJobSignupCompanyName.requestFocus();
//                        ((SignUpJobSeekerActivity) getActivity()).changeFragment(new SignupFragmentWhatCanYouDo(), "signupwhatcanyoudo", R.id.frame_container);
//                    } else {
//                        etJobSignupCompanyName.setText("");
//                        etJobSignupJobRole.setText("");
//                        etJobSignupStartDate.setText("");
//                        etJobSignupEndDate.setText("");
//                        etJobSignupDescription.setText("");
//                        etJobSignupCompanyName.requestFocus();
//                        getJobs();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseJob> call, Throwable t) {
//                progressView.hideLoader();
//                t.printStackTrace();
//            }
//        });
//        activityManager.startFragment(R.id.frame_container,new SignupFragmentWhatCanYouDo());


    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignUpJobSeekerActivity) getActivity()).setUpToolbar("Add previous jobs", "3/8", true, true);

    }
}
