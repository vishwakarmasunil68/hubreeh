package com.git.hubreeh.view.jobseeker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_profile_firstname)
    AppCompatEditText etProfileFirstname;
    @BindView(R.id.et_profile_lastname)
    AppCompatEditText etProfileLastname;
    @BindView(R.id.et_profile_about)
    AppCompatEditText etProfileAbout;
    @BindView(R.id.et_rate)
    AppCompatEditText etRate;
    @BindView(R.id.btnSave)
    AppCompatButton btnSave;
    @BindView(R.id.root)
    LinearLayout root;
    ProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        progressView = new ProgressView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            JSONObject object1 = new JSONObject(MyApplication.readStringPref(PrefData.PREF_JOB_DATA));
            JSONObject object = object1.getJSONObject("result");
            etProfileFirstname.setText(object.getString("firstName"));
            etProfileLastname.setText(object.getString("lastName"));
            etRate.setText(object.getString("rate"));
            etProfileAbout.setText(object.getString("aboutMe"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        String contactFName = etProfileFirstname.getText().toString();
        String contactLName = etProfileLastname.getText().toString();
        String about = etProfileAbout.getText().toString();
        String rate = etRate.getText().toString();

        if (Validation.nullValidator(contactFName)) {
            Utils.showSnackBar(root, "Enter First Name", etProfileFirstname, this);
        } else if (Validation.nullValidator(contactLName)) {
            Utils.showSnackBar(root, "Enter Last Name", etProfileLastname, this);
        } else if (Validation.nullValidator(about)) {
            Utils.showSnackBar(root, "Enter About ", etProfileLastname, this);
        } else if (Validation.nullValidator(rate)) {
            Utils.showSnackBar(root, "Enter Rate ", etProfileLastname, this);
        } else {
            updateSettings(contactFName, contactLName, rate, about);
        }
    }


    private void updateSettings(String fname, String lname, String rate, String about) {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.update_profile_details(fname, lname, about, rate, MyApplication.readStringPref(PrefData.PREF_JOBID));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    Utils.showSnackBar(root, response.body().getMessage(), ProfileActivity.this);
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), ProfileActivity.this);
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
