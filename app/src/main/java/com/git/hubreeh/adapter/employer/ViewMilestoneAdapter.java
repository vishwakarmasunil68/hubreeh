package com.git.hubreeh.adapter.employer;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.employer.ViewMilestoneModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/30/2018.
 */

public class ViewMilestoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ViewMilestoneModel> viewMilestoneModels =new ArrayList<>();

    public ViewMilestoneAdapter(Context context, List<ViewMilestoneModel> viewMilestoneModels) {
        this.context = context;
        this.viewMilestoneModels = viewMilestoneModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_milestone,parent,false);

        return new ViewMilestoneAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder holder1=(ViewHolder) holder;

        holder1.createdDate.setText(viewMilestoneModels.get(position).getCreatedDate());
        holder1.employer.setText(viewMilestoneModels.get(position).getEmployerName());
        holder1.projectName.setText(viewMilestoneModels.get(position).getProjectName());
        holder1.amount.setText(viewMilestoneModels.get(position).getAmount());
        holder1.description.setText(viewMilestoneModels.get(position).getDescription());
        holder1.status.setText(viewMilestoneModels.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return viewMilestoneModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView createdDate,employer,projectName,amount,description,status;
        public ViewHolder(View itemView) {
            super(itemView);
            createdDate=itemView.findViewById(R.id.tv_view_milestone_created_date);
            employer=itemView.findViewById(R.id.tv_view_milestone_employer_name);
            projectName=itemView.findViewById(R.id.tv_view_milestone_project_name);
            amount=itemView.findViewById(R.id.tv_view_milestone_amount);
            description=itemView.findViewById(R.id.tv_view_milestone_description);
            status=itemView.findViewById(R.id.tv_view_milestone_status);
        }
    }

}
