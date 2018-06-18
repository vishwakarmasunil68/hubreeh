package com.git.hubreeh.view.employer.fragment;

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

import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.pojo.business.BusinessUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.FinalKeywordsClass;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.ToastClass;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.employer.activity.SignupBusinessHandler;
import com.git.hubreeh.webservice.BusinessWebserviceUrl;
import com.git.hubreeh.webservice.WebServiceBase;
import com.git.hubreeh.webservice.WebServicesCallBack;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 12/18/2017.
 */

public class SignupBusinessSignup extends FragmentController {
    View view;
    AppCompatButton btnContinue;
    AppCompatTextView tvInstruction;
    AppCompatTextView Signin;
    @BindView(R.id.et_business_email)
    AppCompatEditText etBusinessEmail;
    @BindView(R.id.et_business_password)
    AppCompatEditText etBusinessPassword;
    @BindView(R.id.et_business_mobile_number)
    AppCompatEditText etBusinessMobileNumber;
    @BindView(R.id.root_business_signup_first)
    LinearLayout rootBusinessSignupFirst;

    String emailId, password, mobile;
    ProgressView progressView;

    Unbinder unbinder;
    @BindView(R.id.tv_signupbusiness_signin)
    AppCompatTextView tvSignupbusinessSignin;
    private String email = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signupbusiness_signup, container, false);
        setUpView(getActivity(), this, view);
        unbinder = ButterKnife.bind(this, view);
        initialize();


        email = MyApplication.readStringPref(FinalKeywordsClass.EMAIL).toString();
        etBusinessEmail.setText(email);
        Log.e("Email", email + "");

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
        etBusinessEmail.setFilters(new InputFilter[]{filter});


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailId = etBusinessEmail.getText().toString().trim();
                password = etBusinessPassword.getText().toString().trim();
                mobile = etBusinessMobileNumber.getText().toString().trim();
                MyApplication.writeStringPref(PrefData.PREF_MOBILENUMBER_BUSINESS, mobile);
                if (Validation.nullValidator(emailId)) {
                    Utils.showSnackBar(rootBusinessSignupFirst, "Please fill the email", etBusinessEmail, getActivity());
                } else if (!Validation.emailValidator(emailId)) {
                    Utils.showSnackBar(rootBusinessSignupFirst, "Please fill the email in proper format", etBusinessEmail, getActivity());
                } else if (!Validation.passValidator(password)) {
                    Utils.showSnackBar(rootBusinessSignupFirst, "Password must have 5 characters", etBusinessPassword, getActivity());
                } else if (Validation.nullValidator(mobile)) {
                    Utils.showSnackBar(rootBusinessSignupFirst, "Please fill mobile number", etBusinessMobileNumber, getActivity());
                } else if (mobile.length() < 8 && mobile.length() > 15) {
                    Utils.showSnackBar(rootBusinessSignupFirst, "Please Enter Correct Mobile Number", etBusinessMobileNumber, getActivity());
                } else {
                    createUser(emailId, mobile, password);
                }

            }
        });

        setLinks();


        return view;
    }


    private void setLinks() {
        String text = "By tapping continue, you agree to the <a href='myapp://terms'><font color='#FFC005'>Terms & Conditions</font></a> and  <a href='myapp://terms'><font color='#FFC005'>Privacy Policy</font></a>";
        tvInstruction.setText(Html.fromHtml(text));
        tvInstruction.setMovementMethod(LinkMovementMethod.getInstance());
        String sign = "Already have an account? <a href='myapp://sign-in'><font color='#FFC005'>Sign in!</font></a>";
        tvSignupbusinessSignin.setText(Html.fromHtml(sign));
        tvSignupbusinessSignin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void createUser(String email, String mobile, String password) {
//        progressView.showLoader();
//
//        ApiInterface apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
//        Call<ApiResponse> call = apiInterface.create_user(email, mobile, password);
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                if (response.body().getStatus() == 1) {
//                    MyApplication.writeStringPref(PrefData.PREFNUSINESSID, response.body().getBusiness_id());
//                    ((SignupBusinessHandler) getActivity()).changeFragment(new SignupOtpBusiness(), "signupotp_business");
//                } else {
//                    Utils.showSnackBar(rootBusinessSignupFirst, response.body().getMessage(), getActivity());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                t.printStackTrace();
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//            }
//        });
//        activityManager.startFragment(R.id.frame_signup_business,new SignupOtpBusiness());
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optBoolean("isSuccess")) {
                        Gson gson = new Gson();
                        BusinessUserPOJO businessUserPOJO = gson.fromJson(jsonObject.optJSONObject("Result").toString(), BusinessUserPOJO.class);
                        Constants.businessUserPOJO = businessUserPOJO;
                        Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.BUSINESS_USER_POJO, jsonObject.optJSONObject("Result").toString());
                        Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.IS_BUSINESS_LOGIN, true);
                        activityManager.startFragment(R.id.frame_signup_business, new SignupOtpBusiness());
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CREATE_BUSINESS_USER", true).execute(BusinessWebserviceUrl.CREATE_USER);
    }

    private void initialize() {
        progressView = new ProgressView(getActivity());
        btnContinue = view.findViewById(R.id.btn_signupbusiness_continue);
        tvInstruction = view.findViewById(R.id.tv_signupbusiness_instruction);
        Signin = view.findViewById(R.id.tv_signupbusiness_signin);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignupBusinessHandler) getActivity()).setUpToolbar("Signup", "1/3", true, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
