package com.git.hubreeh.adapter.jobseeker;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.jobseeker.MembershipPointsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/6/2018.
 */

public class MembershipPointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MembershipPointsModel> membershipPointsModels=new ArrayList<>();


    public MembershipPointsAdapter(Context context, List<MembershipPointsModel> membershipPointsModels) {
        this.context = context;
        this.membershipPointsModels = membershipPointsModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_membership_points_layout,parent,false);

        return new MembershipPointsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder holder1=(ViewHolder) holder;
        holder1.plans.setText(membershipPointsModels.get(position).getPlans());
    }

    @Override
    public int getItemCount() {
        return membershipPointsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView plans;
        public ViewHolder(View itemView) {
            super(itemView);
            plans=itemView.findViewById(R.id.tv_membership_plans);
        }
    }

}
