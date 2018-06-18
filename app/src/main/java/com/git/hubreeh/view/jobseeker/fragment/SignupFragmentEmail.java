package com.git.hubreeh.view.jobseeker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.pojo.user.JobUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.FinalKeywordsClass;
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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Hp on 12/16/2017.
 */

public class SignupFragmentEmail extends FragmentController {
    View view;

    ProgressView progressView;
    Unbinder unbinder;
    @BindView(R.id.et_signup_jobseeker_email)
    AppCompatEditText etSignupJobseekerEmail;
    @BindView(R.id.et_signup_jobseeker_password)
    AppCompatEditText etSignupJobseekerPassword;
    @BindView(R.id.et_signup_jobseeker_mobilenumber)
    AppCompatEditText etSignupJobseekerMobilenumber;
    @BindView(R.id.tv_email_signin)
    AppCompatTextView tvEmailSignin;
    @BindView(R.id.btn_email_continue)
    AppCompatButton btnEmailContinue;
    @BindView(R.id.ripple_email_continue)
    MaterialRippleLayout rippleEmailContinue;
    @BindView(R.id.tv_conditions)
    AppCompatTextView tvConditions;
    @BindView(R.id.root_email_job)
    LinearLayout rootEmailJob;

    String email = "", username = "", pass = "", role = "", socialtype = "", gender = "", fbId = "", profilePicUrl = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_email, container, false);
        setUpView(getActivity(),this,view);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            socialtype = getArguments().getString(FinalKeywordsClass.SOCIAL_TYPE);
            username = getArguments().getString(FinalKeywordsClass.USERNAME);
            email = getArguments().getString(FinalKeywordsClass.EMAIL);
            etSignupJobseekerEmail.setText(email);

            Log.v(FinalKeywordsClass.SOCIAL_TYPE, socialtype);
            Log.v(FinalKeywordsClass.USERNAME, username);
            Log.v(FinalKeywordsClass.EMAIL, email);
        }

        email = MyApplication.readStringPref(FinalKeywordsClass.EMAIL).toString();
        etSignupJobseekerEmail.setText(email);
        Log.e("Email",email+"");


        progressView = new ProgressView(getActivity());


        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }

        };
        etSignupJobseekerEmail.setFilters(new InputFilter[]{filter});


        setLinks();
        return view;
    }

    private void setLinks() {
        String text = "By tapping continue, you agree to the <a href='myapp://terms'><font color='#FFC005'>Terms & Conditions</font></a> and  <a href='myapp://terms'><font color='#FFC005'>Privacy Policy</font></a>";
        tvConditions.setText(Html.fromHtml(text));
        tvConditions.setMovementMethod(LinkMovementMethod.getInstance());
        String sign = "Already have an account? <a href='myapp://sign-in'><font color='#FFC005'>Sign in!</font></a>";
        tvEmailSignin.setText(Html.fromHtml(sign));
        tvEmailSignin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_email_continue)
    public void onViewClicked() {


        String email = etSignupJobseekerEmail.getText().toString().trim();
        String password = etSignupJobseekerPassword.getText().toString().trim();
        String mobile = etSignupJobseekerMobilenumber.getText().toString().trim();

        MyApplication.writeStringPref(PrefData.PREF_MOBILE_JOB, mobile);

        if (Validation.nullValidator(email)) {
            Utils.showSnackBar(rootEmailJob, "Please enter the email", etSignupJobseekerEmail, getActivity());
        } else if (!Validation.emailValidator(email)) {
            Utils.showSnackBar(rootEmailJob, "Please enter the email in proper format", etSignupJobseekerEmail, getActivity());
        } else if (!Validation.passValidator(password)) {
            Utils.showSnackBar(rootEmailJob, "Password must be 5 characters", etSignupJobseekerPassword, getActivity());
        } else if (Validation.nullValidator(mobile)) {
            Utils.showSnackBar(rootEmailJob, "Please fill the mobile number", etSignupJobseekerMobilenumber, getActivity());
        }else if(mobile.length() < 8 && mobile.length() > 15){
            Utils.showSnackBar(rootEmailJob, "Please Enter valid mobile number", etSignupJobseekerMobilenumber, getActivity());
        }else {
            createJobSeeker(email, password, mobile);
        }

    }

    private void createJobSeeker(String Email, String Password, String MobileNumber) {
//        progressView.showLoader("Loading Please Wait");
//        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
//        Call<ApiResponseJob> call = apiInterface.create_user_job(Email, MobileNumber, Password);
//        call.enqueue(new Callback<ApiResponseJob>() {
//            @Override
//            public void onResponse(Call<ApiResponseJob> call, Response<ApiResponseJob> response) {
//                progressView.hideLoader();
//                if (response.body().getStatus() == 1) {
//                    MyApplication.writeStringPref(PrefData.PREF_JOBID, response.body().getUser_id());
//                    ((SignUpJobSeekerActivity) getActivity()).changeFragment(new SignupOtpJobseeker(), "signupotp", R.id.frame_container);
//                } else {
//                    Utils.showSnackBar(rootEmailJob, response.body().getMessage(), getActivity());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseJob> call, Throwable t) {
//                t.printStackTrace();
//                progressView.hideLoader();
//            }
//        });

//        activityManager.startFragment(R.id.frame_container,new SignupOtpJobseeker());

        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("mobile",MobileNumber));
        nameValuePairs.add(new BasicNameValuePair("email",Email));
        nameValuePairs.add(new BasicNameValuePair("password",Password));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optBoolean("isSuccess")){
                        Gson gson=new Gson();
                        JobUserPOJO jobUserPOJO =gson.fromJson(jsonObject.optJSONObject("Result").toString(),JobUserPOJO.class);
                        Constants.jobUserPOJO=jobUserPOJO;
                        Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.JOB_USER_POJO,jsonObject.optJSONObject("Result").toString());
                        activityManager.startFragment(R.id.frame_container,new SignupOtpJobseeker());
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CREATE_USER",true).execute(WebServicesUrls.CREATE_USER);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignUpJobSeekerActivity) getActivity()).setUpToolbar("Sign Up", "1/4", true, true);

    }
}
