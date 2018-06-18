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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hp on 1/10/2018.
 */

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Activity context;
    List<MessageBeans.ContactsBean> contactsModels;

    public ContactsAdapter(Activity context, List<MessageBeans.ContactsBean> contactsModels) {
        this.context = context;
        this.contactsModels = contactsModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_contacts_item_layout, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolderItem holderItem = (ViewHolderItem) holder;
        holderItem.tvContactsName.setText(contactsModels.get(position).getContactName());
        holderItem.tvIcon.setText(contactsModels.get(position).getContactName().substring(0, 1));
        holderItem.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChatActivity.class).putExtra("user_id", contactsModels.get(position).getUser_id()).putExtra("business_id", contactsModels.get(position).getBusiness_id()).putExtra("name", contactsModels.get(position).getContactName()));
                context.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsModels.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView1)
        RoundedImageView imageView1;
        @BindView(R.id.tvIcon)
        AppCompatTextView tvIcon;
        @BindView(R.id.iv_online_status)
        CircleImageView ivOnlineStatus;
        @BindView(R.id.tv_contacts_name)
        AppCompatTextView tvContactsName;
        @BindView(R.id.tv_contacts_msg)
        AppCompatTextView tvContactsMsg;


        public ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
