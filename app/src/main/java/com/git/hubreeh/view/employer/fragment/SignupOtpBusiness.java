package com.git.hubreeh.view.employer.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponse;
import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.Config;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.OTPListener;
import com.git.hubreeh.helper.PinEntryEditText;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.pojo.business.BusinessUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.ToastClass;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.employer.activity.SignupBusinessHandler;
import com.git.hubreeh.view.jobseeker.fragment.SignupBusinessCompanyDetails;
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
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 1/31/2018.
 */

public class SignupOtpBusiness extends FragmentController implements OTPListener {

    View view;
    Unbinder unbinder;
    CountDownTimer countDownTimer;
    Vibrator v;
    @BindView(R.id.appCompatTextView)
    AppCompatTextView appCompatTextView;
    @BindView(R.id.text)
    AppCompatTextView text;
    @BindView(R.id.otp_view)
    PinEntryEditText otpView;
    @BindView(R.id.tvTimer)
    TextView tvTimer;
    @BindView(R.id.btnOtpSubmit)
    AppCompatButton btnOtpSubmit;
    @BindView(R.id.tvResendOtp)
    AppCompatTextView tvResendOtp;
    @BindView(R.id.root)
    RelativeLayout root;
    ApiInterface apiInterface;
    ProgressView progressView;
    int MY_PERMISSIONS_REQUEST_LOCATION = 110;
    boolean isGet = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_otp_business, container, false);
        setUpView(getActivity(),this,view);
        unbinder = ButterKnife.bind(this, view);
        progressView = new ProgressView(getActivity());
        apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
        progressView = new ProgressView(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        text.setText("Please Enter The OTP \n sent to " + MyApplication.readStringPref(PrefData.PREF_MOBILENUMBER_BUSINESS));

        tvResendOtp.setVisibility(View.GONE);
        tvTimer.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(1000 * 60, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("" + millisUntilFinished / 1000 + "Sec.");
            }

            public void onFinish() {
                tvTimer.setVisibility(View.INVISIBLE);
                tvResendOtp.setVisibility(View.VISIBLE);
            }
        }.start();

        otpView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6) {
                    isGet = false;
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        otpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(otpView, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        return view;
    }


    @Override
    public void otpReceived(String messageText) {
        if (isGet) {
            String number = messageText.replaceAll("\\D+", "");
            otpView.setText(number);
            isGet = false;
            getOTP(otpView.getText().toString());

        }
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();

                }
            }
        }
    }


    private void getOTP(String otp) {
//        progressView.showLoader();
//        ApiInterface apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
//        Call<ApiResponse> call = apiInterface.authenticate_otp(MyApplication.readStringPref(PrefData.PREFNUSINESSID), otp);
//
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                progressView.hideLoader();
//                if (response.body().getStatus() == 1) {
//                    ((SignupBusinessHandler) getActivity()).changeFragment(new SignupBusinessCompanyDetails(), "businessCompanyDetails");
//                } else {
//                    Utils.showSnackBar(root, response.body().getMessage(), getActivity());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                t.printStackTrace();
//                progressView.hideLoader();
//            }
//        });
//        activityManager.startFragment(R.id.frame_signup_business,new SignupBusinessCompanyDetails());
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id",Constants.businessUserPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("otp",otp));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optBoolean("isSuccess")){
                        activityManager.startFragment(R.id.frame_signup_business,new SignupBusinessCompanyDetails());
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CREATE_BUSINESS_USER",true).execute(BusinessWebserviceUrl.AUTHENTICATE_OTP);

    }


    @OnClick({R.id.btnOtpSubmit, R.id.tvResendOtp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnOtpSubmit:
                if (!otpView.getText().toString().equalsIgnoreCase("")) {
                    isGet = false;
                    getOTP(otpView.getText().toString());
                } else {
                    Utils.showSnackBar(root, "Enter OTP", getActivity());
                }
                break;
            case R.id.tvResendOtp:
                openDialogForConfirmation();
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void openDialogForConfirmation() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_confirm_otp);
        dialog.getWindow().getAttributes().height = (int) (getDeviceMetrics(getActivity()).heightPixels * 0.45);
        dialog.getWindow().getAttributes().width = (int) (getDeviceMetrics(getActivity()).heightPixels * 0.55);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


        final AppCompatEditText etEmail = (AppCompatEditText) dialog.findViewById(R.id.etPhoneNumber);
        ImageView edit = (ImageView) dialog.findViewById(R.id.edit);
        AppCompatButton btnResend = (AppCompatButton) dialog.findViewById(R.id.btnResend);
        etEmail.setText(MyApplication.readStringPref(PrefData.PREF_MOBILENUMBER_BUSINESS));
        etEmail.setEnabled(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail.setEnabled(true);
                etEmail.requestFocus();
            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpView.setText("");
                if (!etEmail.getText().toString().equalsIgnoreCase("")) {
                    MyApplication.writeStringPref(PrefData.PREF_MOBILENUMBER_BUSINESS, etEmail.getText().toString());
                    text.setText("Please Enter The OTP \nsent to " + etEmail.getText().toString());
                    dialog.cancel();
                    resendOtpConnectApi(etEmail.getText().toString());

                } else {
                    Utils.showSnackBar(root, "Enter Phone Number", etEmail, getActivity());
                }

            }
        });
        dialog.show();
    }

    public static DisplayMetrics getDeviceMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

    private void resendOtpConnectApi(String mobileNumber) {
//        progressView.showLoader();
//        ApiInterface apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
//        Call<ApiResponse> call = apiInterface.resend_otp(mobileNumber, MyApplication.readStringPref(PrefData.PREFNUSINESSID));
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                if (response.body().getStatus() == 1) {
//                    Utils.showSnackBar(root, "Otp Resend Successfully", getActivity());
//                } else {
//                    Utils.showSnackBar(root, response.body().getMessage(), getActivity());
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
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id",Constants.businessUserPOJO.getUserId()));
        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optInt("status")==1){

                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CREATE_BUSINESS_USER",true).execute(BusinessWebserviceUrl.RESEND_OTP);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignupBusinessHandler) getActivity()).setUpToolbar("OTP Verification", "2/3", true, true);
    }


}
