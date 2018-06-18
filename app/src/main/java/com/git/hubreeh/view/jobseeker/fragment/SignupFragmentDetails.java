package com.git.hubreeh.view.jobseeker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.pojo.user.JobUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.TagUtils;
import com.git.hubreeh.utility.ToastClass;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.employer.activity.MapsActivity;
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

public class SignupFragmentDetails extends FragmentController {
    View view;

    @BindView(R.id.tv_signup_job_fName)
    AppCompatEditText tvSignupJobFName;
    @BindView(R.id.tv_signup_job_lName)
    AppCompatEditText tvSignupJobLName;
    @BindView(R.id.tv_signup_job_date)
    AppCompatEditText tvSignupJobDate;
    @BindView(R.id.cb_signup_job_eligible_uae)
    CheckBox cbSignupJobEligibleUae;
    @BindView(R.id.root_signup_job_three)
    LinearLayout rootSignupJobThree;
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

    String title, firstName, lastName, dob, address_1, address_2, country, city, location;
    ProgressView progressView;
    Unbinder unbinder;
    @BindView(R.id.btn_email_details_continue)
    AppCompatButton btnContinue;
    @BindView(R.id.spTitle)
    Spinner spTitle;
    String[] titles = {"Mr.", "Miss.", "Mrs."};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpView(getActivity(), this, view);
        progressView = new ProgressView(getActivity());

        setFilters();
        spTitle.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, titles));
        title = spTitle.getSelectedItem().toString();
        spTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title = spTitle.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = tvSignupJobFName.getText().toString().trim();
                lastName = tvSignupJobLName.getText().toString().trim();
                dob = tvSignupJobDate.getText().toString().trim();
                country = et_country.getText().toString().trim();
                city = et_city.getText().toString().trim();
                address_1 = et_business_address_one.getText().toString().trim();
                address_2 = et_business_address_two.getText().toString().trim();
                location = et_location.getText().toString().trim();
                if (cbSignupJobEligibleUae.isChecked()) {
                    MyApplication.writeStringPref(PrefData.PREF_ISUAE, "1");
                } else {
                    MyApplication.writeStringPref(PrefData.PREF_ISUAE, "0");
                }

                if (Validation.nullValidator(title)) {
                    Utils.showSnackBar(rootSignupJobThree, "Please fill the title", getActivity());
                } else if (Validation.nullValidator(firstName)) {
                    Utils.showSnackBar(rootSignupJobThree, "Please fill the first name", tvSignupJobFName, getActivity());
                } else if (Validation.nullValidator(dob)) {
                    Utils.showSnackBar(rootSignupJobThree, "Please select the DOB", getActivity());
                } else if (Validation.nullValidator(country)) {
                    Utils.showSnackBar(rootSignupJobThree, "Please Fill Country", et_country, getActivity());
                } else if (Validation.nullValidator(city)) {
                    Utils.showSnackBar(rootSignupJobThree, "Please Fill City", et_city, getActivity());
                } else if (Validation.nullValidator(address_1)) {
                    Utils.showSnackBar(rootSignupJobThree, "Please Fill Address 1", et_business_address_one, getActivity());
                } else if (Validation.nullValidator(address_2)) {
                    Utils.showSnackBar(rootSignupJobThree, "Please Fill Address 2", et_business_address_two, getActivity());
                }
