package com.git.hubreeh.view.jobseeker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.git.hubreeh.R;
import com.git.hubreeh.view.employer.activity.HomeHandlerBusiness;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Hp on 12/18/2017.
 */

public class SignupBusinessConfirmFirm extends Fragment {
    View view;
    @BindView(R.id.iv_clock)
    ImageView ivClock;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signupbusiness_confirm_firm, container, false);
        unbinder = ButterKnife.bind(this, view);


        ivClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), HomeHandlerBusiness.class));
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
