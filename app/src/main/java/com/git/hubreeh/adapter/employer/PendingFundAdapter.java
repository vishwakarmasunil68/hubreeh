package com.git.hubreeh.adapter.employer;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.employer.AllTransactionModel;
import com.git.hubreeh.model.employer.PendingFundModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/30/2018.
 */

public class PendingFundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<PendingFundModel> pendingFundModels=new ArrayList<>();

    public PendingFundAdapter(Context context, List<PendingFundModel> pendingFundModels) {
        this.context = context;
        this.pendingFundModels = pendingFundModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_pending_fund,parent,false);

        return new PendingFundAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1=(ViewHolder) holder;

        holder1.date.setText(pendingFundModels.get(position).getDate());
        holder1.transaction.setText(pendingFundModels.get(position).getTransaction());
        holder1.reason.setText(pendingFundModels.get(position).getReason());
        holder1.currency.setText(pendingFundModels.get(position).getCurrency());
        holder1.amount.setText(pendingFundModels.get(position).getAmount());
        holder1.expected_time.setText(pendingFundModels.get(position).getExpectedDate());

    }

    @Override
    public int getItemCount() {
        return pendingFundModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView date,transaction,reason,currency,amount,expected_time;

        public ViewHolder(View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.tv_pending_fund_date);
            transaction=itemView.findViewById(R.id.tv_pending_transaction_transaction);
            reason=itemView.findViewById(R.id.tv_pending_transaction_reason);
            currency=itemView.findViewById(R.id.tv_pending_transaction_currency);
            amount=itemView.findViewById(R.id.tv_pending_transaction_amount);
            expected_time=itemView.findViewById(R.id.tv_pending_transaction_expected_date);
        }
    }
}
