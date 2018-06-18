package com.git.hubreeh.adapter.jobseeker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.git.hubreeh.R;
import com.git.hubreeh.model.jobseeker.SearchViewBean;
import com.git.hubreeh.view.jobseeker.activity.ProjectDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 12/19/2017.
 */

public class RecommendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<SearchViewBean.RecommendedBean> bookedModels = new ArrayList<>();

    public RecommendedAdapter(Activity context, List<SearchViewBean.RecommendedBean> bookedModels) {
        this.context = context;
        this.bookedModels = bookedModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        holder1.tvTitle.setText(bookedModels.get(position).getJobName());
        holder1.tvAmount.setText("Avg bid: " + bookedModels.get(position).getJobPrice() + " AED" +
                " - " + bookedModels.get(position).getBid_count() + "Bids" + " -" +
                bookedModels.get(position).getUrgentDate());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProjectDetailsActivity.class).putExtra("id", bookedModels.get(position).getJob_id()));
                context.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookedModels.size() > 3) {
            return 3;
        } else {
            return bookedModels.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;
        @BindView(R.id.tvAmount)
        AppCompatTextView tvAmount;
        @BindView(R.id.linear_job_one)
        LinearLayout linearJobOne;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


}
