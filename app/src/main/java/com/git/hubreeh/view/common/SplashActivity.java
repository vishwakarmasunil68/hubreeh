package com.git.hubreeh.view.common;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.git.hubreeh.R;
import com.git.hubreeh.helper.Config;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.view.employer.activity.HomeHandlerBusiness;
import com.git.hubreeh.view.jobseeker.activity.HomeHandlerJobseeker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;


public class SplashActivity extends AppCompatActivity {
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    Intent intent;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static GoogleApiClient mGoogleApiClient;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initGoogleAPIClient();//Init Google API Client
        checkPermissions();
    }


    private void initGoogleAPIClient() {
        //Without Google API Client Auto Location Dialog will not work
        mGoogleApiClient = new GoogleApiClient.Builder(SplashActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(SplashActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();

    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        goNext();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(SplashActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    private void goNext() {
        if (!MyApplication.readStringPref(PrefData.PREF_FCM_TOKEN).equalsIgnoreCase("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    if (MyApplication.readStringPref(PrefData.LOGIN_STATUS).equalsIgnoreCase("1")) {
                        if (MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("job")) {
                            startActivity(new Intent(SplashActivity.this, HomeHandlerJobseeker.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            finish();
                        } else {
                            startActivity(new Intent(SplashActivity.this, HomeHandlerBusiness.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            finish();
                        }

                    } else {
                        intent = new Intent(SplashActivity.this, StarterActivity.class);
                        startActivity(intent);
                        finish();
                    }


                }
            }, 1500);
        } else {
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                        if (MyApplication.readStringPref(PrefData.LOGIN_STATUS).equalsIgnoreCase("1")) {
                            if (MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("job")) {
                                startActivity(new Intent(SplashActivity.this, HomeHandlerJobseeker.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                finish();
                            } else {
                                startActivity(new Intent(SplashActivity.this, HomeHandlerBusiness.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                finish();
                            }

                        } else {
                            Intent intent1 = new Intent(SplashActivity.this, StarterActivity.class);
                            startActivity(intent1);
                            finish();
                        }


                    }
                }
            };
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_INTENT_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mGoogleApiClient == null) {
                        initGoogleAPIClient();
                        showSettingDialog();
                    } else
                        showSettingDialog();
                } else {
                    requestLocationPermission();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        goNext();
                        //startLocationUpdates();
                        break;
                    case RESULT_CANCELED:
                        showSettingDialog();
                        break;
                }
                break;
        }
    }

}
