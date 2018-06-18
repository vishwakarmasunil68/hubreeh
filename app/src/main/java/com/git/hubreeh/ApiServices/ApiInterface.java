package com.git.hubreeh.ApiServices;

import com.git.hubreeh.model.AddressDecoder;
import com.git.hubreeh.model.LoginBean;
import com.git.hubreeh.model.employer.BusinessHomeModel;
import com.git.hubreeh.model.jobseeker.AlertBean;
import com.git.hubreeh.model.jobseeker.CategoryBean;
import com.git.hubreeh.model.jobseeker.ChatBean;
import com.git.hubreeh.model.jobseeker.JobDetails;
import com.git.hubreeh.model.jobseeker.JobModel;
import com.git.hubreeh.model.jobseeker.MessageBeans;
import com.git.hubreeh.model.jobseeker.SearchBean;
import com.git.hubreeh.model.jobseeker.SearchViewBean;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("create_user")
    Call<ApiResponse> create_user(
            @Field("email") String email,
            @Field("mobileNumber") String mobile,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("authenticate_otp")
    Call<ApiResponse> authenticate_otp(
            @Field("business_id") String id,
            @Field("otp") String otp);

    @FormUrlEncoded
    @POST("resend_otp")
    Call<ApiResponse> resend_otp(
            @Field("mobileNumber") String mobile,
            @Field("business_id") String id);

    @FormUrlEncoded
    @POST("user_register")
    Call<ApiResponse> user_register(
            @Field("companyName") String companyName,
            @Field("brandName") String brandName,
            @Field("contactName") String contactName,
            @Field("address1") String address1,
            @Field("address2") String address2,
            @Field("landmark") String landmark,
            @Field("city") String city,
            @Field("pincode") String pincode,
            @Field("isHospitality") String isHospitality,
            @Field("capicity") String capicity,
            @Field("osType") String osType,
            @Field("fcmToken") String fcmToken,
            @Field("device_id") String device_id,
            @Field("business_id") String business_id
    );

    @FormUrlEncoded
    @POST("create_user")
    Call<ApiResponseJob> create_user_job(
            @Field("email") String email,
            @Field("mobileNumber") String mobile,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("authenticate_otp")
    Call<ApiResponseJob> authenticate_otp_job(
            @Field("user_id") String id,
            @Field("otp") String otp);

    @FormUrlEncoded
    @POST("resend_otp")
    Call<ApiResponseJob> resend_otp_job(
            @Field("user_id") String UID,
            @Field("mobileNumber") String mobileNumber);

    @FormUrlEncoded
    @POST("user_register1")
    Call<ApiResponseJob> user_register1(
            @Field("title") String title,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("dateOfBirth") String dateOfBirth,
            @Field("isUae") String isUae,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("user_register2")
    Call<ApiResponseJob> user_register2(
            @Field("name") String name,
            @Field("role") String role,
            @Field("start") String start,
            @Field("end") String end,
            @Field("description") String description,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("remove_company")
    Call<ApiResponseJob> remove_company(@Field("company_id") String company_id);

    @FormUrlEncoded
    @POST("get_previous_company")
    Call<ApiResponseGetJob> get_previous_company(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get_categories")
    Call<CategoryBean> get_categories(@Field("user_id") String user_id);


    @POST("get_categories")
    Call<CategoryBean> get_categories();

    @FormUrlEncoded
    @POST("get_previous_categories")
    Call<ApiResponseGetJob> get_selected_categoty_if_any(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("update_categories")
    Call<ApiResponseGetJob> update_categories(
            @Field("category_id") String category_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("user_register3")
    Call<ApiResponseGetJob> RateAndAvailability(
            @FieldMap() Map<String, String> map
    );

    @Multipart
    @POST("user_register4")
    Call<ApiResponseGetJob> Upload_profile_pic(
            @Part MultipartBody.Part userfile,
            @Part("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("user_register5")
    Call<ApiResponseGetJob> update_about_me(
            @Field("aboutMe") String aboutMe,
            @Field("hearUs") String hearUs,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("final_register")
    Call<ApiResponseFinal> final_step_jobseeker(
            @Field("InsuranceNumber") String InsuranceNumber,
            @Field("dateSituation") String dateSituation,
            @Field("studentLoan") String studentLoan,
            @Field("fcmToken") String fcmToken,
            @Field("osType") String osType,
            @Field("device_id") String device_id,
            @Field("user_id") String user_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );


    @FormUrlEncoded
    @POST("login")
    Call<LoginBean> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("role") String role,
            @Field("fcmToken") String fcmToken,
            @Field("osType") String osType,
            @Field("device_id") String device_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );


    @FormUrlEncoded
    @POST("update_profile_details")
    Call<ApiResponse> update_profile_details(
            @Field("contactName") String contactName,
            @Field("address1") String address1,
            @Field("address2") String address2,
            @Field("city") String city,
            @Field("pincode") String pincode,
            @Field("landmark") String landmark,
            @Field("business_id") String business_id
    );


    @FormUrlEncoded
    @POST("update_profile_details")
    Call<ApiResponse> update_profile_details(
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("aboutMe") String aboutMe,
            @Field("rate") String rate,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("get_jobs")
    Call<BusinessHomeModel> get_jobs(
            @Field("business_id") String business_id
    );

    @FormUrlEncoded
    @POST("get_all_notification")
    Call<AlertBean> get_all_notification(
            @Field("user_id") String user_id
    );

    @Multipart
    @POST("create_job")
    Call<ApiResponse> create_job(
            @Part MultipartBody.Part userfile,
            @Part("business_id") RequestBody business_id,
            @Part("category_id") RequestBody category_id,
            @Part("jobName") RequestBody jobName,
            @Part("aboutJob") RequestBody aboutJob,
            @Part("isUrgent") RequestBody isUrgent,
            @Part("urgentDate") RequestBody urgentDate,
            @Part("isJobShared") RequestBody isJobShared,
            @Part("jobAddress") RequestBody jobAddress,
            @Part("jobPrice") RequestBody jobPrice,
            @Part("paymentMethod") RequestBody paymentMethod,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("radius") RequestBody radius
    );


    @FormUrlEncoded
    @POST("get_wallet")
    Call<ApiResponseJob> get_wallet(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("get_jobs")
    Call<JobModel> get_jobs(
            @Field("category_id") String category_id,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("get_job_details")
    Call<JobDetails> get_job_details(
            @Field("job_id") String job_id
    );

    @FormUrlEncoded
    @POST("get_business_details")
    Call<JsonObject> get_business_details(
            @Field("business_id") String business_id
    );


    @FormUrlEncoded
    @POST("place_bid")
    Call<JsonObject> place_bid(
            @Field("user_id") String user_id,
            @Field("job_id") String job_id,
            @Field("bid_amount") String bid_amount,
            @Field("num_of_days") String num_of_days,
            @Field("text") String text
    );

    @FormUrlEncoded
    @POST("search_bid")
    Call<SearchBean> search_bid(
            @Field("text") String text,
            @Field("category_id") String category_id,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("search_bid_view_data")
    Call<SearchViewBean> search_bid_view_data(
            @Field("category_id") String category_id,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("get_chat_rooms")
    Call<MessageBeans> get_chat_rooms(
            @FieldMap() Map<String, String> map
    );


    @FormUrlEncoded
    @POST("get_chats")
    Call<ChatBean> get_chats(
            @Field("user_id") String user_id,
            @Field("business_id") String business_id
    );

    @GET
    Call<AddressDecoder> address_decoder(@Url String url);


    @Multipart
    @POST("send_message")
    Call<ApiResponse> send_message(
            @Part MultipartBody.Part userfile,
            @Part("user_id") RequestBody user_id,
            @Part("business_id") RequestBody business_id,
            @Part("message") RequestBody message,
            @Part("message_type") RequestBody message_type,
            @Part("created") RequestBody created
    );

    /*
    @FormUrlEncoded
    @POST("login")
    Call<ApiResponse> login(@Field("email") String email, @Field("password") String password);


    @GET
    Call<AddressDecoder> address_decoder(@Url String url);


    @Multipart
    @POST("user_register")
    Call<ApiResponse> user_register(
            @Part MultipartBody.Part attachPic,
            @Part MultipartBody.Part billFile,
            @PartMap Map<String, RequestBody> bodyMap
    );

    @POST("get_categories")
    Call<CategoryBean> get_categories();*/


    /* headers.put("firm_name", firmName);
                headers.put("contact_name", contactName);
                headers.put("business_address", businessAddress);
                headers.put("telephone", telephone);
                headers.put("state", state);
                headers.put("city", city);
                headers.put("pincode", pincode);
                headers.put("area", area);
                headers.put("cities_to_serve", citiesToServe);
                headers.put("business_type", businessTpye);
                headers.put("business_registration_no", BusinessRegistrationNo);
                headers.put("latest_bill", "attach bill");
                headers.put("minimum_order", Quantity);

               // headers.put("userfile2",);
                headers.put("annual_turnover", annualTurnover);
                headers.put("specialities", specialities);
                headers.put("certifications",certification);
                headers.put("categories", categories);
                headers.put("gstNumber", gstNumber);
                //headers.put("userfile1",)
                headers.put("fcm_token", MyApplication.readStringPref(PrefsData.PREF_TOKEN));
                headers.put("deviceType", "android");
                headers.put("id", MyApplication.readStringPref(PrefsData.PREF_USERID));
*/


    /*@FormUrlEncoded
    @POST("Login")
    Call<UserData> userLogin(@Field("email") String email, @Field("loginType") String loginType, @Field("password") String password, @Field("token") String token);


    @FormUrlEncoded
    @POST("Getposts")
    Call<PostResponse> getPosts(@Field("user_id") String userID, @Field("filter") String filters, @Field("type") String type);


    @FormUrlEncoded
    @POST("Register")
    Call<RegisterResponse> userRegistration(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("nickName") String nickname,
            @Field("aboutMe") String aboutMe,
            @Field("webLink") String webLink,
            @Field("userfile") String userfile,
            @Field("loginType") String loginType,
            @Field("child") String child,
            @Field("token") String token,
            @Field("ostype") String ostype
    );


    @Multipart
    @POST("Upload_image")
    Call<ApiResponse> uploadFile(@Part MultipartBody.Part file, @Part("userfile") RequestBody name);

    @Multipart
    @POST("Add_posts")
    Call<ApiResponse> addPhotoPost(
            @Part MultipartBody.Part file,
            @Part("userfile") RequestBody name,
            @Part("shortDescription") RequestBody shortDescription,
            @Part("ageGroup") RequestBody ageGroup,
            @Part("description") RequestBody description,
            @Part("hashTags") RequestBody hashTags,
            @Part("buyUrl") RequestBody buyUrl,
            @Part("selfRate") RequestBody selfRate,
            @Part("isGift") RequestBody isGift,
            @Part("postType") RequestBody postType,
            @Part("user_id") RequestBody user_id
    );

    @FormUrlEncoded
    @POST("Add_posts")
    Call<ApiResponse> addPhotoPost(
            @Field("video_url") String ostype,
            @Field("shortDescription") String email,
            @Field("ageGroup") String password,
            @Field("description") String name,
            @Field("hashTags") String nickname,
            @Field("buyUrl") String aboutMe,
            @Field("selfRate") String userfile,
            @Field("isGift") String loginType,
            @Field("postType") String child,
            @Field("user_id") String token

    );


    @FormUrlEncoded
    @POST("Add_child")
    Call<ApiResponse> addchild(
            @Field("child_name") String child_name,
            @Field("child_image") String child_image,
            @Field("child_birthday") String child_birthday,
            @Field("child_gender") String child_gender,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("get_user_data")
    Call<AlertBean> getUserData(@Field("user_id") String userID);


    @POST("notification")
    Call<Notification> notification();

    @FormUrlEncoded
    @POST("post_details")
    Call<PostData> getPostDetails(@Field("post_id") String postID, @Field("user_id") String userID);


    @FormUrlEncoded
    @POST("Delete_child")
    Call<ApiResponse> deleteChild(@Field("child_id") String child_id);


    @FormUrlEncoded
    @POST("search")
    Call<Search> search(@Field("text") String query);


    @FormUrlEncoded
    @POST("Follow")
    Call<Follow> follow(@Field("follower_id") String follower_id, @Field("following_id") String following_id);


    @FormUrlEncoded
    @POST("Post_like")
    Call<ApiResponse> postLike(@Field("user_id") String userID, @Field("post_id") String postID);


    @FormUrlEncoded
    @POST("Add_comments")
    Call<ApiResponse> addComment(@Field("user_id") String userID, @Field("post_id") String postID, @Field("comment") String comment, @Field("commentReview") String commentReview);


    @Multipart
    @POST("Update_profile")
    Call<UpdateProfile> updateProfile(
            @Part MultipartBody.Part file,
            @Part("userfile") RequestBody filename,
            @Part("nickName") RequestBody nickName,
            @Part("name") RequestBody name,
            @Part("webLink") RequestBody webLink,
            @Part("email") RequestBody email,
            @Part("aboutMe") RequestBody aboutMe,
            @Part("user_id") RequestBody user_id);


    @Multipart
    @POST("Update_profile")
    Call<UpdateProfile> updateProfile(
            @Part("userfile") RequestBody filename,
            @Part("nickName") RequestBody nickName,
            @Part("name") RequestBody name,
            @Part("webLink") RequestBody webLink,
            @Part("email") RequestBody email,
            @Part("aboutMe") RequestBody aboutMe,
            @Part("user_id") RequestBody user_id);

    @Multipart
    @POST("Update_child")
    Call<UpdateProfile> updateChild(
            @Part MultipartBody.Part file,
            @Part("userfile") RequestBody filename,
            @Part("child_name") RequestBody nickName,
            @Part("child_birthday") RequestBody name,
            @Part("child_gender") RequestBody webLink,
            @Part("child_id") RequestBody email);

    @Multipart
    @POST("Update_child")
    Call<UpdateProfile> updateChild(
            @Part("userfile") RequestBody filename,
            @Part("child_name") RequestBody nickName,
            @Part("child_birthday") RequestBody name,
            @Part("child_gender") RequestBody webLink,
            @Part("child_id") RequestBody email);


    @FormUrlEncoded
    @POST("reset_password")
    Call<ApiResponse> resetPassword(@Field("user_id") String userID, @Field("old_password") String old_password, @Field("new_password") String new_password);*/

}

