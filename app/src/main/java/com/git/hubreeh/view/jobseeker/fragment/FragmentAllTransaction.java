package com.git.hubreeh.view.jobseeker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.adapter.employer.AllTransactionAdapter;
import com.git.hubreeh.adapter.employer.PaymentMethodAdapter;
import com.git.hubreeh.model.employer.AllTransactionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/26/2018.
 */

public class FragmentAllTransaction extends Fragment {
    RecyclerView recyclerAllTransaction;
    AllTransactionAdapter mAdapter;
    List<AllTransactionModel> allTransactionModels = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_transcation, container, false);


        initialize();

        recyclerAllTransaction.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AllTransactionAdapter(getActivity(), allTransactionModels);
        recyclerAllTransaction.setAdapter(mAdapter);


        prepareData();

        return view;
    }

    private void initialize() {
        recyclerAllTransaction=view.findViewById(R.id.recycler_all_transactions);
    }

    private void prepareData() {
        allTransactionModels.clear();

        AllTransactionModel model = new AllTransactionModel("09-Dec-2018 10:40AM", "Some of the transaction Mention", "AED", "+200000");
        allTransactionModels.add(model);
        model = new AllTransactionModel("09-Dec-2018 10:40AM", "Some of the transaction Mention", "AED", "-5000");
        allTransactionModels.add(model);
        model = new AllTransactionModel("09-Dec-2018 10:40AM", "Some of the transaction Mention", "AED", "+200000");
        allTransactionModels.add(model);
        model = new AllTransactionModel("09-Dec-2018 10:40AM", "Some of the transaction Mention", "AED", "+200000");
        allTransactionModels.add(model);
        model = new AllTransactionModel("09-Dec-2018 10:40AM", "Some of the transaction Mention", "AED", "-8000");
        allTransactionModels.add(model);

        mAdapter.notifyDataSetChanged();
    }
}
