package com.git.hubreeh.view.jobseeker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.GPSTracker;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.pojo.user.JobUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 12/16/2017.
 */

public class SignupFragmentTaxInformation extends FragmentController {
    View view;

    @BindView(R.id.cb_waiting_NIN)
    CheckBox cbWaitingNIN;
    @BindView(R.id.rb_first_job)
    RadioButton rbFirstJob;
    @BindView(R.id.rb_only_job)
    RadioButton rbOnlyJob;
    @BindView(R.id.rb_another_job)
    RadioButton rbAnotherJob;
    @BindView(R.id.rg_date_situation)
    RadioGroup rgDateSituation;
    @BindView(R.id.rb_no_loan)
    RadioButton rbNoLoan;
    @BindView(R.id.rb_one_loan)
    RadioButton rbOneLoan;
    @BindView(R.id.rb_two_loan)
    RadioButton rbTwoLoan;
    @BindView(R.id.rg_student_loan)
    RadioGroup rgStudentLoan;
    @BindView(R.id.root_national_insurance_number)
    LinearLayout rootNationalInsuranceNumber;

    @BindView(R.id.et_national_insurance_number)
    AppCompatEditText etNationalInsuranceNumber;

    ProgressView progressView;
    String nationalInsuranceNumber = "0";

    GPSTracker gpsTracker;
    Unbinder unbinder;
    @BindView(R.id.btn_signup_finish)
    AppCompatButton finish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_tax_information, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpView(getActivity(),this,view);
        progressView = new ProgressView(getActivity());
        gpsTracker = new GPSTracker(getActivity());
        etNationalInsuranceNumber.setText(SignUpJobSeekerActivity.NIN);

        etNationalInsuranceNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                SignUpJobSeekerActivity.NIN = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (SignUpJobSeekerActivity.finance != 0) {
            if (SignUpJobSeekerActivity.finance == R.id.rb_first_job) {
                rbFirstJob.setChecked(true);
            } else if (SignUpJobSeekerActivity.finance == R.id.rb_only_job) {
                rbOnlyJob.setChecked(true);
            } else if (SignUpJobSeekerActivity.finance == R.id.rb_another_job) {
                rbAnotherJob.setChecked(true);
            }
        }

        if (SignUpJobSeekerActivity.lone != 0) {
            if (SignUpJobSeekerActivity.lone == R.id.rb_no_loan) {
                rbNoLoan.setChecked(true);
            } else if (SignUpJobSeekerActivity.lone == R.id.rb_one_loan) {
                rbOneLoan.setChecked(true);
            } else if (SignUpJobSeekerActivity.lone == R.id.rb_two_loan) {
                rbTwoLoan.setChecked(true);
            }
        }


