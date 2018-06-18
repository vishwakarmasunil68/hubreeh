package com.git.hubreeh.view.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.helper.GPSTracker;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.model.LoginBean;
import com.git.hubreeh.pojo.business.BusinessUserPOJO;
import com.git.hubreeh.pojo.user.JobUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.ToastClass;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.employer.activity.HomeHandlerBusiness;
import com.git.hubreeh.view.employer.activity.SignupBusinessHandler;
import com.git.hubreeh.view.employer.fragment.SignupOtpBusiness;
import com.git.hubreeh.view.jobseeker.activity.HomeHandlerJobseeker;
import com.git.hubreeh.view.jobseeker.activity.SignUpJobSeekerActivity;
import com.git.hubreeh.view.jobseeker.fragment.SignupOtpJobseeker;
import com.git.hubreeh.webservice.BusinessWebserviceUrl;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_login_jobseeker_password)
    AppCompatEditText etPassword;
    @BindView(R.id.rbSeeker)
    RadioButton rbSeeker;
    @BindView(R.id.rbBusiness)
    RadioButton rbBusiness;
    @BindView(R.id.tv_login)
    AppCompatButton tvLogin;
    @BindView(R.id.fb_login)
    AppCompatButton fbLogin;


    ProgressView progressView;
    ApiInterface apiInterface;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.etEmail)
    AppCompatEditText etEmail;
    GPSTracker gpsTracker;
    @BindView(R.id.btn_originalfb_login)
    LoginButton btnOriginalfbLogin;

    private CallbackManager callbackManager;
    ArrayList<String> permission;

    private int resultcode = -1, request_code = 0;
    private int fbcode = 112;
