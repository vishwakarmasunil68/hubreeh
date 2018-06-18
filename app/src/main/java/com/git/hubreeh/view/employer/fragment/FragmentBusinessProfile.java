package com.git.hubreeh.view.employer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponse;
import com.git.hubreeh.R;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.utility.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 1/25/2018.
 */

public class FragmentBusinessProfile extends Fragment {
    View view;
    @BindView(R.id.et_profile_firstname)
    AppCompatEditText etProfileFirstname;
    @BindView(R.id.et_profile_lastname)
    AppCompatEditText etProfileLastname;
    @BindView(R.id.et_profile_address_one)
    AppCompatEditText etProfileAddressOne;
    @BindView(R.id.et_profile_address_two)
    AppCompatEditText etProfileAddressTwo;
    @BindView(R.id.et_profile_city)
    AppCompatEditText etProfileCity;
    @BindView(R.id.et_profile_pincode)
    AppCompatEditText etProfilePincode;
    @BindView(R.id.et_profile_state)
    AppCompatEditText etProfileState;
    @BindView(R.id.et_profile_country)
    AppCompatEditText etProfileCountry;
    @BindView(R.id.et_profile_company)
    AppCompatEditText etProfileCompany;
    @BindView(R.id.et_profile_timezone)
    AppCompatEditText etProfileTimezone;
    @BindView(R.id.et_profile_location)
    AppCompatEditText etProfileLocation;
    @BindView(R.id.sp_profile_website)
    AppCompatSpinner spProfileWebsite;
    @BindView(R.id.sp_profile_project)
    AppCompatSpinner spProfileProject;


    List<String> websiteSpinnerList = new ArrayList<>();
    List<String> projectSpinnerList = new ArrayList<>();

    Unbinder unbinder;
    @BindView(R.id.tvMobile)
    AppCompatTextView tvMobile;
    @BindView(R.id.tvCountry)
    AppCompatTextView tvCountry;
    @BindView(R.id.btnSave)
    AppCompatButton btnSave;
    ProgressView progressView;
    @BindView(R.id.root)
    LinearLayout root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_business_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        progressView = new ProgressView(getActivity());
        websiteSpinnerList.clear();
        websiteSpinnerList.add("English");
        websiteSpinnerList.add("Hindi");
        websiteSpinnerList.add("Spanish");
        websiteSpinnerList.add("Dutch");

        projectSpinnerList.clear();
        projectSpinnerList.add("English");
        projectSpinnerList.add("Hindi");
        projectSpinnerList.add("Spanish");
        projectSpinnerList.add("Dutch");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, websiteSpinnerList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProfileWebsite.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, projectSpinnerList);
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProfileProject.setAdapter(spinnerArrayAdapter1);


        try {
            JSONObject object1 = new JSONObject(MyApplication.readStringPref(PrefData.PREF_BUSINESS_DATA));
            JSONObject object = object1.getJSONObject("result");
            Log.e("dd", object1.toString());
            etProfileFirstname.setText(object.getString("companyName"));
            etProfileLastname.setText(object.getString("email"));
            etProfileAddressOne.setText(object.getString("address1"));
            etProfileAddressTwo.setText(object.getString("address2"));
            etProfileCity.setText(object.getString("city"));
            etProfilePincode.setText(object.getString("pincode"));
            etProfileState.setText(object.getString("landmark"));
            etProfileCountry.setText(object.getString("landmark"));
            etProfileCompany.setText(object.getString("companyName"));
            tvMobile.setText("Phone : " + object.getString("mobileNumber"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactFName = etProfileFirstname.getText().toString();
                String contactLName = etProfileLastname.getText().toString();
                String address1 = etProfileAddressOne.getText().toString();
                String address2 = etProfileAddressTwo.getText().toString();
                String city = etProfileCity.getText().toString();
                String pincode = etProfilePincode.getText().toString();
                String landmark = etProfileState.getText().toString();


                if (Validation.nullValidator(contactFName)) {
                    Utils.showSnackBar(root, "Enter First Name", etProfileFirstname, getActivity());
                } else if (Validation.nullValidator(contactLName)) {
                    Utils.showSnackBar(root, "Enter Last Name", etProfileLastname, getActivity());
                } else if (Validation.nullValidator(address1)) {
                    Utils.showSnackBar(root, "Enter Address ", etProfileLastname, getActivity());
                } else if (Validation.nullValidator(address2)) {
                    Utils.showSnackBar(root, "Enter Address ", etProfileLastname, getActivity());
                } else if (Validation.nullValidator(city)) {
                    Utils.showSnackBar(root, "Enter City ", etProfileLastname, getActivity());
                } else if (Validation.nullValidator(pincode)) {
                    Utils.showSnackBar(root, "Enter Pin Code ", etProfileLastname, getActivity());
                } else if (Validation.nullValidator(landmark)) {
                    Utils.showSnackBar(root, "Enter Landmark", etProfileLastname, getActivity());
                } else {
                    updateSettings(contactFName + " " + contactLName, address1, address2, city, pincode, landmark);
                }


            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void updateSettings(String contactName, String address1, String address2, String city, String pincode, String landmark) {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.update_profile_details
                (contactName,
                        address1,
                        address2,
                        city,
                        pincode,
                        landmark,
                        MyApplication.readStringPref(PrefData.PREFNUSINESSID));

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    Utils.showSnackBar(root, response.body().getMessage(), getActivity());
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), getActivity());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });

    }
}
