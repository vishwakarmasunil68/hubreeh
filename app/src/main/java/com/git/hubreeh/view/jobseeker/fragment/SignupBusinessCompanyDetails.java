package com.git.hubreeh.view.jobseeker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponse;
import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.pojo.business.BusinessUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.employer.activity.HomeHandlerBusiness;
import com.git.hubreeh.view.employer.activity.MapsActivity;
import com.git.hubreeh.view.employer.activity.SignupBusinessHandler;
import com.git.hubreeh.view.employer.fragment.SignupOtpBusiness;
import com.git.hubreeh.webservice.BusinessWebserviceUrl;
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
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/18/2017.
 */

public class SignupBusinessCompanyDetails extends FragmentController {
    View view;

    @BindView(R.id.spinner_title)
    Spinner spinner_title;
    @BindView(R.id.root_business_signup_second)
    LinearLayout rootBusinessSignupSecond;
    @BindView(R.id.btn_signusbusiness_company_register)
    Button btn_signusbusiness_company_register;
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_dob)
    EditText et_dob;
    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_business_address_one)
    EditText et_business_address_one;
    @BindView(R.id.et_business_address_two)
    EditText et_business_address_two;
    @BindView(R.id.et_location)
    EditText et_location;

    ProgressView progressView;



    Unbinder unbinder;
    String[] titles = {"Mr.", "Miss.", "Mrs."};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signupbusiness_company_details, container, false);
        setUpView(getActivity(),this,view);
        unbinder = ButterKnife.bind(this, view);
        initialize();
        btn_signusbusiness_company_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNullValue();
            }
        });

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.oldDatePicker(getActivity(), et_dob);
            }
        });

        et_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MapsActivity.class);
                startActivityForResult(intent,101);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        spinner_title.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, titles));
        ((SignupBusinessHandler) getActivity()).setUpToolbar("Signup Company Details", "3/3", true, true);
    }
    String first_name, last_name, dob, address_1, address_2, country,city,location;
    private void checkNullValue() {
        first_name = et_first_name.getText().toString().trim();
        last_name = et_last_name.getText().toString().trim();
        dob = et_dob.getText().toString().trim();
        country = et_country.getText().toString().trim();
        city = et_city.getText().toString().trim();
        address_1 = et_business_address_one.getText().toString().trim();
        address_2 = et_business_address_two.getText().toString().trim();
        location = et_location.getText().toString().trim();

        if (Validation.nullValidator(first_name)) {
            Utils.showSnackBar(rootBusinessSignupSecond, "Please Fill First Name", et_first_name, getActivity());
        } else if (Validation.nullValidator(last_name)) {
            Utils.showSnackBar(rootBusinessSignupSecond, "Please Fill Last Name", et_last_name, getActivity());
        } else if (Validation.nullValidator(dob)) {
            Utils.showSnackBar(rootBusinessSignupSecond, "Please Fill Date of Birth", et_dob, getActivity());
        } else if (Validation.nullValidator(country)) {
            Utils.showSnackBar(rootBusinessSignupSecond, "Please Fill Country", et_country, getActivity());
        }else if (Validation.nullValidator(city)) {
            Utils.showSnackBar(rootBusinessSignupSecond, "Please Fill City", et_city, getActivity());
        }else if (Validation.nullValidator(address_1)) {
            Utils.showSnackBar(rootBusinessSignupSecond, "Please Fill Address 1", et_business_address_one, getActivity());
        }else if (Validation.nullValidator(address_2)) {
            Utils.showSnackBar(rootBusinessSignupSecond, "Please Fill Address 2", et_business_address_two, getActivity());
        }else if (Validation.nullValidator(location)) {
            Utils.showSnackBar(rootBusinessSignupSecond, "Please Fill Location", et_location, getActivity());
        } else {
            completeBusinessRegisterForm();
        }
    }

    private void completeBusinessRegisterForm() {

        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.businessUserPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("title", spinner_title.getSelectedItem().toString()));
        nameValuePairs.add(new BasicNameValuePair("first_name", et_first_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("last_name", et_last_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("date_of_birth", et_dob.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("email", Constants.businessUserPOJO.getUserEmail()));
        nameValuePairs.add(new BasicNameValuePair("mobile", Constants.businessUserPOJO.getUserMobile()));
        nameValuePairs.add(new BasicNameValuePair("country", et_country.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("city", et_city.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("address1", et_business_address_one.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("address2", et_business_address_two.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("location", et_location.getText().toString()));


        new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optBoolean("isSuccess")){
                        Gson gson = new Gson();
                        BusinessUserPOJO businessUserPOJO = gson.fromJson(jsonObject.optJSONObject("Result").toString(), BusinessUserPOJO.class);
                        Constants.businessUserPOJO = businessUserPOJO;
                        Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.BUSINESS_USER_POJO, jsonObject.optJSONObject("Result").toString());
                        getActivity().startActivity(new Intent(getActivity(), HomeHandlerBusiness.class));
                        getActivity().finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"BUSINESS_REGISTRATION",true).execute(BusinessWebserviceUrl.BUSINESS_REGISTRATION);

//        progressView.showLoader();
//
//        ApiInterface apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
//        Call<ApiResponse> call = apiInterface.user_register
//                (companyName,
//                        brandName,
//                        contactPersonName,
//                        addressOne,
//                        addressTwo,
//                        Landmark,
//                        city,
//                        pincode,
//                        MyApplication.readStringPref(PrefData.PREF_HOSPITALITY_STATUS),
//                        hiringCapacity,
//                        "Android",
//                        MyApplication.readStringPref(PrefData.PREF_FCM_TOKEN),
//                        MyApplication.readStringPref(PrefData.PREF_DEVICE_ID),
//                        MyApplication.readStringPref(PrefData.PREFNUSINESSID));
//
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                if (response.body().getStatus() == 1) {
//
//                    String bId = response.body().getResult().getBusiness_id();
//                    String cName = response.body().getResult().getCompanyName();
//                    String emailID = response.body().getResult().getEmail();
//                    String password = response.body().getResult().getBusiness_id();
//                    String bName = response.body().getResult().getBrandName();
//                    String conName = response.body().getResult().getContactName();
//                    String mNumber = response.body().getResult().getMobileNumber();
//                    String add1 = response.body().getResult().getAddress1();
//                    String add2 = response.body().getResult().getAddress2();
//                    String cit = response.body().getResult().getCity();
//                    String pin = response.body().getResult().getPincode();
//                    String capicity = response.body().getResult().getCapicity();
//
//                    ((SignupBusinessHandler) getActivity()).changeFragment(new SignupBusinessConfirmFirm(), "signupConfirm");
//                } else {
//                    Utils.showSnackBar(rootBusinessSignupSecond, response.body().getMessage(), getActivity());
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

//        getActivity().startActivity(new Intent(getActivity(), HomeHandlerBusiness.class));
//        getActivity().finish();

    }

    private void initialize() {
        progressView = new ProgressView(getActivity());
//        register = view.findViewById(R.id.btn_signusbusiness_company_register);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101&&resultCode== Activity.RESULT_OK){
            String address=data.getStringExtra("address");
            String lat=data.getStringExtra("lat");
            String longi=data.getStringExtra("long");

            et_location.setText(address);

        }
    }
}
