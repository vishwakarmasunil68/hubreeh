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
import com.git.hubreeh.model.jobseeker.DaysModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 2/17/2018.
 */

public class DaysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<DaysModel> daysModels = new ArrayList<>();


    public DaysAdapter(Activity context, List<DaysModel> daysModels) {
        this.context = context;
        this.daysModels = daysModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_days_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DaysAdapter.ViewHolder holder1 = (DaysAdapter.ViewHolder) holder;

        holder1.tvDays.setText(daysModels.get(position).getDays());
        holder1.cbDayAm.setChecked(daysModels.get(position).isAmChecked());
        holder1.cbDayPm.setChecked(daysModels.get(position).isPmChecked());

        holder1.cbDayAm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                daysModels.get(position).setAmChecked(isChecked);
            }
        });

        holder1.cbDayPm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                daysModels.get(position).setPmChecked(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return daysModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_days)
        AppCompatTextView tvDays;
        @BindView(R.id.cb_day_am)
        AppCompatCheckBox cbDayAm;
        @BindView(R.id.cb_day_pm)
        AppCompatCheckBox cbDayPm;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
