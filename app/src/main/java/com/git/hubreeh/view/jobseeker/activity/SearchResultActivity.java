package com.git.hubreeh.view.jobseeker.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.SearchAdapter;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.jobseeker.SearchBean;
import com.git.hubreeh.utility.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.results)
    RecyclerView results;
    ProgressView progressView;
    SearchAdapter mSearchAdapter;
    List<SearchBean.ResultBean> resultBeans;
    @BindView(R.id.root)
    CoordinatorLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        progressView = new ProgressView(this);
        resultBeans = new ArrayList<>();
        mSearchAdapter = new SearchAdapter(this, resultBeans);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitle("");
        results.setLayoutManager(new LinearLayoutManager(this));
        results.setAdapter(mSearchAdapter);


        if (getIntent().hasExtra("query")) {
            getSearchResult();
        } else {
            getSearchResult(getIntent().getStringExtra("cat_id"));
        }

    }


    public void getSearchResult() {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);

        Call<SearchBean> call = apiInterface.search_bid(getIntent().getStringExtra("query"), "", MyApplication.readStringPref(PrefData.PREF_JOBID));
        call.enqueue(new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    resultBeans.clear();
                    resultBeans.addAll(response.body().getResult());
                    mSearchAdapter.notifyDataSetChanged();
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), SearchResultActivity.this);
                }
            }

            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }

    public void getSearchResult(String cat_id) {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);

        Call<SearchBean> call = apiInterface.search_bid("", cat_id,MyApplication.readStringPref(PrefData.PREF_JOBID));
        call.enqueue(new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    resultBeans.clear();
                    resultBeans.addAll(response.body().getResult());
                    mSearchAdapter.notifyDataSetChanged();
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), SearchResultActivity.this);
                }
            }

            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }
}
