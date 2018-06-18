package com.git.hubreeh.adapter.jobseeker;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.git.hubreeh.R;
import com.git.hubreeh.model.jobseeker.JobDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 12/19/2017.
 */

public class BiddingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<JobDetails.ResultBean.BidingsBean> bookedModels = new ArrayList<>();

    public BiddingAdapter(Activity context, List<JobDetails.ResultBean.BidingsBean> bookedModels) {
        this.context = context;
        this.bookedModels = bookedModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bidding_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        holder1.tvIcon.setText(bookedModels.get(position).getUser_name().substring(0, 1));
        holder1.tvTitle.setText(bookedModels.get(position).getUser_name());
        holder1.tvReviewCount.setText("(" + bookedModels.get(position).getReview_count() + " Reviews)");
        holder1.tvIcon.setText(bookedModels.get(position).getUser_name().substring(0, 1));
        if (bookedModels.get(position).getReview_count2() != null) {
            holder1.tvRateCount.setText(bookedModels.get(position).getReview_count2().substring(0, 3));
            holder1.rbRating.setRating(Float.parseFloat(bookedModels.get(position).getReview_count2()));
        }
    }

    @Override
    public int getItemCount() {
        return bookedModels.size();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvIcon)
        AppCompatTextView tvIcon;
        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;
        @BindView(R.id.tvRateCount)
        AppCompatTextView tvRateCount;
        @BindView(R.id.rbRating)
        RatingBar rbRating;
        @BindView(R.id.tvReviewCount)
        AppCompatTextView tvReviewCount;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
