package com.git.hubreeh.view.jobseeker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.git.hubreeh.R;
import com.git.hubreeh.view.jobseeker.activity.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Hp on 12/19/2017.
 */

public class HomeProfile extends Fragment {
    View view;
    AppCompatTextView tvmin;
    @BindView(R.id.ivEdit)
    ImageView ivEdit;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_profile, container, false);

        initialize();

        String text = "<font color=#000000>Min.\n</font> <font color=#FFC005<>1250\n</font><font color=#FFC005<>AED\n</font>";
        tvmin.setText(Html.fromHtml(text));

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initialize() {
        tvmin = view.findViewById(R.id.tv_min);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ivEdit)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), ProfileActivity.class));

    }
}