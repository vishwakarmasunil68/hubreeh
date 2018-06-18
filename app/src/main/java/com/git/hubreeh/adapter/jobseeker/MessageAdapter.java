package com.git.hubreeh.adapter.jobseeker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.jobseeker.MessageBeans;
import com.git.hubreeh.view.jobseeker.activity.ChatActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hp on 12/19/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<MessageBeans.MessagedBean> messagesModels = new ArrayList<>();

    public MessageAdapter(Activity context, List<MessageBeans.MessagedBean> messagesModels) {
        this.context = context;
        this.messagesModels = messagesModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;

        holder1.tvMsgName.setText(messagesModels.get(position).getContactName());
        holder1.ivOnlineStatus.setVisibility(View.GONE);
        holder1.tvLastMsg.setText(messagesModels.get(position).getLast_message());
        holder1.tvMsgTime.setText(messagesModels.get(position).getLast_created());
        holder1.tvIcon.setText(messagesModels.get(position).getContactName().substring(0, 1));
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChatActivity.class).putExtra("user_id", messagesModels.get(position).getUser_id()).putExtra("business_id", messagesModels.get(position).getBusiness_id()).putExtra("name", messagesModels.get(position).getContactName()));
                context.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

    }

    @Override
    public int getItemCount() {
        return messagesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView1)
        RoundedImageView imageView1;
        @BindView(R.id.tvIcon)
        AppCompatTextView tvIcon;
        @BindView(R.id.iv_online_status)
        CircleImageView ivOnlineStatus;
        @BindView(R.id.tv_msg_name)
        AppCompatTextView tvMsgName;
        @BindView(R.id.tv_msg_time)
        AppCompatTextView tvMsgTime;
        @BindView(R.id.tvLastMsg)
        AppCompatTextView tvLastMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
