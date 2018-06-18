package com.git.hubreeh.view.jobseeker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.adapter.jobseeker.BiddingAdapter;
import com.git.hubreeh.helper.ExpandableLayout;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.model.jobseeker.JobDetails;
import com.git.hubreeh.utility.Utils;
import com.git.hubreeh.view.jobseeker.fragment.PlaceBidFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_view_profile_job_creater)
    AppCompatTextView tvViewProfileJobCreater;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.tvDate)
    AppCompatTextView tvDate;
    @BindView(R.id.tvDec)
    AppCompatTextView tvDec;
    @BindView(R.id.tvAmount)
    AppCompatTextView tvAmount;
    @BindView(R.id.tvAvgBid)
    AppCompatTextView tvAvgBid;
    @BindView(R.id.tvBidCount)
    AppCompatTextView tvBidCount;
    @BindView(R.id.tvTotalBids)
    AppCompatTextView tvTotalBids;
    @BindView(R.id.bidsRecycler)
    RecyclerView bidsRecycler;
    @BindView(R.id.tvJobID)
    AppCompatTextView tvJobID;
    @BindView(R.id.tv_company_name)
    AppCompatTextView tvCompanyName;
    @BindView(R.id.tv_company_rating)
    AppCompatTextView tvCompanyRating;
    @BindView(R.id.rate)
    RatingBar rate;
    @BindView(R.id.btn_place_bid)
    AppCompatTextView btnPlaceBid;
    @BindView(R.id.frame_place_bid)
    FrameLayout framePlaceBid;

    public static SlidingUpPanelLayout slidingLayout;
    ProgressView progressView;
    @BindView(R.id.root)
    LinearLayout root;
    List<JobDetails.ResultBean.BidingsBean> bidingsBeans;
    BiddingAdapter biddingAdapter;

    String compnyName, id, jobname, amount;
    @BindView(R.id.tvFull)
    TextView tvFull;
    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;
    @BindView(R.id.readMore)
    AppCompatTextView readMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        ButterKnife.bind(this);
        bidingsBeans = new ArrayList<>();
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        progressView = new ProgressView(this);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        biddingAdapter = new BiddingAdapter(this, bidingsBeans);
        bidsRecycler.setLayoutManager(new LinearLayoutManager(this));
        bidsRecycler.setAdapter(biddingAdapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Project Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        readMore.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().hasExtra("from")) {
            btnPlaceBid.setText("Message");
        }
        btnPlaceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().hasExtra("from")) {
                    startActivity(new Intent(ProjectDetailsActivity.this, ChatActivity.class).putExtra("user_id", getIntent().getStringExtra("user_id")).putExtra("business_id", getIntent().getStringExtra("business_id")).putExtra("name", getIntent().getStringExtra("name")));
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                } else {
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    PlaceBidFragment placeBidFragment = new PlaceBidFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", jobname);
                    bundle.putString("amount", amount);
                    bundle.putString("jobID", getIntent().getStringExtra("id"));
                    placeBidFragment.setArguments(bundle);
                    changeFragment(placeBidFragment, "placeBidFragment");
                }


            }
        });

        tvViewProfileJobCreater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProjectDetailsActivity.this, ViewProfileEmployer.class).putExtra("name", compnyName).putExtra("id", id));
            }
        });
        getJobDetails();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_place_bid);
        if (currentFragment instanceof PlaceBidFragment) {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            finish();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void changeFragment(Fragment targetFragment, String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_place_bid, targetFragment)
                .addToBackStack(name)
                .commit();
    }

    private void getJobDetails() {
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);

        Call<JobDetails> call = apiInterface.get_job_details(getIntent().getStringExtra("id"));

        call.enqueue(new Callback<JobDetails>() {
            @Override
            public void onResponse(Call<JobDetails> call, Response<JobDetails> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {

                    jobname = response.body().getResult().getJobName();
                    amount = response.body().getResult().getJobPrice();

                    tvTitle.setText(response.body().getResult().getJobName());

                    if (response.body().getResult().getAboutJob().length() > 100) {
                        tvDec.setText(response.body().getResult().getAboutJob().substring(0, 100));
                        readMore.setVisibility(View.VISIBLE);
                        tvFull.setText(response.body().getResult().getAboutJob().substring(100, response.body().getResult().getAboutJob().length()));

                    } else {
                        readMore.setVisibility(View.VISIBLE);
                        tvDec.setText(response.body().getResult().getAboutJob());
                    }
                    tvAvgBid.setText(response.body().getResult().getJobPrice() + " AED");
                    tvAmount.setText(response.body().getResult().getJobPrice() + " AED");
                    tvBidCount.setText(response.body().getResult().getBid_count());
                    tvJobID.setText(response.body().getResult().getJob_id());
                    bidingsBeans.clear();
                    bidingsBeans.addAll(response.body().getResult().getBidings());
                    biddingAdapter.notifyDataSetChanged();
                    tvTotalBids.setText("Total bid (" + response.body().getResult().getBid_count() + ")");
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = df.format(c);
                    int diff = Integer.parseInt(Utils.getCountOfDays(formattedDate, response.body().getResult().getUrgentDate()));
                    if (diff < 0) {
                        tvDate.setText("Close");
                    } else {
                        tvDate.setText("Open /" + diff + " Days left");
                    }

                    id = response.body().getResult().getBusiness_id();
                    compnyName = response.body().getResult().getBusiness_name();

                } else {
                    Utils.showSnackBar(root, response.body().getMessage(), ProjectDetailsActivity.this);
                }


            }

            @Override
            public void onFailure(Call<JobDetails> call, Throwable t) {
                t.printStackTrace();
                progressView.hideLoader();
            }
        });
    }

    @OnClick(R.id.readMore)
    public void onViewClicked() {
        if (expandableLayout.isExpanded()) {
            expandableLayout.collapse(true);
        } else {
            expandableLayout.expand(true);
        }
    }
}
