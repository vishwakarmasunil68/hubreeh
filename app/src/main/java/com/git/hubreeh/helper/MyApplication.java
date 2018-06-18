package com.git.hubreeh.helper;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDex;
import android.util.Base64;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;


public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;
    public static String SHARED_PREFERENCES_NAME = "shared_myfore_seller";


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        printHashKey();
        mInstance = this;

    }

    private void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.git.hubreeh",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public static void clearPref() {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();

    }

    public static void clearKeyPref(String key) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.commit();
    }

    public static String readStringPref(String key) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void writeStringPref(String key, String data) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, data);
        edit.commit();
    }

    public static boolean readBooleanPref(String key) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static void writeBooleanPref(String key, boolean data) {
        SharedPreferences prefs = mInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(key, data);
        edit.commit();

    }

}
