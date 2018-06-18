package com.git.hubreeh.helper;


import android.content.Intent;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("token",refreshedToken);
        MyApplication.writeStringPref(PrefData.PREF_FCM_TOKEN,refreshedToken);

        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        MyApplication.writeStringPref(PrefData.PREF_DEVICE_ID,android_id);
    }
}