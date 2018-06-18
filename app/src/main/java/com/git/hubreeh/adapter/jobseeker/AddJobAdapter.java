package com.git.hubreeh.adapter.jobseeker;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.ApiServices.ApiResponseGetJob;
import com.git.hubreeh.ApiServices.ApiResponseJob;
import com.git.hubreeh.R;
import com.git.hubreeh.helper.ProgressView;
import com.git.hubreeh.pojo.AddedJobPOJO;
import com.git.hubreeh.utility.Constants;
import com.git.hubreeh.webservice.WebServiceBase;
import com.git.hubreeh.webservice.WebServicesCallBack;
import com.git.hubreeh.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 12/19/2017.
 */

public class AddJobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity context;
    List<AddedJobPOJO> addJobModelList;

    public AddJobAdapter(Activity context, List<AddedJobPOJO> addJobModelList) {
        this.context = context;
        this.addJobModelList = addJobModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_images, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvNumber.setText(addJobModelList.get(position).getJobDescription());


        viewHolder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                removeJob(addJobModelList.get(position).getCompany_id(), position);
                ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", Constants.jobUserPOJO.getUserId()));
                nameValuePairs.add(new BasicNameValuePair("prev_job_id",addJobModelList.get(position).getPrevJobId()));
                new WebServiceBase(nameValuePairs, context, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String apicall, String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.optString("status").equalsIgnoreCase("1")){
                                addJobModelList.remove(position);
                                notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, "REMOVE_JOBS",true).execute(WebServicesUrls.REMOVE_PREVIOUS_JOBS);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addJobModelList.size();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.ivRemove)
        ImageView ivRemove;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public void removeJob(String id, final int position) {
        final ProgressView progressView = new ProgressView(context);
        progressView.showLoader();
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        Call<ApiResponseJob> call = apiInterface.remove_company(id);

        call.enqueue(new Callback<ApiResponseJob>() {
            @Override
            public void onResponse(Call<ApiResponseJob> call, Response<ApiResponseJob> response) {
                progressView.hideLoader();
                if (response.body().getStatus() == 1) {
                    addJobModelList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, addJobModelList.size());
                }
            }

            @Override
            public void onFailure(Call<ApiResponseJob> call, Throwable t) {
                progressView.hideLoader();
                t.printStackTrace();
            }
        });
    }
}