        cbWaitingNIN.setChecked(SignUpJobSeekerActivity.waiting);
        cbWaitingNIN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    etNationalInsuranceNumber.setEnabled(false);
                } else {
                    etNationalInsuranceNumber.setEnabled(true);
                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbWaitingNIN.isChecked()) {
                    nationalInsuranceNumber = etNationalInsuranceNumber.getText().toString();
                } else {
                    nationalInsuranceNumber = "0";
                }
                if (Validation.nullValidator(nationalInsuranceNumber)) {
                    Utils.showSnackBar(rootNationalInsuranceNumber, "Please Fill the National Insurance Number ", getActivity());
                } else if (TextUtils.isEmpty(getRadioButtonDataFromFirstJob())) {
                    Utils.showSnackBar(rootNationalInsuranceNumber, "Please Select At least 1 date situation", getActivity());
                } else if (TextUtils.isEmpty(getRadioButtonDataFromStudentLoan())) {
                    Utils.showSnackBar(rootNationalInsuranceNumber, "Please Select At least 1 Loan type", getActivity());
                } else {
                    connectApiFinalRegistration();
                }
            }
        });

        rgDateSituation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SignUpJobSeekerActivity.finance = checkedId;
            }
        });

        rgStudentLoan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SignUpJobSeekerActivity.lone = checkedId;
            }
        });


        if (getActivity().getIntent().hasExtra("direct2")) {
            Log.e("dd", getActivity().getIntent().getStringExtra("obj"));
            try {
                JSONObject object = new JSONObject(getActivity().getIntent().getStringExtra("obj"));
                etNationalInsuranceNumber.setText(object.getString("InsuranceNumber"));


                if (object.getString("dateSituation").equalsIgnoreCase("1")) {
                    rbFirstJob.setChecked(true);
                } else if (object.getString("dateSituation").equalsIgnoreCase("2")) {
                    rbOnlyJob.setChecked(true);
                } else if (object.getString("dateSituation").equalsIgnoreCase("3")) {
                    rbAnotherJob.setChecked(true);
                }

                if (object.getString("studentLoan").equalsIgnoreCase("1")) {
                    rbNoLoan.setChecked(true);
                } else if (object.getString("studentLoan").equalsIgnoreCase("2")) {
                    rbOneLoan.setChecked(true);
                } else if (object.getString("studentLoan").equalsIgnoreCase("3")) {
                    rbTwoLoan.setChecked(true);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return view;
    }

    public String getRadioButtonDataFromStudentLoan() {
        String studentLoan = "";
        switch (rgStudentLoan.getCheckedRadioButtonId()) {
            case R.id.rb_no_loan:
                studentLoan = "1";
                break;

            case R.id.rb_one_loan:
                studentLoan = "2";
                break;

            case R.id.rb_two_loan:
                studentLoan = "3";
                break;
        }
        return studentLoan;
    }

    public String getRadioButtonDataFromFirstJob() {
        String dateSituation = "";
        switch (rgDateSituation.getCheckedRadioButtonId()) {
            case R.id.rb_first_job:
                dateSituation = "1";
                break;

            case R.id.rb_only_job:
                dateSituation = "2";
                break;

            case R.id.rb_another_job:
                dateSituation = "3";
                break;
        }
        Log.e("date1", dateSituation);
        return dateSituation;
    }

    private void connectApiFinalRegistration() {
//        progressView.showLoader();
//        String lat = "" + gpsTracker.getLatitude();
//        String longx = "" + gpsTracker.getLongitude();
//        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
//        Call<ApiResponseFinal> call = apiInterface.final_step_jobseeker(nationalInsuranceNumber,
//                getRadioButtonDataFromFirstJob(),
//                getRadioButtonDataFromStudentLoan(),
//                MyApplication.readStringPref(PrefData.PREF_FCM_TOKEN),
//                "Android",
//                MyApplication.readStringPref(PrefData.PREF_DEVICE_ID),
//                MyApplication.readStringPref(PrefData.PREF_JOBID),
//                lat,
//                longx
//        );
//        call.enqueue(new Callback<ApiResponseFinal>() {
//            @Override
//            public void onResponse(Call<ApiResponseFinal> call, Response<ApiResponseFinal> response) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                if (response.body().getStatus() == 1) {
//                    String email = response.body().getResult().get(0).getEmail();
//                    String password = response.body().getResult().get(0).getPassword();
//                    String firstName = response.body().getResult().get(0).getFirstName();
//                    String lastName = response.body().getResult().get(0).getLastName();
//                    String dateOfBirth = response.body().getResult().get(0).getDateOfBirth();
//                    String MobileNumber = response.body().getResult().get(0).getMobileNumber();
//                    String image = response.body().getResult().get(0).getImage();
//                    String InsuranceNumber = response.body().getResult().get(0).getInsuranceNumber();
//
//
//                    SignUpJobSeekerActivity.hospitalityBeans.clear();
//                    SignUpJobSeekerActivity.warehousingBeans.clear();
//                    SignUpJobSeekerActivity.daysModels.clear();
//                    SignUpJobSeekerActivity.hearUsModels.clear();
//                    SignUpJobSeekerActivity.path = "";
//                    SignUpJobSeekerActivity.aboutMe = "";
//                    SignUpJobSeekerActivity.rate = "";
//                    SignUpJobSeekerActivity.workhours = false;
//                    SignUpJobSeekerActivity.fullTime = false;
//                    SignUpJobSeekerActivity.NIN = "";
//                    SignUpJobSeekerActivity.finance = 0;
//                    SignUpJobSeekerActivity.lone = 0;
//
//
//                    startActivity(new Intent(getActivity(), HomeHandlerJobseeker.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                    getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//                    getActivity().finish();
//
//                } else {
//                    Utils.showSnackBar(rootNationalInsuranceNumber, response.body().getMessage(), getActivity());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseFinal> call, Throwable t) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                t.printStackTrace();
//            }
//        });


        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.jobUserPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("InsuranceNumber",etNationalInsuranceNumber.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("dateSituation",getRadioGroupValues(rgDateSituation)));
        nameValuePairs.add(new BasicNameValuePair("userFinanceYear","2018"));
        nameValuePairs.add(new BasicNameValuePair("studentLoan",getRadioGroupValues(rgStudentLoan)));
        nameValuePairs.add(new BasicNameValuePair("latitude",""));
        nameValuePairs.add(new BasicNameValuePair("longitude",""));

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
                        startActivity(new Intent(getActivity(), HomeHandlerJobseeker.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                        getActivity().finish();
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CREATE_USER",true).execute(WebServicesUrls.FINAL_REGISTER);


    }
    public String getRadioGroupValues(RadioGroup rg) {
        try {
            int checkedIn = rg.getCheckedRadioButtonId();
            if (checkedIn != -1) {
                return ((RadioButton) view.findViewById(checkedIn)).getText().toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
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
        ((SignUpJobSeekerActivity) getActivity()).setUpToolbar("Tax information", "8/8", true, true);
    }
}
