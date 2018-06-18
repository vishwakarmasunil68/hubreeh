package com.git.hubreeh.view.employer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.git.hubreeh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/25/2018.
 */

public class FragmentBusinessNotification extends Fragment {
    View view;
    @BindView(R.id.sp_email_format)
    AppCompatSpinner spEmailFormat;

    List<String> emailFormatList=new ArrayList<>();


    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_business_notification, container, false);
        unbinder = ButterKnife.bind(this, view);

        emailFormatList.clear();
        emailFormatList.add("HTML");
        emailFormatList.add("NORMAL");
        emailFormatList.add("SCRIPTED");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,emailFormatList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmailFormat.setAdapter(spinnerArrayAdapter);



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
