package com.git.hubreeh.view.employer.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponse;
import com.git.hubreeh.R;
import com.git.hubreeh.helper.GPSTracker;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.helper.Validation;
import com.git.hubreeh.model.jobseeker.CategoryBean;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.employer.activity.MapsActivity;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hp on 1/24/2018.
 */

public class PostNewJobFragment extends Fragment implements View.OnClickListener {

    View view;
    @BindView(R.id.sp_business_category)
    AppCompatSpinner spBusinessCategory;
    @BindView(R.id.sp_business_subcategory)
    AppCompatSpinner spBusinessSubcategory;
    @BindView(R.id.btn_upload_image)
    AppCompatButton btnUploadImage;
    @BindView(R.id.iv_job_pic)
    RoundedImageView ivJobPic;
    @BindView(R.id.tv_pick_date)
    AppCompatTextView tvPickDate;
    @BindView(R.id.tv_pick_time)
    AppCompatTextView tvPickTime;
    @BindView(R.id.btn_post_job)
    AppCompatTextView btnPostJob;
    @BindView(R.id.et_use_current_location)
    AppCompatEditText etUseCurrentLocation;
    String lat = "", longx = "";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ProgressView progressView;
    ApiInterface apiInterface;

    String[] type = {"Hospitality", "Warehousing"};
    List<CategoryBean.HospitalityBean> hospitalityBeans;
    List<CategoryBean.WarehousingBean> warehousingBeans;

    Unbinder unbinder;
    @BindView(R.id.root)
    LinearLayout root;
    String path = "";
    @BindView(R.id.rbUrgent)
    RadioButton rbUrgent;
    @BindView(R.id.rbSchedule)
    RadioButton rbSchedule;
    @BindView(R.id.rbno)
    RadioButton rbno;
    @BindView(R.id.rbYes)
    RadioButton rbYes;
    @BindView(R.id.rbcash)
    RadioButton rbcash;
    @BindView(R.id.rbCredit)
    RadioButton rbCredit;
    @BindView(R.id.etname)
    AppCompatEditText etname;
    @BindView(R.id.etdes)
    AppCompatEditText etdes;
    @BindView(R.id.etBudget)
    AppCompatEditText etBudget;
    @BindView(R.id.rgLoc)
    RadioGroup rgLoc;
    @BindView(R.id.etRadius)
    AppCompatEditText etRadius;

