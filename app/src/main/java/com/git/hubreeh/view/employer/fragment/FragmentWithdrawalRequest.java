package com.git.hubreeh.view.employer.fragment;

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
import com.git.hubreeh.adapter.employer.PendingFundAdapter;
import com.git.hubreeh.adapter.employer.WithdrawalRequestAdapter;
import com.git.hubreeh.model.employer.PendingFundModel;
import com.git.hubreeh.model.employer.WithdrawalRequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/26/2018.
 */

public class FragmentWithdrawalRequest extends Fragment {
    View view;

    RecyclerView recyclerWithdrawalRequest;
    WithdrawalRequestAdapter mAdapter;
    List<WithdrawalRequestModel> withdrawalRequestModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_withdrawal_request_layout,container,false);

        initialize();

        recyclerWithdrawalRequest.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WithdrawalRequestAdapter(getActivity(), withdrawalRequestModels);
        recyclerWithdrawalRequest.setAdapter(mAdapter);

        prepareData();
        return view;
    }

    private void prepareData() {
        WithdrawalRequestModel model=new WithdrawalRequestModel("Some Method","Some description baout the method","No fee","AED","\u20B9 20000");
        withdrawalRequestModels.add(model);
        model=new WithdrawalRequestModel("Some Method","Some description baout the method","No fee","AED","\u20B9 20000");
        withdrawalRequestModels.add(model);
        model=new WithdrawalRequestModel("Some Method","Some description baout the method","No fee","AED","\u20B9 20000");
        withdrawalRequestModels.add(model);
        model=new WithdrawalRequestModel("Some Method","Some description baout the method","No fee","AED","\u20B9 20000");
        withdrawalRequestModels.add(model);
        model=new WithdrawalRequestModel("Some Method","Some description baout the method","No fee","AED","\u20B9 20000");
        withdrawalRequestModels.add(model);
        model=new WithdrawalRequestModel("Some Method","Some description baout the method","No fee","AED","\u20B9 20000");
        withdrawalRequestModels.add(model);
    }

    private void initialize() {
        recyclerWithdrawalRequest=view.findViewById(R.id.recycler_withdrawal_request_status);
    }
}
