package com.git.hubreeh.adapter.employer;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.employer.WithdrawalPendingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/30/2018.
 */

public class WithdrawalPendingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<WithdrawalPendingModel> withdrawalPendingModels = new ArrayList<>();

    public WithdrawalPendingAdapter(Context context, List<WithdrawalPendingModel> withdrawalPendingModels) {
        this.context = context;
        this.withdrawalPendingModels = withdrawalPendingModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_withdrawal_pending_layout, parent, false);

        return new WithdrawalPendingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        holder1.requestedDate.setText(withdrawalPendingModels.get(position).getRequestedDate());
        holder1.amount.setText(withdrawalPendingModels.get(position).getPendingAmount());
        holder1.details.setText(withdrawalPendingModels.get(position).getDetails());
        holder1.status.setText(withdrawalPendingModels.get(position).getStatus());
        holder1.processingDate.setText(withdrawalPendingModels.get(position).getRequestedDate());

    }

    @Override
    public int getItemCount() {
        return withdrawalPendingModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView requestedDate, amount, details, status, processingDate;

        public ViewHolder(View itemView) {
            super(itemView);
            requestedDate = itemView.findViewById(R.id.tv_withdrawal_pending_requested_date);
            amount = itemView.findViewById(R.id.tv_withdrawal_pending_amount);
            details = itemView.findViewById(R.id.tv_withdrawal_pending_details);
            status = itemView.findViewById(R.id.tv_withdrawal_pending_status);
            processingDate = itemView.findViewById(R.id.tv_withdrawal_pending_processing_date);

        }
    }

}