    GPSTracker gpsTracker;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_new_job, container, false);
        unbinder = ButterKnife.bind(this, view);
        gpsTracker = new GPSTracker(getActivity());
        progressView = new ProgressView(getActivity());
        apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
        hospitalityBeans = new ArrayList<>();
        warehousingBeans = new ArrayList<>();
        spBusinessCategory.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, type));
        tvPickDate.setOnClickListener(this);
        tvPickTime.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);

        etUseCurrentLocation.setEnabled(false);
        spBusinessCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spBusinessCategory.getSelectedItem().toString().equalsIgnoreCase("Hospitality")) {
                    String[] hos = new String[hospitalityBeans.size()];
                    for (int i = 0; i < hospitalityBeans.size(); i++) {
                        hos[i] = hospitalityBeans.get(i).getName();
                    }
                    spBusinessSubcategory.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, hos));
                } else {

                    String[] hos = new String[warehousingBeans.size()];
                    for (int i = 0; i < warehousingBeans.size(); i++) {
                        hos[i] = warehousingBeans.get(i).getName();
                    }
                    spBusinessSubcategory.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, hos));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        connectApiToGetCategories();


        rbUrgent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat time = new SimpleDateFormat("HH:mm a");
                if (isChecked) {
                    tvPickDate.setText(date.format(c.getTime()));
                    tvPickTime.setText(time.format(c.getTime()));
                    tvPickTime.setEnabled(false);
                    tvPickDate.setEnabled(false);
                } else {
                    tvPickDate.setText("Pick Date");
                    tvPickTime.setText("Pick Time");
                    tvPickTime.setEnabled(true);
                    tvPickDate.setEnabled(true);
                }

            }
        });

        rgLoc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbYes) {
                    etUseCurrentLocation.setEnabled(false);
                } else {
                    etUseCurrentLocation.setEnabled(true);
                }
            }
        });


        etUseCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivityForResult(i, 105);
            }
        });


        btnPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "";
                String loc = "";
                String pay = "";

                if (rbUrgent.isChecked()) {
                    type = "urgent";
                } else if (rbSchedule.isChecked()) {
                    type = "schedule";
                }


                if (rbYes.isChecked()) {
                    loc = "yes";
                } else if (rbno.isChecked()) {
                    loc = "no";
                }

                if (rbcash.isChecked()) {
                    pay = "cash";
                } else if (rbCredit.isChecked()) {
                    pay = "credit";
                }


                if (Validation.nullValidator(etname.getText().toString())) {
                    Utils.showSnackBar(root, "Please Enter Job Name", etname, getActivity());
                } else if (Validation.nullValidator(etdes.getText().toString())) {
                    Utils.showSnackBar(root, "Please Enter More About Job", etdes, getActivity());
                } else if (Validation.nullValidator(etBudget.getText().toString())) {
                    Utils.showSnackBar(root, "Please Enter Your Budget", etBudget, getActivity());
                } else if (Validation.nullValidator(path)) {
                    Utils.showSnackBar(root, "Please Select Image", getActivity());
                } else if (Validation.nullValidator(type)) {
                    Utils.showSnackBar(root, "Please Select Time For Service", getActivity());
                } else if (Validation.nullValidator(loc)) {
                    Utils.showSnackBar(root, "Please Select Location Share Option", getActivity());
                } else if (loc.equalsIgnoreCase("no") && Validation.nullValidator(etUseCurrentLocation.getText().toString())) {
                    Utils.showSnackBar(root, "Please Enter Your Address", getActivity());
                } else if (Validation.nullValidator(etRadius.getText().toString())) {
                    Utils.showSnackBar(root, "Please Enter Your Radius", getActivity());
                } else if (Validation.nullValidator(pay)) {
                    Utils.showSnackBar(root, "Please Select Payment Method", getActivity());
                } else {
                    updatePost();
                }


            }
        });


        return view;
    }


    private void showDatePicker(final AppCompatTextView textView) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DecimalFormat mFormat = new DecimalFormat("00");
                textView.setText(mFormat.format(Double.valueOf(dayOfMonth)) + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }

    private void showTimePicker(final AppCompatTextView textView) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void showPicDialog() {
        ImagePicker.with(getActivity())
                .setToolbarColor("#212121")
                .setStatusBarColor("#000000")
                .setToolbarTextColor("#FFFFFF")
                .setToolbarIconColor("#FFFFFF")
                .setProgressBarColor("#4CAF50")
                .setBackgroundColor("#212121")
                .setCameraOnly(false)
                .setMultipleMode(false)
                .setFolderMode(true)
                .setShowCamera(true)
                .setFolderTitle("Albums")
                .setImageTitle("Galleries")
                .setDoneTitle("Done")
                .setKeepScreenOn(true)
                .start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pick_date:
                showDatePicker(tvPickDate);
                break;
            case R.id.tv_pick_time:
                showTimePicker(tvPickTime);
                break;
            case R.id.btn_post_job:
                break;
            case R.id.btn_upload_image:
                showPicDialog();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images.size() > 0) {
                path = images.get(0).getPath();
                Glide.with(getActivity()).load(images.get(0).getPath()).into(ivJobPic);

            } else {
                Toast.makeText(getActivity(), "Image not found", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 99) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                etUseCurrentLocation.setText(place.getAddress());
            }
        }

        if (requestCode == 105) {
            if (resultCode == Activity.RESULT_OK) {
                lat = data.getStringExtra("lat");
                longx = data.getStringExtra("long");
                etUseCurrentLocation.setText(data.getStringExtra("address"));
            } else {
                Toast.makeText(getActivity(), "Error in getting address try again", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void connectApiToGetCategories() {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        Call<CategoryBean> call = apiInterface.get_categories();

        call.enqueue(new Callback<CategoryBean>() {
            @Override
            public void onResponse(Call<CategoryBean> call, Response<CategoryBean> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    hospitalityBeans.clear();
                    warehousingBeans.clear();
                    hospitalityBeans.addAll(response.body().getHospitality());
                    warehousingBeans.addAll(response.body().getWarehousing());

                    if (spBusinessCategory.getSelectedItem().toString().equalsIgnoreCase("Hospitality")) {
                        String[] hos = new String[hospitalityBeans.size()];
                        for (int i = 0; i < hospitalityBeans.size(); i++) {
                            hos[i] = hospitalityBeans.get(i).getName();
                        }
                        spBusinessSubcategory.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, hos));
                    } else {

                        String[] hos = new String[warehousingBeans.size()];
                        for (int i = 0; i < warehousingBeans.size(); i++) {
                            hos[i] = warehousingBeans.get(i).getName();
                        }
                        spBusinessSubcategory.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, hos));

                    }

                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), getActivity());
                }
            }

            @Override
            public void onFailure(Call<CategoryBean> call, Throwable t) {
                progressView.hideLoader();
                t.printStackTrace();
            }
        });
    }

    private void updatePost() {
        progressView.showLoader();
        MultipartBody.Part profilePic = null;
        File file;
        if (path.equalsIgnoreCase("")) {
            file = new File(path);
            profilePic = MultipartBody.Part.createFormData("userfile", file.getName(), RequestBody.create(MediaType.parse("*/*"), file));
        }


        RequestBody business_id = RequestBody.create(MediaType.parse("text/plain"), MyApplication.readStringPref(PrefData.PREFNUSINESSID));
        RequestBody jobName = RequestBody.create(MediaType.parse("text/plain"), etname.getText().toString());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), etBudget.getText().toString());
        RequestBody aboutJob = RequestBody.create(MediaType.parse("text/plain"), etdes.getText().toString());
        RequestBody urgentDate = RequestBody.create(MediaType.parse("text/plain"), tvPickDate.getText().toString() + " " + tvPickTime.getText().toString());
        RequestBody jobAddress = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody lat = null, longx = null;

        if (rbno.isChecked()) {
            lat = RequestBody.create(MediaType.parse("text/plain"), this.lat);
            longx = RequestBody.create(MediaType.parse("text/plain"), this.longx);
        } else {
            lat = RequestBody.create(MediaType.parse("text/plain"), "" + gpsTracker.getLatitude());
            longx = RequestBody.create(MediaType.parse("text/plain"), "" + gpsTracker.getLongitude());
        }


        RequestBody radius = RequestBody.create(MediaType.parse("text/plain"), etRadius.getText().toString());

        RequestBody isUrgent = null;
        if (rbUrgent.isChecked()) {
            isUrgent = RequestBody.create(MediaType.parse("text/plain"), "1");
        } else {
            isUrgent = RequestBody.create(MediaType.parse("text/plain"), "0");
        }
        RequestBody isJobShared = null;
        if (rbno.isChecked()) {
            isJobShared = RequestBody.create(MediaType.parse("text/plain"), "0");
        } else {
            isJobShared = RequestBody.create(MediaType.parse("text/plain"), "1");
        }

        RequestBody paymentMethod = null;
        if (rbcash.isChecked()) {
            paymentMethod = RequestBody.create(MediaType.parse("text/plain"), "cash");
        } else {
            paymentMethod = RequestBody.create(MediaType.parse("text/plain"), "credit card");
        }


        RequestBody category_id = null;
        if (spBusinessCategory.getSelectedItem().toString().equalsIgnoreCase("Hospitality")) {
            category_id = RequestBody.create(MediaType.parse("text/plain"), hospitalityBeans.get(spBusinessSubcategory.getSelectedItemPosition()).getCategory_id());
        } else {
            category_id = RequestBody.create(MediaType.parse("text/plain"), warehousingBeans.get(spBusinessSubcategory.getSelectedItemPosition()).getCategory_id());
        }

        Call<ApiResponse> call = apiInterface.create_job(profilePic, business_id, category_id, jobName, aboutJob, isUrgent, urgentDate, isJobShared, jobAddress, price, paymentMethod, lat, longx, radius);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressView.hideLoader();
                Utils.showSnackBar(root, response.body().getMessage(), getActivity());

                etname.setText("");
                etdes.setText("");
                etBudget.setText("");
                path = "";
                rbcash.setChecked(false);
                rbCredit.setChecked(false);
                rbno.setChecked(false);
                rbYes.setChecked(true);
                rbSchedule.setChecked(false);
                rbUrgent.setChecked(false);
                tvPickDate.setText("Pick Date");
                tvPickTime.setText("Pick Time");
                etUseCurrentLocation.setText("");
                etRadius.setText("");
                ivJobPic.setImageResource(R.drawable.car_tyre);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressView.hideLoader();
                t.printStackTrace();
            }
        });


    }
}
