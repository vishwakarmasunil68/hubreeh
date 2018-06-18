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
import com.git.hubreeh.adapter.employer.ViewMilestoneAdapter;
import com.git.hubreeh.model.employer.ViewMilestoneModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 1/30/2018.
 */

public class FragmentViewMilestone extends Fragment {

    View view;

    RecyclerView recyclerViewMilestone;
    ViewMilestoneAdapter mAdapter;
    List<ViewMilestoneModel> viewMilestoneModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_view_milestone,container,false);

        initialize();
        recyclerViewMilestone.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ViewMilestoneAdapter(getActivity(), viewMilestoneModels);
        recyclerViewMilestone.setAdapter(mAdapter);


        prepareData();

        return view;
    }

    private void prepareData() {
        viewMilestoneModels.clear();


        ViewMilestoneModel model=new ViewMilestoneModel("09-Dec-2018 10:40AM","Some Employer","Some Project Name","20000","some description about project","In Progess");
        viewMilestoneModels.add(model);
        model=new ViewMilestoneModel("09-Dec-2018 10:40AM","Some Employer","Some Project Name","20000","some description about project","In Progess");
        viewMilestoneModels.add(model);
        model=new ViewMilestoneModel("09-Dec-2018 10:40AM","Some Employer","Some Project Name","20000","some description about project","In Progess");
        viewMilestoneModels.add(model);
        model=new ViewMilestoneModel("09-Dec-2018 10:40AM","Some Employer","Some Project Name","20000","some description about project","In Progess");
        viewMilestoneModels.add(model);
        model=new ViewMilestoneModel("09-Dec-2018 10:40AM","Some Employer","Some Project Name","20000","some description about project","In Progess");
        viewMilestoneModels.add(model);
        model=new ViewMilestoneModel("09-Dec-2018 10:40AM","Some Employer","Some Project Name","20000","some description about project","In Progess");
        viewMilestoneModels.add(model);



        mAdapter.notifyDataSetChanged();



    }

    private void initialize() {
        recyclerViewMilestone=view.findViewById(R.id.recycler_view_milestone);
    }
}
