package com.git.hubreeh.adapter.jobseeker;

import android.app.Activity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.git.hubreeh.R;
import com.git.hubreeh.model.jobseeker.HearUsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/17/2018.
 */

public class HearUsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<HearUsModel> hearUsModels = new ArrayList<>();


    public HearUsAdapter(Activity context, List<HearUsModel> hearUsModels) {
        this.context = context;
        this.hearUsModels = hearUsModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hearus_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;

        holder1.tvFromWhereHearUs.setText(hearUsModels.get(position).getReasonHearUs());
        holder1.cbHearUs.setChecked(hearUsModels.get(position).isHearIsChecked());


        holder1.cbHearUs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hearUsModels.get(position).setHearIsChecked(isChecked);
                    for (int i = 0; i < hearUsModels.size(); i++) {
                        if (position != i) {
                            hearUsModels.get(i).setHearIsChecked(false);
                        }
                    }
                } else {
                    for (int i = 0; i < hearUsModels.size(); i++) {
                        hearUsModels.get(i).setHearIsChecked(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hearUsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_from_where_hear_us)
        AppCompatTextView tvFromWhereHearUs;
        @BindView(R.id.cb_hear_us)
        AppCompatCheckBox cbHearUs;

        public ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
            ButterKnife.bind(this, itemView);


        }
    }
}
