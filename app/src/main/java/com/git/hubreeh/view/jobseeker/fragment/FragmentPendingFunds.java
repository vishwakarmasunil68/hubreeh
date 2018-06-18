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
import com.git.hubreeh.adapter.employer.PendingFundAdapter;
import com.git.hubreeh.model.employer.AllTransactionModel;
import com.git.hubreeh.model.employer.PendingFundModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/26/2018.
 */

public class FragmentPendingFunds extends Fragment {
    View view;

    RecyclerView recyclerPendingFund;
    PendingFundAdapter mAdapter;
    List<PendingFundModel> pendingFundModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_pending_funds,container,false);


        initialize();


        recyclerPendingFund.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PendingFundAdapter(getActivity(), pendingFundModels);
        recyclerPendingFund.setAdapter(mAdapter);


        prepareData();


        return view;
    }

    private void prepareData() {

        pendingFundModels.clear();

        PendingFundModel model=new PendingFundModel("09-Dec-2018 10:40AM","Some of the transaction Mention","Delay in timeline","AED","+20000","25-Dec-2018");
        pendingFundModels.add(model);
        model=new PendingFundModel("09-Dec-2018 10:40AM","Some of the transaction Mention","Delay in timeline","AED","+20000","25-Dec-2018");
        pendingFundModels.add(model);
        model=new PendingFundModel("09-Dec-2018 10:40AM","Some of the transaction Mention","Delay in timeline","AED","+20000","25-Dec-2018");
        pendingFundModels.add(model);
        model=new PendingFundModel("09-Dec-2018 10:40AM","Some of the transaction Mention","Delay in timeline","AED","+20000","25-Dec-2018");
        pendingFundModels.add(model);
        model=new PendingFundModel("09-Dec-2018 10:40AM","Some of the transaction Mention","Delay in timeline","AED","+20000","25-Dec-2018");
        pendingFundModels.add(model);
        model=new PendingFundModel("09-Dec-2018 10:40AM","Some of the transaction Mention","Delay in timeline","AED","+20000","25-Dec-2018");
        pendingFundModels.add(model);
        model=new PendingFundModel("09-Dec-2018 10:40AM","Some of the transaction Mention","Delay in timeline","AED","+20000","25-Dec-2018");
        pendingFundModels.add(model);

        mAdapter.notifyDataSetChanged();

    }

    private void initialize() {
        recyclerPendingFund=view.findViewById(R.id.recycler_pending_funds);
    }
}
