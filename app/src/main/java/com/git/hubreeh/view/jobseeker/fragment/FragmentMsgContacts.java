package com.git.hubreeh.view.jobseeker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.ContactsAdapter;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.jobseeker.MessageBeans;
import com.git.hubreeh.utility.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/19/2017.
 */

public class FragmentMsgContacts extends Fragment {
    View view;

    ContactsAdapter mAdapter;
    List<MessageBeans.ContactsBean> contactsBeans;
    ProgressView progressView;
    @BindView(R.id.recycler_contacts)
    RecyclerView recyclerContacts;
    @BindView(R.id.root)
    LinearLayout root;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_msg_contacts, container, false);
        unbinder = ButterKnife.bind(this, view);
        contactsBeans = new ArrayList<>();
        progressView = new ProgressView(getActivity());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerContacts.setHasFixedSize(true);
        recyclerContacts.setLayoutManager(mLayoutManager);
        mAdapter = new ContactsAdapter(getActivity(), contactsBeans);
        recyclerContacts.setAdapter(mAdapter);

        getChats();

        return view;
    }


    public void getChats() {
        try {
            progressView.showLoader();
            ApiInterface apiInterface;
            Map<String, String> map = new HashMap<>();
            if (MyApplication.readStringPref(PrefData.LOGIN_TYPE).equalsIgnoreCase("bus")) {
                apiInterface = ApiClient.getBusinessClient().create(ApiInterface.class);
                map.put("business_id", MyApplication.readStringPref(PrefData.PREFNUSINESSID));
            } else {
                apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
                map.put("user_id", MyApplication.readStringPref(PrefData.PREF_JOBID));
            }
            Call<MessageBeans> call = apiInterface.get_chat_rooms(map);
            call.enqueue(new Callback<MessageBeans>() {
                @Override
                public void onResponse(Call<MessageBeans> call, Response<MessageBeans> response) {
                    progressView.hideLoader();
                    if (response.body().getStatus() == 1) {
                        contactsBeans.clear();
                        contactsBeans.addAll(response.body().getContacts());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Utils.showSnackBar(root, response.body().getMessage(), getActivity());
                    }
                }

                @Override
                public void onFailure(Call<MessageBeans> call, Throwable t) {
                    t.printStackTrace();
                    progressView.hideLoader();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
