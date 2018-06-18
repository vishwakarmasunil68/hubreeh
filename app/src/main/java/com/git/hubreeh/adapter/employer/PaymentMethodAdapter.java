package com.git.hubreeh.adapter.employer;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.employer.PaymentMethodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/26/2018.
 */

public class PaymentMethodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<PaymentMethodModel> paymentMethodModels = new ArrayList<>();

    public PaymentMethodAdapter(Context context, List<PaymentMethodModel> paymentMethodModels) {
        this.context = context;
        this.paymentMethodModels = paymentMethodModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_payment_method_layout, parent, false);

        return new PaymentMethodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.status.setText(paymentMethodModels.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return paymentMethodModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView status;

        public ViewHolder(View itemView) {
            super(itemView);

            status=itemView.findViewById(R.id.tv_status);
        }
    }


}
