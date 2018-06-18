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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/12/2018.
 */

public class WarehousingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<CategoryBean.WarehousingBean> warehousingBeans;


    public WarehousingAdapter(Context context, List<CategoryBean.WarehousingBean> warehousingBeans) {
        this.context = context;
        this.warehousingBeans = warehousingBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehousing_adapter_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.tvWarehousingTitle.setText(warehousingBeans.get(position).getName());
        Glide.with(context).load(warehousingBeans.get(position).getImage()).into(holder1.ivWarehousingIcon);


        if (warehousingBeans.get(position).isSeleted()) {
            ImageViewCompat.setImageTintList(holder1.ivWarehousingIcon, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorWhite)));
        } else {
            ImageViewCompat.setImageTintList(holder1.ivWarehousingIcon, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorBlack)));
        }


        holder1.ivWarehousingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!warehousingBeans.get(position).isSeleted()) {
                    ImageViewCompat.setImageTintList(holder1.ivWarehousingIcon, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorWhite)));
                    warehousingBeans.get(position).setSeleted(true);
                } else {
                    ImageViewCompat.setImageTintList(holder1.ivWarehousingIcon, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorBlack)));
                    warehousingBeans.get(position).setSeleted(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return warehousingBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_warehousing_icon)
        ImageView ivWarehousingIcon;
        @BindView(R.id.tv_warehousing_title)
        AppCompatTextView tvWarehousingTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
