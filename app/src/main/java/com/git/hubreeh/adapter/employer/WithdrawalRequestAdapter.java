package com.git.hubreeh.adapter.employer;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.employer.WithdrawalRequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/30/2018.
 */

public class WithdrawalRequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<WithdrawalRequestModel> withdrawalRequestModels = new ArrayList<>();

    public WithdrawalRequestAdapter(Context context, List<WithdrawalRequestModel> withdrawalRequestModels) {
        this.context = context;
        this.withdrawalRequestModels = withdrawalRequestModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_withdrawal_request_layout,parent,false);

        return new WithdrawalRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 =(ViewHolder) holder;

        holder1.method.setText(withdrawalRequestModels.get(position).getMethod());
        holder1.description.setText(withdrawalRequestModels.get(position).getDescreption());
        holder1.fee.setText(withdrawalRequestModels.get(position).getFees());
        holder1.currency.setText(withdrawalRequestModels.get(position).getCurrency());
        holder1.total.setText(withdrawalRequestModels.get(position).getTotal());

    }

    @Override
    public int getItemCount() {
        return withdrawalRequestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView method,description,fee,currency,total;
        public ViewHolder(View itemView) {
            super(itemView);

            method=itemView.findViewById(R.id.tv_withdrawal_request_method);
            description=itemView.findViewById(R.id.tv_withdrawal_request_description);
            fee=itemView.findViewById(R.id.tv_withdrawal_request_fee);
            currency=itemView.findViewById(R.id.tv_withdrawal_request_currency);
            total=itemView.findViewById(R.id.tv_withdrawal_request_total);

        }
    }

}
