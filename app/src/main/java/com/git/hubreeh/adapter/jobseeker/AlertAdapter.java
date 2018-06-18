package com.git.hubreeh.adapter.jobseeker;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.jobseeker.AlertBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 12/19/2017.
 */

public class AlertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<AlertBean.ResultBean> alertBeans = new ArrayList<>();


    public AlertAdapter(Context context, List<AlertBean.ResultBean> alertBeans) {
        this.context = context;
        this.alertBeans = alertBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_profile_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.tvProfileName.setText(alertBeans.get(position).getTitle());
        holder1.ivBody.setText(alertBeans.get(position).getBody());
        holder1.tvTime.setText(alertBeans.get(position).getCreated());
        holder1.tvIcon.setText(alertBeans.get(position).getTitle().substring(0,1));
    }

    @Override
    public int getItemCount() {
        return alertBeans.size();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView1)
        RoundedImageView imageView1;
        @BindView(R.id.tv_profile_name)
        AppCompatTextView tvProfileName;
        @BindView(R.id.ivBody)
        AppCompatTextView ivBody;
        @BindView(R.id.tvTime)
        AppCompatTextView tvTime;
        @BindView(R.id.tvIcon)
        AppCompatTextView tvIcon;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
