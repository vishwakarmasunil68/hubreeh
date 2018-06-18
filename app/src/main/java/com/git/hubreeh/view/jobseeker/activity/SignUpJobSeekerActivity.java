package com.git.hubreeh.view.jobseeker.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.ActivityManager;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.model.jobseeker.AddJobModel;
import com.git.hubreeh.model.jobseeker.CategoryBean;
import com.git.hubreeh.model.jobseeker.DaysModel;
import com.git.hubreeh.model.jobseeker.HearUsModel;
import com.git.hubreeh.utility.FinalKeywordsClass;
import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentDescribeYourself;
import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentDetails;
import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentEmail;
import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentPreviousJobs;
import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentSetYourRate;
import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentTaxInformation;
import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentWhatCanYouDo;
import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentWhatDoYouLike;
import com.git.hubreeh.view.jobseeker.fragment.SignupOtpJobseeker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpJobSeekerActivity extends ActivityManager {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public static List<CategoryBean.HospitalityBean> hospitalityBeans = new ArrayList<>();
    public static List<CategoryBean.WarehousingBean> warehousingBeans = new ArrayList<>();
    public static List<DaysModel> daysModels = new ArrayList<>();
    public static List<HearUsModel> hearUsModels = new ArrayList<>();
    public static String path = "";
    public static String aboutMe = "";
    public static String rate = "";
    public static String NIN = "";
    public static int finance = 0;
    public static int lone = 0;
    public static boolean workhours = false;
    public static boolean fullTime = false;
    public static boolean waiting = false;

    String email = "", username = "", pass = "", role = "", socialtype = "", gender = "", fbId = "", profilePicUrl = "";
    private String TAG = "SignUpJobSeekerActivity=>";

    Intent intent;
    Bundle bundle;
    SignupFragmentEmail fragmentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        /*
        username = bundle.getString();
        bundle.getString();
         bundle.getString();*/
        intent=getIntent();
        socialtype = intent.getStringExtra(FinalKeywordsClass.SOCIAL_TYPE);
        username = intent.getStringExtra(FinalKeywordsClass.USERNAME);
        email = intent.getStringExtra(FinalKeywordsClass.EMAIL);
//        Log.v(TAG, "Fb Data");
//        Log.v(FinalKeywordsClass.USERNAME, username);
//        Log.v(FinalKeywordsClass.SOCIAL_TYPE, socialtype);
//        Log.v(FinalKeywordsClass.EMAIL, email);
        bundle = new Bundle();



        if (getIntent().hasExtra("direct")) {
            changeFragment(new SignupOtpJobseeker(), "signupotp", R.id.frame_container);
        } else if (getIntent().hasExtra("direct2")) {
            changeFragment(new SignupFragmentDetails(), "signupdetails", R.id.frame_container);
        } else {
            if ((MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("FACEBOOK"))) {
                bundle.putString(FinalKeywordsClass.USERNAME, username);
                bundle.putString(FinalKeywordsClass.SOCIAL_TYPE, socialtype);
                bundle.putString(FinalKeywordsClass.EMAIL, email);
                fragmentEmail = new SignupFragmentEmail();
                fragmentEmail.setArguments(bundle);
                changeFragment(fragmentEmail, "signupemail", R.id.frame_container);
            } else {
//                changeFragment(new SignupFragmentEmail(), "signupemail", R.id.frame_container);
                startFragment(R.id.frame_container,new SignupFragmentEmail());
//                startFragment(R.id.frame_container,new SignupFragmentDetails());
            }
        }
    }

    public void changeFragment(Fragment targetFragment, String name, int containerId) {

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(containerId, targetFragment)
                .addToBackStack(name)
                .commit();


    }


    @Override
    public void onBackPressed() {
        final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);

        if (currentFragment instanceof SignupFragmentDetails) {
            confirm();
        } else if (currentFragment instanceof SignupOtpJobseeker) {
            confirm();
        } else {
            if (currentFragment instanceof SignupFragmentEmail) {
                Bundle args = new Bundle();
                finish();
            } else if (currentFragment instanceof SignupFragmentPreviousJobs) {
                currentFragment.getChildFragmentManager().popBackStack("signupdetails", 0);
            } else if (currentFragment instanceof SignupFragmentWhatCanYouDo) {
                currentFragment.getChildFragmentManager().popBackStack("signuppreviousjob", 0);
            } else if (currentFragment instanceof SignupFragmentSetYourRate) {
                currentFragment.getChildFragmentManager().popBackStack("signupwhatcanyoudo", 0);
            } else if (currentFragment instanceof SignupFragmentWhatDoYouLike) {
                currentFragment.getChildFragmentManager().popBackStack("fragmentrate", 0);
            } else if (currentFragment instanceof SignupFragmentDescribeYourself) {
                currentFragment.getChildFragmentManager().popBackStack("fragmentlooklike", 0);
            } else if (currentFragment instanceof SignupFragmentTaxInformation) {
                currentFragment.getChildFragmentManager().popBackStack("fragmentdescribe", 0);
            }
            super.onBackPressed();
        }
    }

    private void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you really want to cancel your sign up process.?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SignUpJobSeekerActivity.hospitalityBeans.clear();
                SignUpJobSeekerActivity.warehousingBeans.clear();
                SignUpJobSeekerActivity.daysModels.clear();
                SignUpJobSeekerActivity.hearUsModels.clear();
                SignUpJobSeekerActivity.path = "";
                SignUpJobSeekerActivity.aboutMe = "";
                SignUpJobSeekerActivity.rate = "";
                SignUpJobSeekerActivity.workhours = false;
                SignUpJobSeekerActivity.fullTime = false;
                SignUpJobSeekerActivity.NIN = "";
                SignUpJobSeekerActivity.finance = 0;
                SignUpJobSeekerActivity.lone = 0;
                changeFragment(new SignupFragmentEmail(), "signupemail", R.id.frame_container);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    public void setUpToolbar(String title, String subTitle, boolean home, boolean btn) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(home);
        getSupportActionBar().setHomeButtonEnabled(btn);
    }


}
