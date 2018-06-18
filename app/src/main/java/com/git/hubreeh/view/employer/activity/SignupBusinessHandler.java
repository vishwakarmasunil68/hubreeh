package com.git.hubreeh.view.employer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.ActivityManager;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.utility.FinalKeywordsClass;
import com.git.hubreeh.view.employer.fragment.SignupBusinessSignup;
import com.git.hubreeh.view.employer.fragment.SignupOtpBusiness;
import com.git.hubreeh.view.jobseeker.fragment.SignupBusinessCompanyDetails;
import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentEmail;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupBusinessHandler extends ActivityManager {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String email = "", username = "", pass = "", role = "", socialtype = "", gender = "", fbId = "", profilePicUrl = "";
    private String TAG="SignupBusinessHandler=>";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_business_handler);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Bundle intdata = getIntent().getExtras();

        if (getIntent().hasExtra("direct")) {
            changeFragment(new SignupOtpBusiness(), "signupBusinessSignup");
        } else if (getIntent().hasExtra("direct2")) {
            changeFragment(new SignupBusinessCompanyDetails(), "businessCompanyDetails");
        } else {
            if ((MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("FACEBOOK"))) {
                username = intdata.getString(FinalKeywordsClass.USERNAME);
                socialtype = intdata.getString(FinalKeywordsClass.SOCIAL_TYPE);
                email = intdata.getString(FinalKeywordsClass.EMAIL);
                Log.v(TAG, "Fb Data");
                Log.v(FinalKeywordsClass.USERNAME, username);
                Log.v(FinalKeywordsClass.SOCIAL_TYPE, username);
                Log.v(FinalKeywordsClass.EMAIL, email);

                changeFragment(new SignupBusinessSignup(), "signupemail");
            }else {
//                changeFragment(new SignupBusinessSignup(), "signupBusinessSignup");
                startFragment(R.id.frame_signup_business,new SignupBusinessSignup());
//                startFragment(R.id.frame_signup_business,new SignupBusinessCompanyDetails());
            }



        }


    }


    public void changeFragment(Fragment targetFragment, String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_signup_business, targetFragment)
                .addToBackStack(name)
                .commit();
    }


//    @Override
//    public void onBackPressed() {
//        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_signup_business);
//        if (currentFragment instanceof SignupBusinessSignup) {
//            finish();
//        } else {
//            if (currentFragment instanceof SignupBusinessCompanyDetails) {
//                confirm();
//            } else if (currentFragment instanceof SignupOtpBusiness) {
//                confirm();
//            }
//            // super.onBackPressed();
//        }
//
//
//    }

    private void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you really want to cancel your sign up process.?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeFragment(new SignupBusinessSignup(), "signupBusinessSignup");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setUpToolbar(String title, String subTitle, boolean home, boolean btn) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(home);
        getSupportActionBar().setHomeButtonEnabled(btn);
    }
}
