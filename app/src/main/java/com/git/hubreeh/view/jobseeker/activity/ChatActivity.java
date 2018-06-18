package com.git.hubreeh.view.jobseeker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponse;
import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.ChatAdapter;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.jobseeker.ChatBean;
import com.git.hubreeh.utility.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {


    ChatAdapter mAdapter;
    List<ChatBean.ResBean> chatModels = new ArrayList<>();
    ProgressView progressView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_chat_screen)
    RecyclerView recyclerChat;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.etMsg)
    AppCompatEditText etMsg;
    @BindView(R.id.ivSend)
    ImageView ivSend;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        progressView = new ProgressView(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setSubtitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);

        recyclerChat.setLayoutManager(linearLayoutManager);
        mAdapter = new ChatAdapter(ChatActivity.this, chatModels, getIntent().getStringExtra("name"));
        recyclerChat.setAdapter(mAdapter);
        getChats();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void getChats() {
        progressView.showLoader();
        ApiInterface apiInterface;

        if (MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("bus")) {
            apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
        } else {
            apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        }

        Call<ChatBean> call = apiInterface.get_chats(getIntent().getStringExtra("user_id"), getIntent().getStringExtra("business_id"));
        call.enqueue(new Callback<ChatBean>() {
            @Override
            public void onResponse(Call<ChatBean> call, Response<ChatBean> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {

                    chatModels.clear();
                    chatModels.addAll(response.body().getRes());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), ChatActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ChatBean> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }

    @OnClick(R.id.ivSend)
    public void onViewClicked() {
        if (!etMsg.getText().toString().equalsIgnoreCase("")) {
            DateFormat df = new SimpleDateFormat("dd MM yyyy h:mm a");
            final String date = df.format(Calendar.getInstance().getTime());
            ChatBean.ResBean resBean = new ChatBean.ResBean();
            resBean.setCreated(date);

            if (MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("bus")) {
                resBean.setLogin_type("business");
            } else {
                resBean.setLogin_type("user");
            }
            resBean.setMessage(etMsg.getText().toString());
            resBean.setUser_id(MyApplication.readStringPref(PrefData.PREF_JOBID));
            resBean.setMessage_type("text");
            chatModels.add(resBean);
            mAdapter.notifyDataSetChanged();
            linearLayoutManager.smoothScrollToPosition(recyclerChat, null, mAdapter.getItemCount());
            sendMessage(etMsg.getText().toString(), "text");
            etMsg.setText("");
        } else {
            Utils.showSnackBar(root, "Type your message.!", ChatActivity.this);
        }
    }


    private void sendMessage(final String context, final String message_Type) {
        DateFormat df = new SimpleDateFormat("dd MM yyyy h:mm a");
        final String date = df.format(Calendar.getInstance().getTime());
        MultipartBody.Part file = null;
        final RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), MyApplication.readStringPref(PrefData.PREF_JOBID));
        RequestBody business_id = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("business_id"));
        final RequestBody message = RequestBody.create(MediaType.parse("text/plain"), context);
        RequestBody message_type = RequestBody.create(MediaType.parse("text/plain"), message_Type);
        RequestBody created = RequestBody.create(MediaType.parse("text/plain"), date);
        ApiInterface apiInterface;
        if (MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("bus")) {
            apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
        } else {
            apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        }
        Call<ApiResponse> call = apiInterface.send_message(file, user_id, business_id, message, message_type, created);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getStatus() == 0) {
                    chatModels.remove(chatModels.size() - 1);
                    mAdapter.notifyDataSetChanged();
                    linearLayoutManager.smoothScrollToPosition(recyclerChat, null, mAdapter.getItemCount());
                    Utils.showSnackBar(root, response.body().getMessage(), ChatActivity.this);

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
