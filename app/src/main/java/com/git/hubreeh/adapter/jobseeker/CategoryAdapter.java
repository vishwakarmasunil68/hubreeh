package com.git.hubreeh.adapter.jobseeker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.git.hubreeh.R;
import com.git.hubreeh.model.jobseeker.SearchViewBean;
import com.git.hubreeh.view.jobseeker.activity.SearchResultActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 12/19/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<SearchViewBean.CategoriesBean> bookedModels = new ArrayList<>();

    public CategoryAdapter(Activity context, List<SearchViewBean.CategoriesBean> bookedModels) {
        this.context = context;
        this.bookedModels = bookedModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder holder1 = (ViewHolder) holder;
        holder1.tvName.setText(bookedModels.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SearchResultActivity.class).putExtra("cat_id", bookedModels.get(position).getCategory_id()));

            }
        });

    }

    @Override
    public int getItemCount() {
        if (bookedModels.size() > 3) {
            return 3;
        } else {
            return bookedModels.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        AppCompatTextView tvName;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }


}
