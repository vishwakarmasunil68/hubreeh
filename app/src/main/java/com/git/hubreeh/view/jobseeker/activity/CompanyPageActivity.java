package com.git.hubreeh.view.jobseeker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponseJob;
import com.git.hubreeh.R;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.common.StarterActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyPageActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linMembership, linAvailableBalance, linDepositeFund;
    ImageView back;
    @BindView(R.id.tvLogout)
    AppCompatTextView tvLogout;
    @BindView(R.id.ivAmount)
    AppCompatTextView ivAmount;
    @BindView(R.id.root)
    LinearLayout root;
    ProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_page);
        ButterKnife.bind(this);
        progressView = new ProgressView(this);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);

        initialize();
        getWallet();
        linMembership.setOnClickListener(this);
        linAvailableBalance.setOnClickListener(this);
        linDepositeFund.setOnClickListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = MyApplication.readStringPref(PrefData.PREF_FCM_TOKEN);
                MyApplication.clearPref();
                MyApplication.writeStringPref(PrefData.PREF_FCM_TOKEN, token);
                startActivity(new Intent(CompanyPageActivity.this, StarterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

    }

    private void initialize() {
        linMembership = findViewById(R.id.lin_membership);
        back = findViewById(R.id.back_arrow);
        linAvailableBalance = findViewById(R.id.linear_available_balance);
        linDepositeFund = findViewById(R.id.linear_payment_deposite_fund);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_membership:
                startActivity(new Intent(CompanyPageActivity.this, MembershipActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.linear_available_balance:
                startActivity(new Intent(CompanyPageActivity.this, DepositeFundActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case R.id.linear_payment_deposite_fund:
                startActivity(new Intent(CompanyPageActivity.this, DepositeFundActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }


    public void getWallet() {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        Call<ApiResponseJob> call = apiInterface.get_wallet(MyApplication.readStringPref(PrefData.PREF_JOBID));

        call.enqueue(new Callback<ApiResponseJob>() {
            @Override
            public void onResponse(Call<ApiResponseJob> call, Response<ApiResponseJob> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    ivAmount.setText("AED " + response.body().getAmount());
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), CompanyPageActivity.this);
                }

            }

            @Override
            public void onFailure(Call<ApiResponseJob> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }
}
