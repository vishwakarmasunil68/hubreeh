package com.git.hubreeh.adapter.jobseeker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.git.hubreeh.R;
import com.git.hubreeh.model.jobseeker.CategoryBean;

import java.util.List;

/**
 * Created by Hp on 12/16/2017.
 */

public class HospitalityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<CategoryBean.HospitalityBean> hospitalityModels;

    public HospitalityAdapter(Context context, List<CategoryBean.HospitalityBean> hospitalityModels) {
        this.context = context;
        this.hospitalityModels = hospitalityModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospitality_adapter_layout, parent, false);

        return new HospitalityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.jobRole.setText(hospitalityModels.get(position).getName());

        Glide.with(context).load(hospitalityModels.get(position).getImage()).into(holder1.jobIcons);


        if (hospitalityModels.get(position).isSelect()) {
            ImageViewCompat.setImageTintList(holder1.jobIcons, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorWhite)));
        } else {
            ImageViewCompat.setImageTintList(holder1.jobIcons, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorBlack)));
        }



        holder1.jobIcons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hospitalityModels.get(position).isSelect()) {
                    ImageViewCompat.setImageTintList(holder1.jobIcons, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorWhite)));
                    hospitalityModels.get(position).setSelect(true);
                } else {
                    ImageViewCompat.setImageTintList(holder1.jobIcons, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorBlack)));
                    hospitalityModels.get(position).setSelect(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalityModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView jobRole;
        ImageView jobIcons;

        public ViewHolder(View itemView) {
            super(itemView);
            jobRole = itemView.findViewById(R.id.tv_job_roles);
            jobIcons = itemView.findViewById(R.id.iv_job_icons);
        }
    }
}
