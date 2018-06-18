package com.git.hubreeh.view.jobseeker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponseGetJob;
import com.git.hubreeh.R;
import com.git.hubreeh.fragmentcontroller.FragmentContants;
import com.git.hubreeh.fragmentcontroller.FragmentController;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.pojo.user.JobUserPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.TagUtils;
import com.git.hubreeh.utility.ToastClass;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.jobseeker.activity.SignUpJobSeekerActivity;
import com.git.hubreeh.webservice.WebServicesCallBack;
import com.git.hubreeh.webservice.WebServicesUrls;
import com.git.hubreeh.webservice.WebUploadService;
import com.google.gson.Gson;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

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
 * Created by Hp on 12/16/2017.
 */

public class SignupFragmentWhatDoYouLike extends FragmentController {
    View view;


    @BindView(R.id.iv_set_profile_jobseeker)
    ImageView ivSetProfileJobseeker;
    @BindView(R.id.root_profile_pic)
    LinearLayout rootProfilePic;
    ProgressView progressView;

    File file;

    Unbinder unbinder;
    @BindView(R.id.btn_look_like_continue)
    AppCompatButton btnContinue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup_what_do_u_look_like, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpView(getActivity(),this,view);
        progressView = new ProgressView(getActivity());

        if (!SignUpJobSeekerActivity.path.equalsIgnoreCase("")) {
            file = new File(SignUpJobSeekerActivity.path);
            Glide.with(getActivity()).load(new File(SignUpJobSeekerActivity.path)).into(ivSetProfileJobseeker);
        }

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SignUpJobSeekerActivity.path.equalsIgnoreCase("")) {
                    ((SignUpJobSeekerActivity) getActivity()).changeFragment(new SignupFragmentDescribeYourself(), "fragmentdescribe", R.id.frame_container);
                } else {
                    connectApiToSendPic();
                }
//                activityManager.startFragment(R.id.frame_container,new SignupFragmentDescribeYourself());

            }
        });

        ivSetProfileJobseeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicDialog();
            }
        });

        if (getActivity().getIntent().hasExtra("direct2")) {
            try {
                JSONObject object = new JSONObject(getActivity().getIntent().getStringExtra("obj"));
                Glide.with(getActivity()).load(object.getString("image")).into(ivSetProfileJobseeker);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    private void connectApiToSendPic() {
//        progressView.showLoader();
//        MultipartBody.Part profilePic = null;
//        profilePic = MultipartBody.Part.createFormData("userfile", "profilePic" + MyApplication.readStringPref(PrefData.PREF_JOBID), RequestBody.create(MediaType.parse("*/*"), file));
//
//        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
//        Call<ApiResponseGetJob> call = apiInterface.Upload_profile_pic(profilePic, MyApplication.readStringPref(PrefData.PREF_JOBID));
//        call.enqueue(new Callback<ApiResponseGetJob>() {
//            @Override
//            public void onResponse(Call<ApiResponseGetJob> call, Response<ApiResponseGetJob> response) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                if (response.body().getStatus() == 1) {
//                    ((SignUpJobSeekerActivity) getActivity()).changeFragment(new SignupFragmentDescribeYourself(), "fragmentdescribe", R.id.frame_container);
//                } else {
//                    Utils.showSnackBar(rootProfilePic, response.body().getMessage(), getActivity());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseGetJob> call, Throwable t) {
//                if (progressView.isShowing()) {
//                    progressView.hideLoader();
//                }
//                t.printStackTrace();
//            }
//        });

        try{
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("user_id", new StringBody(Constants.jobUserPOJO.getUserId()));
            reqEntity.addPart("userfile", new FileBody(new File(SignUpJobSeekerActivity.path)));

            new WebUploadService(reqEntity, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String apicall, String response) {
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        if(jsonObject.optInt("status")==1){
//                            Gson gson=new Gson();
//                            JobUserPOJO jobUserPOJO =gson.fromJson(jsonObject.optJSONObject("result").toString(),JobUserPOJO.class);
//                            Constants.jobUserPOJO=jobUserPOJO;
//                            Pref.SetStringPref(getActivity().getApplicationContext(), StringUtils.JOB_USER_POJO,jsonObject.optJSONObject("result").toString());
                            activityManager.startFragment(R.id.frame_container,new SignupFragmentDescribeYourself());
                        }
                        ToastClass.showShortToast(getActivity().getApplicationContext(),jsonObject.optString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, "UPLOAD IMAGE", true).execute(WebServicesUrls.USER_REGISTER_5);
        }catch (Exception e){
            e.printStackTrace();
        }

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
                file = new File(images.get(0).getPath());
                SignUpJobSeekerActivity.path = images.get(0).getPath();
                Glide.with(getActivity()).load(images.get(0).getPath()).into(ivSetProfileJobseeker);

            } else {
                Toast.makeText(getActivity(), "Image not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SignUpJobSeekerActivity) getActivity()).setUpToolbar("What do you look like?", "6/8", true, true);
    }
}
