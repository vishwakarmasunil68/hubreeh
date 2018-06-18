package com.git.hubreeh.view.jobseeker.fragment;

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
 * Created by Hp on 1/30/2018.
 */

public class FragmentCreateMilestone extends Fragment {

    View view;
    @BindView(R.id.sp_jobseeker_select_project)
    AppCompatSpinner spJobseekerSelectProject;
    Unbinder unbinder;

    List<String> selectProject=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_milestone, container, false);
        unbinder = ButterKnife.bind(this, view);

        addDatainSpinner();

        return view;
    }

    private void addDatainSpinner() {
        selectProject.add("Project -1");
        selectProject.add("Project -2");
        selectProject.add("Project -3");
        selectProject.add("Project -4");
        selectProject.add("Project -5");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,selectProject);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJobseekerSelectProject.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
