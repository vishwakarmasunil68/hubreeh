package com.git.hubreeh.view.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.git.hubreeh.R;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.utility.FinalKeywordsClass;
import com.git.hubreeh.view.employer.activity.SignupBusinessHandler;
import com.git.hubreeh.view.jobseeker.activity.SignUpJobSeekerActivity;
import com.rd.PageIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarterActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @BindView(R.id.tv_skip)
    AppCompatTextView skip;
    private int resultcode = -1, request_code = 0;
    private int fbcode = 112;
    String email = "", username = "", pass = "", role = "", socialtype = "", gender = "", fbId = "", profilePicUrl = "";
    private String firstname, lastname, name, mobileNumber, social_id, link, password, social_type, passwordfb, countrycode = "", plush = "+", country;

    private CallbackManager callbackManager;
    private String TAG = "StarterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_starter);
        ButterKnife.bind(this);
        setFacebook_sdk();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        changeStatusBarColor();

        initialize();

        viewPager.setAdapter(new MyPagerAdapter(StarterActivity.this));
        pageIndicatorView.setViewPager(viewPager);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    skip.setVisibility(View.GONE);
                } else {
                    skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setFacebook_sdk() {
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
    }

    private void initialize() {
        viewPager = findViewById(R.id.viewpager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        skip = findViewById(R.id.tv_skip);
    }

    public class MyPagerAdapter extends PagerAdapter {
        Context mContext;

        MyPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
            LayoutInflater inflater = LayoutInflater.from(mContext);

            final ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);

            if (customPagerEnum.getLayoutResId() == R.layout.fragment_starter_four_layout) {

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == 3) {

                            AppCompatTextView signin = layout.findViewById(R.id.tv_signin);
                            AppCompatButton btn_fbsignup = layout.findViewById(R.id.btn_fbsignup);
                            AppCompatButton btn_signup = layout.findViewById(R.id.btn_signup);

                            final RadioButton rbSeeker = layout.findViewById(R.id.rbSeeker);
                            final RadioButton rbBusiness = layout.findViewById(R.id.rbBusiness);
                            role = "";

                            if (!(role.equalsIgnoreCase("seeker"))) {
                                rbSeeker.setChecked(true);
                                rbBusiness.setChecked(false);
                                rbSeeker.setTextColor(getResources().getColor(R.color.colorAccent));
                                rbBusiness.setTextColor(getResources().getColor(R.color.black));

                            } else {
                                rbBusiness.setChecked(true);
                                rbSeeker.setChecked(false);
//                                rbBusiness.setTextColor(getResources().getColor(R.color.colorAccent));
//                                rbSeeker.setTextColor(getResources().getColor(R.color.black));
                            }


                            //Fbsingup
                            btn_fbsignup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fbsigninMethod();
                                    if (rbBusiness.isChecked()) {
//                                        rbBusiness.setTextColor(getResources().getColor(R.color.colorAccent));
//                                        rbSeeker.setTextColor(getResources().getColor(R.color.black));
                                        role = "business";
                                    } else if (rbSeeker.isChecked()) {
                                        role = "seeker";
//                                        rbSeeker.setTextColor(getResources().getColor(R.color.colorAccent));
//                                        rbBusiness.setTextColor(getResources().getColor(R.color.black));

                                    }
                                    socialtype = "FACEBOOK";
                                    MyApplication.writeStringPref(PrefData.LOGIN_TYPE, role);

//                                    gotoSignupActivity(role, socialtype);
                                }
                            });
                            //MainualSignup
                            btn_signup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (rbBusiness.isChecked()) {
                                        role = "business";
//                                        rbBusiness.setTextColor(getResources().getColor(R.color.colorAccent));
//                                        rbSeeker.setTextColor(getResources().getColor(R.color.black));
                                    } else if (rbSeeker.isChecked()) {
                                        role = "seeker";
//                                        rbSeeker.setTextColor(getResources().getColor(R.color.colorAccent));
//                                        rbBusiness.setTextColor(getResources().getColor(R.color.black));
                                    }
                                    socialtype = "blank";
                                    gotoSignupActivity(role, socialtype);

                                }
                            });


                            signin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(StarterActivity.this, LoginActivity.class));
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                }
                            });
