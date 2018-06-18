package com.git.hubreeh.view.employer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.git.hubreeh.R;
import com.git.hubreeh.adapter.employer.ViewProposalsAdapter;
import com.git.hubreeh.model.employer.ViewProposalsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewProposalActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_proposals)
    RecyclerView recyclerViewProposals;
    ViewProposalsAdapter mAdapter;
    List<ViewProposalsModel> viewProposalsModels=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_proposal);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Proposal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        recyclerViewProposals.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new ViewProposalsAdapter(ViewProposalActivity.this, viewProposalsModels);
        recyclerViewProposals.setAdapter(mAdapter);

        prepareData();


    }

    private void prepareData() {
        viewProposalsModels.clear();

        ViewProposalsModel model=new ViewProposalsModel("Charlie Chaplin");
        viewProposalsModels.add(model);
        model=new ViewProposalsModel("Sakar Subedi");
        viewProposalsModels.add(model);
        model=new ViewProposalsModel("John Doe");
        viewProposalsModels.add(model);
        model=new ViewProposalsModel("Lorem Ipsum");
        viewProposalsModels.add(model);
        model=new ViewProposalsModel("Charlie Chaplin");
        viewProposalsModels.add(model);
        model=new ViewProposalsModel("Charlie Chaplin");
        viewProposalsModels.add(model);
        model=new ViewProposalsModel("Charlie Chaplin");
        viewProposalsModels.add(model);
        model=new ViewProposalsModel("Charlie Chaplin");
        viewProposalsModels.add(model);
        model=new ViewProposalsModel("Charlie Chaplin");
        viewProposalsModels.add(model);


        mAdapter.notifyDataSetChanged();
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
