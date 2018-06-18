package com.git.hubreeh.view.jobseeker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.CategoryAdapter;
import com.git.hubreeh.adapter.jobseeker.RecommendedAdapter;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.jobseeker.SearchViewBean;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.jobseeker.activity.SearchResultActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/19/2017.
 */

public class HomeSearch extends Fragment {
    View view;
    @BindView(R.id.recommended)
    RecyclerView recommended;
    @BindView(R.id.categories)
    RecyclerView categories;
    Unbinder unbinder;
    ProgressView progressView;
    List<SearchViewBean.CategoriesBean> categoriesBeans;
    List<SearchViewBean.RecommendedBean> recommendedBeans;
    CategoryAdapter mCategoryAdapter;
    RecommendedAdapter mRecommendedAdapter;
    @BindView(R.id.root)
    NestedScrollView root;
    @BindView(R.id.etSearchView)
    AppCompatEditText etSearchView;
    @BindView(R.id.showMore)
    AppCompatTextView showMore;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        progressView = new ProgressView(getActivity());
        categoriesBeans = new ArrayList<>();
        recommendedBeans = new ArrayList<>();

        mCategoryAdapter = new CategoryAdapter(getActivity(), categoriesBeans);
        mRecommendedAdapter = new RecommendedAdapter(getActivity(), recommendedBeans);

        categories.setLayoutManager(new LinearLayoutManager(getActivity()));
        categories.setAdapter(mCategoryAdapter);

        recommended.setLayoutManager(new LinearLayoutManager(getActivity()));
        recommended.setAdapter(mRecommendedAdapter);


        etSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!etSearchView.getText().toString().equalsIgnoreCase("")) {
                        startActivity(new Intent(getActivity(), SearchResultActivity.class).putExtra("query", etSearchView.getText().toString()));
                    }
                    return true;
                }
                return false;
            }
        });

//        try {
//            getData();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchResultActivity.class).putExtra("query", "recommended"));
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void getData() throws JSONException {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        JSONObject object1 = new JSONObject(MyApplication.readStringPref(PrefData.PREF_JOB_DATA));
        JSONObject object = object1.getJSONObject("result");
        Call<SearchViewBean> call = apiInterface.search_bid_view_data(object.getString("category_id"), MyApplication.readStringPref(PrefData.PREF_JOBID));
        call.enqueue(new Callback<SearchViewBean>() {
            @Override
            public void onResponse(Call<SearchViewBean> call, Response<SearchViewBean> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    recommendedBeans.clear();
                    categoriesBeans.clear();
                    recommendedBeans.addAll(response.body().getRecommended());
                    categoriesBeans.addAll(response.body().getCategories());
                    mCategoryAdapter.notifyDataSetChanged();
                    mRecommendedAdapter.notifyDataSetChanged();
                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), getActivity());
                }
            }

            @Override
            public void onFailure(Call<SearchViewBean> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }

}