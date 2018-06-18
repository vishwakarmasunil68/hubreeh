package com.git.hubreeh.view.jobseeker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.utility.Utils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileEmployer extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ProgressView progressView;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.tvEmail)
    AppCompatTextView tvEmail;
    @BindView(R.id.tvphone)
    AppCompatTextView tvphone;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.tvPrsnName)
    AppCompatTextView tvPrsnName;
    @BindView(R.id.tvAddress)
    AppCompatTextView tvAddress;
    @BindView(R.id.tvCity)
    AppCompatTextView tvCity;
    @BindView(R.id.tvPincode)
    AppCompatTextView tvPincode;
    @BindView(R.id.tvCapacity)
    AppCompatTextView tvCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_employer);
        ButterKnife.bind(this);
        progressView = new ProgressView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getJobDetails();

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

    private void getJobDetails() {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.get_business_details(getIntent().getStringExtra("id"));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressView.hideLoader();
                try {
                    JSONObject object = new JSONObject(response.body().toString());
                    if (object.getString("status").equalsIgnoreCase("1")) {
                        JSONObject mObject = object.getJSONObject("result");
                        tvEmail.setText(mObject.getString("email"));
                        tvphone.setText(mObject.getString("mobileNumber"));
                        tvPrsnName.setText(mObject.getString("contactName"));
                        tvName.setText(mObject.getString("companyName"));
                        tvAddress.setText(mObject.getString("address1") + " " + mObject.getString("address2"));
                        tvCity.setText(mObject.getString("city"));
                        tvPincode.setText(mObject.getString("pincode"));
                        tvCapacity.setText(mObject.getString("capicity"));
                    } else {
                        Utils.showSnackBar(root, object.getString("message"), ViewProfileEmployer.this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }
}