//    private String firstname, lastname, name, mobileNumber, social_id, link, emailaddress, password, social_type, passwordfb, countrycode = "", plush = "+", country;

    String email = "", username = "", pass = "", role = "", gender = "", fbId = "", profilePicUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        permission = new ArrayList<String>();
        permission.add("email");
        permission.add("public_profile");


        setfbSdk();

        progressView = new ProgressView(this);
        apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
        gpsTracker = new GPSTracker(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                pass = etPassword.getText().toString();
                role = "";
                if (rbBusiness.isChecked()) {
                    role = "business";
                } else if (rbSeeker.isChecked()) {
                    role = "seeker";
                }

                if (Validation.nullValidator(email)) {
                    Utils.showSnackBar(root, "Please Enter email", etEmail, LoginActivity.this);
                } else if (!Validation.emailValidator(email)) {
                    Utils.showSnackBar(root, "Enter valid email", etEmail, LoginActivity.this);
                } else if (!Validation.passValidator(pass)) {
                    Utils.showSnackBar(root, "Enter valid Password with 6 character length", etEmail, LoginActivity.this);
                } else if (Validation.nullValidator(role)) {
                    Utils.showSnackBar(root, "Select Your Role", etEmail, LoginActivity.this);
                } else {
                    login(email, pass, role);
                }
                /*startActivity(new Intent(LoginActivity.this, HomeHandlerJobseeker.class));
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);*/


            }
        });
        fbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOriginalfbLogin.performClick();
                fbLoginMethod(btnOriginalfbLogin);
            }
        });


        if (!MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("")) {
            if (MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("job")) {
                rbSeeker.setChecked(false);
            } else {
                rbBusiness.setChecked(true);
            }
        }

    }

    private void setfbSdk() {

        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
    }

    private void fbLoginMethod(LoginButton facebookLoginBtn) {
        callbackManager = CallbackManager.Factory.create();
        facebookLoginBtn.setReadPermissions(permission);
        facebookLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            JSONObject json = response.getJSONObject();
                            //Log.e("out","yes");
                            if (json != null) {
                                Log.e("facebookResult", json.toString());
                                try {

                                    if (json.has("email")) {
                                        email = json.getString("email");
                                        Log.e("emailId", email);
                                    }
                                    if (json.has("gender")) {
                                        gender = json.getString("gender");
                                        Log.e("gender", gender);
                                    }
                                    username = json.getString("name");
                                    Log.e("name", username);
                                    fbId = json.getString("id");
                                    Log.e("fbId", fbId);
                                    profilePicUrl = "";
                                    if (json.has("picture")) {
                                        profilePicUrl = json.getJSONObject("picture").getJSONObject("data").getString("url");
                                        Log.e("profilePicUrl", profilePicUrl);
                                    }
                                    etEmail.setText(email + "");
                                    LoginManager.getInstance().logOut();
//                                    callApi
//                                    createFirstRegistration(emailId, name, fbId, profilePicUrl, gender);

//                                    login(email, pass, role);
                                    loginwithFacebook(fbId,email,pass,role);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    LoginManager.getInstance().logOut();
                                }


                            } else {
                                LoginManager.getInstance().logOut();
                            }


                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,picture.type(large),gender");
                    request.setParameters(parameters);
                    request.executeAsync();

                } else {
                    LoginManager.getInstance().logOut();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "User Cancelled the login", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(LoginActivity.this, "Error in registering with facebook", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loginwithFacebook(String facebook_id,String email, String pass, String role) {
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("email",email));
        nameValuePairs.add(new BasicNameValuePair("facebook_id",email));
        nameValuePairs.add(new BasicNameValuePair("mobile",""));
        nameValuePairs.add(new BasicNameValuePair("firstname",""));
        nameValuePairs.add(new BasicNameValuePair("lastname",""));
        nameValuePairs.add(new BasicNameValuePair("dateofbirth",""));
        if(rbBusiness.isChecked()){

            new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.optInt("status")==1){
                            Gson gson=new Gson();
                            BusinessUserPOJO businessUserPOJO =gson.fromJson(jsonObject.optJSONObject("result").toString(),BusinessUserPOJO.class);
                            Constants.businessUserPOJO=businessUserPOJO;
                            Pref.SetStringPref(getApplicationContext(), StringUtils.BUSINESS_USER_POJO,jsonObject.optJSONObject("result").toString());
                            startActivity(new Intent(LoginActivity.this, HomeHandlerBusiness.class));
                            finish();
                        }
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("Login Successfull"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },"GET_BUSINESS_LOGIN_DATA",true).execute(BusinessWebserviceUrl.CREATE_FACEBOOK_USER);
        }else{
            new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.optInt("status")==1){
                            Gson gson=new Gson();
                            JobUserPOJO jobUserPOJO =gson.fromJson(jsonObject.optJSONObject("result").toString(),JobUserPOJO.class);
                            Constants.jobUserPOJO=jobUserPOJO;
                            Pref.SetStringPref(getApplicationContext(), StringUtils.JOB_USER_POJO,jsonObject.optJSONObject("result").toString());
                            startActivity(new Intent(LoginActivity.this, HomeHandlerJobseeker.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            finish();
                        }
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("Login Successfull"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },"GET_BUSINESS_LOGIN_DATA",true).execute(WebServicesUrls.CREATE_FACEBOOK_USER);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

    private void login(final String email, String password, String role) {

//        progressView.showLoader();
//
//        String lat = "" + gpsTracker.getLatitude();
//        String longx = "" + gpsTracker.getLongitude();
//
//        Call<LoginBean> call = apiInterface.login(email, password, role, MyApplication.readStringPref(PrefData.PREF_FCM_TOKEN), "android", MyApplication.readStringPref(PrefData.PREF_DEVICE_ID), lat, longx);
//        call.enqueue(new Callback<LoginBean>() {
//            @Override
//            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
//                progressView.hideLoader();
//                if (response.body().getStatus() == 0) {
//                    Utils.showSnackBar(root, response.body().getMessage(), LoginActivity.this);
//                } else if (response.body().getStatus() == 1) {
//                    if (rbSeeker.isChecked()) {
//                        MyApplication.writeStringPref(PrefData.PREF_JOBID, response.body().getResult().getUser_id());
//                        MyApplication.writeStringPref(PrefData.LOGIN_STATUS, "1");
//                        MyApplication.writeStringPref(PrefData.LOGIN_TYPE, "job");
//                        MyApplication.writeStringPref(PrefData.PREF_JOB_DATA, new Gson().toJson(response.body()));
//                        startActivity(new Intent(LoginActivity.this, HomeHandlerJobseeker.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//                        finish();
//                    } else {
//                        MyApplication.writeStringPref(PrefData.LOGIN_STATUS, "1");
//                        MyApplication.writeStringPref(PrefData.LOGIN_TYPE, "bus");
//                        MyApplication.writeStringPref(PrefData.PREFNUSINESSID, response.body().getResult().getBusiness_id());
//                        MyApplication.writeStringPref(PrefData.PREF_BUSINESS_DATA, new Gson().toJson(response.body()));
//                        startActivity(new Intent(LoginActivity.this, HomeHandlerBusiness.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//                        finish();
//                    }
//                } else if (response.body().getStatus() == 2) {
//                    if (rbSeeker.isChecked()) {
//                        MyApplication.writeStringPref(PrefData.PREF_JOBID, response.body().getResult().getUser_id());
//                        MyApplication.writeStringPref(PrefData.PREF_MOBILE_JOB, response.body().getResult().getMobileNumber());
//                        startActivity(new Intent(LoginActivity.this, SignUpJobSeekerActivity.class).putExtra("direct", "direct"));
//                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//                        finish();
//                    } else {
//                        MyApplication.writeStringPref(PrefData.PREFNUSINESSID, response.body().getResult().getBusiness_id());
//                        MyApplication.writeStringPref(PrefData.PREF_MOBILENUMBER_BUSINESS, response.body().getResult().getMobileNumber());
//                        startActivity(new Intent(LoginActivity.this, SignupBusinessHandler.class).putExtra("direct", "direct"));
//                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//                        finish();
//                    }
//                } else if (response.body().getStatus() == 3) {
//                    if (rbSeeker.isChecked()) {
//                        MyApplication.writeStringPref(PrefData.PREF_JOBID, response.body().getResult().getUser_id());
//                        startActivity(new Intent(LoginActivity.this, SignUpJobSeekerActivity.class).putExtra("direct2", "direct").putExtra("obj", new Gson().toJson(response.body().getResult())));
//                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//                        finish();
//                    } else {
//                        MyApplication.writeStringPref(PrefData.PREFNUSINESSID, response.body().getResult().getBusiness_id());
//                        startActivity(new Intent(LoginActivity.this, SignupBusinessHandler.class).putExtra("direct2", "direct"));
//                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//                        finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginBean> call, Throwable t) {
//                progressView.hideLoader();
//                t.printStackTrace();
//            }
//        });

        if(rbBusiness.isChecked()){
            ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.optInt("status")==1){
                            Gson gson=new Gson();
                            BusinessUserPOJO businessUserPOJO =gson.fromJson(jsonObject.optJSONObject("result").toString(),BusinessUserPOJO.class);
                            Constants.businessUserPOJO=businessUserPOJO;
                            Pref.SetStringPref(getApplicationContext(), StringUtils.BUSINESS_USER_POJO,jsonObject.optJSONObject("result").toString());
                            startActivity(new Intent(LoginActivity.this, HomeHandlerBusiness.class));
                            finish();
                        }
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },"GET_BUSINESS_LOGIN_DATA",true).execute(BusinessWebserviceUrl.LOGIN);
        }else{
            ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("password",password));
            new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.optInt("status")==1){
                            Gson gson=new Gson();
                            JobUserPOJO jobUserPOJO =gson.fromJson(jsonObject.optJSONObject("result").toString(),JobUserPOJO.class);
                            Constants.jobUserPOJO=jobUserPOJO;
                            Pref.SetStringPref(getApplicationContext(), StringUtils.JOB_USER_POJO,jsonObject.optJSONObject("result").toString());
                            startActivity(new Intent(LoginActivity.this, HomeHandlerJobseeker.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            finish();
                        }
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },"GET_LOGIN_DATA",true).execute(WebServicesUrls.LOGIN_USER);
        }

    }
}
