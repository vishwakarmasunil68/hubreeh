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
import com.git.hubreeh.model.jobseeker.JobModel;
import com.git.hubreeh.view.jobseeker.activity.ProjectDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 12/19/2017.
 */

public class BookedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<JobModel.BookedBean> bookedModels = new ArrayList<>();

    public BookedAdapter(Activity context, List<JobModel.BookedBean> bookedModels) {
        this.context = context;
        this.bookedModels = bookedModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_booked, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

//        holder1.tvBookedTitle.setText(bookedModels.get(position).getJobName());
//        holder1.tvInfo.setText("Avg bid: " + bookedModels.get(position).getJobPrice() + " AED" +
//                " - " + bookedModels.get(position).getBid_count() + "Bids" + " -" +
//                bookedModels.get(position).getUrgentDate());
        holder1.linearBookedRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProjectDetailsActivity.class).putExtra("id", bookedModels.get(position).getJob_id()));
                context.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

    }

    @Override
    public int getItemCount() {
//        return bookedModels.size();
        return 12;
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_booked_title)
        AppCompatTextView tvBookedTitle;
        @BindView(R.id.tvInfo)
        AppCompatTextView tvInfo;
        @BindView(R.id.linear_booked_root)
        LinearLayout linearBookedRoot;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

}
