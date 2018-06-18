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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/30/2018.
 */

public class AllTransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<AllTransactionModel> allTransactionModels=new ArrayList<>();

    public AllTransactionAdapter(Context context, List<AllTransactionModel> allTransactionModels) {
        this.context = context;
        this.allTransactionModels = allTransactionModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_all_transactions,parent,false);

        return new AllTransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1=(ViewHolder) holder;
        char color;
        holder1.amount.setText(allTransactionModels.get(position).getAmount());
        holder1.date.setText(allTransactionModels.get(position).getDate());
        holder1.comments.setText(allTransactionModels.get(position).getComments());
        holder1.currency.setText(allTransactionModels.get(position).getCurrency());

        for (int i=0;i<allTransactionModels.size();i++){
            color=allTransactionModels.get(i).getAmount().charAt(0);
            if (color == '+'){
                holder1.amount.setTextColor(context.getResources().getColor(R.color.colorGreen));
            }else {
                holder1.amount.setTextColor(context.getResources().getColor(R.color.colorRed));
            }
        }
    }

    @Override
    public int getItemCount() {
        return allTransactionModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView amount,date,comments,currency;
        AppCompatButton btnSendInvoice;
        public ViewHolder(View itemView) {
            super(itemView);
            amount=itemView.findViewById(R.id.tv_all_transaction_amount);
            date=itemView.findViewById(R.id.tv_all_transaction_date);
            comments=itemView.findViewById(R.id.tv_all_transaction_comments);
            currency=itemView.findViewById(R.id.tv_all_transaction_currency);
            btnSendInvoice=itemView.findViewById(R.id.btn_all_transaction_send_invoice);
        }
    }
}
