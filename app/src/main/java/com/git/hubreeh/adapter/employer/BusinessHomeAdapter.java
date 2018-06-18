package com.git.hubreeh.adapter.employer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.employer.BusinessHomeModel;
import com.git.hubreeh.view.employer.activity.JobDescriptionPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 1/24/2018.
 */

public class BusinessHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<BusinessHomeModel.ResultBean> businessHomeModels = new ArrayList<>();

//    List<BusinessHomeModel.ResultBean> businessHomeModels = new ArrayList<>();

    public BusinessHomeAdapter(Context context, List<BusinessHomeModel.ResultBean> businessHomeModels) {
        this.context = context;
        this.businessHomeModels = businessHomeModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_business_home, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.tvBusinessHomeHeading.setText(businessHomeModels.get(position).getJobName());
        holder1.tvBusinessHomePostedon.setText("Posted on " + businessHomeModels.get(position).getUrgentDate());
        holder1.tvBusinessHomeProposals.setText("Number of Proposal 0");

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, JobDescriptionPage.class);
                intent.putExtra("title", businessHomeModels.get(position).getJobName());
                intent.putExtra("dec", businessHomeModels.get(position).getAboutJob());
                intent.putExtra("img", businessHomeModels.get(position).getImage());
                intent.putExtra("budget", businessHomeModels.get(position).getJobPrice());
                intent.putExtra("postdate", businessHomeModels.get(position).getUrgentDate());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return businessHomeModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_business_home_heading)
        AppCompatTextView tvBusinessHomeHeading;
        @BindView(R.id.tv_business_home_proposals)
        AppCompatTextView tvBusinessHomeProposals;
        @BindView(R.id.tv_business_home_postedon)
        AppCompatTextView tvBusinessHomePostedon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
