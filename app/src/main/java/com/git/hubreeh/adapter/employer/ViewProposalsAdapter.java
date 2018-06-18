package com.git.hubreeh.adapter.employer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.git.hubreeh.R;
import com.git.hubreeh.model.employer.ViewProposalsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hp on 1/25/2018.
 */

public class ViewProposalsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ViewProposalsModel> viewProposalsModels = new ArrayList<>();
    public final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public ViewProposalsAdapter(Context context, List<ViewProposalsModel> viewProposalsModels) {
        this.context = context;
        this.viewProposalsModels = viewProposalsModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_proposals_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        holder1.tvViewProposalsUserName.setText(viewProposalsModels.get(position).getName());
        //viewBinderHelper.bind(holder1.swipeRevealLayout, dataObject.getId());


    }

    @Override
    public int getItemCount() {
        return viewProposalsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_proposals_user_profile)
        CircleImageView ivProposalsUserProfile;
        @BindView(R.id.tv_view_proposals_user_name)
        AppCompatTextView tvViewProposalsUserName;
        @BindView(R.id.btn_view_profile_award)
        AppCompatButton btnViewProfileAward;
        @BindView(R.id.swipeRevealLayout)
        SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }
}
