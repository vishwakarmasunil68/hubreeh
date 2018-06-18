package com.git.hubreeh.view.employer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.git.hubreeh.R;
import com.git.hubreeh.adapter.employer.BusinessHomeAdapter;
import com.git.hubreeh.adapter.employer.PaymentMethodAdapter;
import com.git.hubreeh.model.employer.PaymentMethodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/25/2018.
 */

public class FragmentBusinessPayment extends Fragment {
        View view;
        AppCompatSpinner spCurrency;
        RecyclerView recyclerPaymentMethod;
        PaymentMethodAdapter mAdapter;
        List<PaymentMethodModel> paymentMethodModels=new ArrayList<>();



    List<String> currencyList=new ArrayList<>();
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view=inflater.inflate(R.layout.fragment_business_payments,container,false);
            initialize();


            currencyList.clear();
            currencyList.add("AED");
            currencyList.add("RUPEES");
            currencyList.add("DOLLAR");
            currencyList.add("DINAR");
            currencyList.add("YEN");


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,currencyList);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCurrency.setAdapter(spinnerArrayAdapter);

            recyclerPaymentMethod.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            mAdapter = new PaymentMethodAdapter(getActivity(), paymentMethodModels);
            recyclerPaymentMethod.setAdapter(mAdapter);

            prepareData();


            return view;
        }

    private void initialize() {
            spCurrency=view.findViewById(R.id.sp_currency);
            recyclerPaymentMethod=view.findViewById(R.id.recycler_payment_method);
    }

    private void prepareData() {

        paymentMethodModels.clear();

        PaymentMethodModel model=new PaymentMethodModel("AUTHENTICATED");
        paymentMethodModels.add(model);

        mAdapter.notifyDataSetChanged();
    }
}
