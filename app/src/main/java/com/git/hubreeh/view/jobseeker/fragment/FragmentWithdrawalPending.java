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
import com.git.hubreeh.adapter.employer.WithdrawalPendingAdapter;
import com.git.hubreeh.model.employer.WithdrawalPendingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/30/2018.
 */

public class FragmentWithdrawalPending extends Fragment {

    View view;

    RecyclerView recyclerWithdrawalPending;
    WithdrawalPendingAdapter mAdapter;
    List<WithdrawalPendingModel> withdrawalRequestModels = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_withdrawal_pending, container, false);
        initialize();

        recyclerWithdrawalPending.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WithdrawalPendingAdapter(getActivity(), withdrawalRequestModels);
        recyclerWithdrawalPending.setAdapter(mAdapter);

        prepareData();

        return view;
    }

    private void prepareData() {
        withdrawalRequestModels.clear();

        WithdrawalPendingModel model = new WithdrawalPendingModel("22-Nov-2018, 20:11PM", "\u20B9 20000", "Reason for pending amount", "In Process", "1-Dec-2019");
        withdrawalRequestModels.add(model);
        model = new WithdrawalPendingModel("22-Nov-2018, 20:11PM", "\u20B9 20000", "Reason for pending amount", "In Process", "1-Dec-2019");
        withdrawalRequestModels.add(model);
        model = new WithdrawalPendingModel("22-Nov-2018, 20:11PM", "\u20B9 20000", "Reason for pending amount", "In Process", "1-Dec-2019");
        withdrawalRequestModels.add(model);
        model = new WithdrawalPendingModel("22-Nov-2018, 20:11PM", "\u20B9 20000", "Reason for pending amount", "In Process", "1-Dec-2019");
        withdrawalRequestModels.add(model);
        model = new WithdrawalPendingModel("22-Nov-2018, 20:11PM", "\u20B9 20000", "Reason for pending amount", "In Process", "1-Dec-2019");
        withdrawalRequestModels.add(model);
        model = new WithdrawalPendingModel("22-Nov-2018, 20:11PM", "\u20B9 20000", "Reason for pending amount", "In Process", "1-Dec-2019");
        withdrawalRequestModels.add(model);
        model = new WithdrawalPendingModel("22-Nov-2018, 20:11PM", "\u20B9 20000", "Reason for pending amount", "In Process", "1-Dec-2019");
        withdrawalRequestModels.add(model);

        mAdapter.notifyDataSetChanged();


    }

    private void initialize() {
        recyclerWithdrawalPending = view.findViewById(R.id.recycler__withdrawal_pending);
    }
}