//                            jobSeeker.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    MyApplication.writeStringPref(PrefData.LOGIN_TYPE, "job");
//                                    startActivity(new Intent(StarterActivity.this, SignUpJobSeekerActivity.class));
//                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                                }
//                            });
//                            business.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    MyApplication.writeStringPref(PrefData.LOGIN_TYPE, "bus");
//                                    startActivity(new Intent(StarterActivity.this, SignupBusinessHandler.class));
//                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
//                                }
//
                        }
                    }


                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
            }
            collection.addView(layout);
            return layout;
        }


        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return CustomPagerEnum.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
            return mContext.getString(customPagerEnum.getTitleResId());
        }
    }

    private void gotoSignupActivity(String mode, String socialType) {

        Log.e("Mode", mode + "\nSocialTye:" + socialType + "");

        if (socialType.equalsIgnoreCase("FACEBOOK")) {
            Intent fbint;
            MyApplication.writeStringPref(PrefData.LOGIN_TYPE, socialtype);
            if (!mode.equalsIgnoreCase("business")) {

//                    getFb Data
                MyApplication.writeStringPref(PrefData.LOGIN_TYPE, "job");
                fbint = new Intent(StarterActivity.this, SignUpJobSeekerActivity.class);
                fbint.putExtra(FinalKeywordsClass.SOCIAL_TYPE, socialtype);
                fbint.putExtra(FinalKeywordsClass.USERNAME, username);
                fbint.putExtra(FinalKeywordsClass.EMAIL, email);
                startActivity(fbint);
                Log.v(TAG, "\nName:" + name + "\nemail:" + email + "\nSocialType:" + socialType);
                MyApplication.writeStringPref(FinalKeywordsClass.EMAIL, email + "");

                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else {
//                    getFb Data
                MyApplication.writeStringPref(PrefData.LOGIN_TYPE, "bus");
                fbint = new Intent(StarterActivity.this, SignupBusinessHandler.class);
                fbint.putExtra(FinalKeywordsClass.SOCIAL_TYPE, socialType);
                fbint.putExtra(FinalKeywordsClass.USERNAME, username);
                fbint.putExtra(FinalKeywordsClass.EMAIL, email);
                startActivity(fbint);
                Log.v(TAG, "\nName:" + name + "\nemail:" + email);
                MyApplication.writeStringPref(FinalKeywordsClass.EMAIL, email + "");

                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }

        } else {
            if (!mode.equalsIgnoreCase("business")) {
//                    getBlank Data
                MyApplication.writeStringPref(PrefData.LOGIN_TYPE, "job");
                startActivity(new Intent(StarterActivity.this, SignUpJobSeekerActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else {
//                    getBlank Data
                MyApplication.writeStringPref(PrefData.LOGIN_TYPE, "bus");
                startActivity(new Intent(StarterActivity.this, SignupBusinessHandler.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }

        }

    }


    private void fbsigninMethod() {
        resultcode = fbcode;
        request_code = 101;
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

    }

    public enum CustomPagerEnum {
        ONE(R.string.one, R.layout.fragment_starter_one_layout),
        TWO(R.string.two, R.layout.fragment_starter_two_layout),
        THREE(R.string.three, R.layout.fragment_starter_three_layout),
        FOUR(R.string.four, R.layout.fragment_starter_four_layout);

        private int mTitleResId;
        private int mLayoutResId;

        CustomPagerEnum(int titleResId, int layoutResId) {
            mTitleResId = titleResId;
            mLayoutResId = layoutResId;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }
    }


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (request_code == 101) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            RequestFbData();
        }
    }

    private void RequestFbData() {

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("response", response.toString());
                        JSONObject json = response.getJSONObject();
                        try {
                            if (json != null) {

                                firstname = json.getString("first_name");
                                lastname = json.getString("last_name");

                                name = json.getString("name");
                                social_id = json.getString("id");

                                if (json.getJSONObject("picture").getJSONObject("data") != null)
                                    link = json.getJSONObject("picture").getJSONObject("data").getString("url");
                                else
                                    link = "";

                                try {


                                    if (object.has("email")) {
                                        email = object.getString("email");
                                    } else {
                                        email = "";
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                social_type = "FACEBOOK";
                                passwordfb = "";
                                Log.e("data:", "\nName:" + name + "\nFristName:" + firstname + "\nLastName:" + lastname + "\nSocialId:" + social_id + "\nlink:" + link + "\nemail:" + email);

//                                if status true in response then redirect to main page
//                                otherwise open signuppage
                                LoginManager.getInstance().logOut();
                                gotoSignupActivity(role, socialtype);
//                                CommonMethod.setPrefrence(UserLoginActivity.this, "UserLogin", "true");
//                                serverRequestForCheckUser(social_id, social_type, emailaddress);


//
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,first_name,last_name,picture.width(150).height(150),birthday");
        request.setParameters(parameters);
        request.executeAsync();
        Log.v("requestdata=>", request + "");
    }
}
