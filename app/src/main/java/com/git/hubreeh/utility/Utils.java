package com.git.hubreeh.utility;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.git.hubreeh.view.jobseeker.fragment.SignupFragmentPreviousJobs;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Hp on 2/13/2018.
 */

public class Utils {
    public void changeStatusBarColor(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    public static void showSnackBar(View view, String msg, AppCompatTextView textView, Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        textView.requestFocus();
    }

    public static void showSnackBar(View view, String msg, AutoCompleteTextView textView, Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        textView.requestFocus();
    }

    public static void showSnackBar(View view, String msg, AppCompatEditText editText, Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        editText.requestFocus();
    }

    public static void showSnackBar(View view, String msg, EditText editText, Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        editText.requestFocus();
    }

    public static void showSnackBar(View view, String msg, Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    public static void oldDatePicker(Context context, final AppCompatEditText editText) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DecimalFormat mFormat = new DecimalFormat("00");
                editText.setText(mFormat.format(Double.valueOf(dayOfMonth)) + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.YEAR, -18);
        datePicker.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePicker.show();
    }

    public static void oldDatePicker(Context context, final EditText editText) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DecimalFormat mFormat = new DecimalFormat("00");
                editText.setText(mFormat.format(Double.valueOf(dayOfMonth)) + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.YEAR, -18);
        datePicker.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePicker.show();
    }

    public static void showDatePicker(Context context, final AppCompatEditText editText) {
        final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DecimalFormat mFormat = new DecimalFormat("00");
                SignupFragmentPreviousJobs.oldDate = mFormat.format(Double.valueOf(dayOfMonth)) + "/" + (monthOfYear + 1) + "/" + year;
                editText.setText(mFormat.format(monthOfYear + 1) + "/" + year);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }

    public static void showDatePicker(Context context, final AppCompatEditText editText, String old) {
        Log.e("date", old);
        final Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DecimalFormat mFormat = new DecimalFormat("00");
                editText.setText(mFormat.format(monthOfYear + 1) + "/" + year);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


        Calendar end = Calendar.getInstance();
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            end.setTime(sdf.parse(old));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal.add(end.get(Calendar.YEAR), 0);
        cal.add(end.get(Calendar.MONTH), 0);
        datePicker.getDatePicker().setMinDate(end.getTimeInMillis());
        datePicker.show();
    }


    public static String getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }
        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        int dayCount = (int) diff / (24 * 60 * 60 * 1000);

        return ("" + dayCount);
    }


}
