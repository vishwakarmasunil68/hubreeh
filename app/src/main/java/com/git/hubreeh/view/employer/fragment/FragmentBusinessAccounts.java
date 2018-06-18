package com.git.hubreeh.view.employer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.view.common.StarterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Hp on 1/25/2018.
 */

public class FragmentBusinessAccounts extends Fragment {
    View view;
    @BindView(R.id.btnLogout)
    AppCompatButton btnLogout;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_business_accounts, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLogout)
    public void onViewClicked() {
        String token = MyApplication.readStringPref(PrefData.PREF_FCM_TOKEN);
        MyApplication.clearPref();
        MyApplication.writeStringPref(PrefData.PREF_FCM_TOKEN, token);
        startActivity(new Intent(getActivity(), StarterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        getActivity().finish();
    }
}