//                else if (Validation.nullValidator(location)) {
//                    Utils.showSnackBar(rootSignupJobThree, "Please Fill Location", et_location, getActivity());
//                }
                else {
                    connectApi(title, firstName, lastName, dob);
                }
            }
        });

        et_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivityForResult(intent, 101);
            }
        });


        tvSignupJobDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.oldDatePicker(getActivity(), tvSignupJobDate);
            }
        });


        if (getActivity().getIntent().hasExtra("direct2")) {
            Log.e("dd", getActivity().getIntent().getStringExtra("obj"));
            try {
                JSONObject object = new JSONObject(getActivity().getIntent().getStringExtra("obj"));

                if (!object.getString("title").equalsIgnoreCase("")) {
                    if (object.getString("title").equalsIgnoreCase("Mr.")) {
                        spTitle.setSelection(0);
                    } else if (object.getString("title").equalsIgnoreCase("Miss.")) {
                        spTitle.setSelection(1);
                    } else if (object.getString("title").equalsIgnoreCase("Mrs.")) {
                        spTitle.setSelection(2);
                    }
                }


                tvSignupJobLName.setText(object.getString("lastName"));
                tvSignupJobFName.setText(object.getString("firstName"));
                tvSignupJobDate.setText(object.getString("dateOfBirth"));

                if (object.getString("isUae").equalsIgnoreCase("0")) {
                    cbSignupJobEligibleUae.setChecked(false);
                } else {
                    cbSignupJobEligibleUae.setChecked(true);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    private void setFilters() {
        tvSignupJobFName.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start, int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z]+")) { // here no space character
                            return cs;
                        }
                        return "";
                    }
                }
        });

        tvSignupJobLName.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start, int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z]+")) { // here no space character
                            return cs;
                        }
                        return "";
                    }
                }
        });
    }

    private void connectApi(String title, String firstName, String lastName, String dob) {
//        progressView.showLoader();
//        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
//        Call<ApiResponseJob> call = apiInterface.user_register1(title,
//                firstName,
//                lastName,
//                dob,
//                MyApplication.readStringPref(PrefData.PREF_ISUAE),
//                MyApplication.readStringPref(PrefData.PREF_JOBID));
//
//        call.enqueue(new Callback<ApiResponseJob>() {
//            @Override
//            public void onResponse(Call<ApiResponseJob> call, Response<ApiResponseJob> response) {
//                progressView.hideLoader();
//                if (response.body().getStatus() == 1) {
//                    ((SignUpJobSeekerActivity) getActivity()).changeFragment(new SignupFragmentPreviousJobs(), "signuppreviousjob", R.id.frame_container);
//                } else {
//                    Utils.showSnackBar(rootSignupJobThree, response.body().getMessage(), getActivity());
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

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.jobUserPOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("title", title));
        nameValuePairs.add(new BasicNameValuePair("first_name", firstName));
        nameValuePairs.add(new BasicNameValuePair("last_name", lastName));
        nameValuePairs.add(new BasicNameValuePair("dob", dob));
        nameValuePairs.add(new BasicNameValuePair("email", Constants.jobUserPOJO.getEmail()));
        nameValuePairs.add(new BasicNameValuePair("mobile", Constants.jobUserPOJO.getMobile()));
        nameValuePairs.add(new BasicNameValuePair("country", country));
        nameValuePairs.add(new BasicNameValuePair("city", city));
        nameValuePairs.add(new BasicNameValuePair("address1", address_1));
        nameValuePairs.add(new BasicNameValuePair("address2", address_2));
        nameValuePairs.add(new BasicNameValuePair("location", location));
        nameValuePairs.add(new BasicNameValuePair("occupation", "job seeker"));
        nameValuePairs.add(new BasicNameValuePair("device_id", "sadcadscsad"));
        if (cbSignupJobEligibleUae.isChecked()) {
            nameValuePairs.add(new BasicNameValuePair("is_uae", "1"));
        } else {
            nameValuePairs.add(new BasicNameValuePair("is_uae", "0"));
        }
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
                        activityManager.startFragment(R.id.frame_container, new SignupFragmentWhatCanYouDo());
                    }
                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CREATE_USER", true).execute(WebServicesUrls.USER_REGISTER_1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignUpJobSeekerActivity) getActivity()).setUpToolbar("Enter Your Details", "3/4", true, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TagUtils.getTag(),"request_code:-"+requestCode);
//        Log.d(TagUtils.getTag(),"result_code:-"+requestCode);
//        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
        try {
            Log.d(TagUtils.getTag(), "address:-" + data.getStringExtra("address"));
            String address = data.getStringExtra("address");
            String lat = data.getStringExtra("lat");
            String longi = data.getStringExtra("long");
            et_location.setText(address);
        }catch (Exception e){
            e.printStackTrace();
        }
//        }
    }
}
