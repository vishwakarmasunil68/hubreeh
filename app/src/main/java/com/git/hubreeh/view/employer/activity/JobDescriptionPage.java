package com.git.hubreeh.view.employer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.git.hubreeh.R;
import com.git.hubreeh.helper.Config;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobDescriptionPage extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_received_proposals)
    AppCompatButton btnReceivedProposals;
    @BindView(R.id.tv_view_profile_jobseeker)
    AppCompatTextView tvViewProfileJobseeker;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.tvBudget)
    AppCompatTextView tvBudget;
    @BindView(R.id.tvDec)
    AppCompatTextView tvDec;
    @BindView(R.id.rivJobImage)
    RoundedImageView rivJobImage;
    @BindView(R.id.tvPostDate)
    AppCompatTextView tvPostDate;
    @BindView(R.id.tvSkills)
    AppCompatTextView tvSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description_page);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        tvTitle.setText(getIntent().getStringExtra("title"));
        tvDec.setText(getIntent().getStringExtra("dec"));
        tvPostDate.setText(getIntent().getStringExtra("postdate"));
        tvBudget.setText(getIntent().getStringExtra("budget") + " AED");

        Glide.with(this).load(Config.IMAGE + getIntent().getStringExtra("img")).into(rivJobImage);


        btnReceivedProposals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JobDescriptionPage.this, ViewProposalActivity.class));
            }
        });

        tvViewProfileJobseeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JobDescriptionPage.this, ViewProfileJobseeker.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
