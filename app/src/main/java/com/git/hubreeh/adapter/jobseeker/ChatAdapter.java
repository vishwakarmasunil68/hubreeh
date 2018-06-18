package com.git.hubreeh.adapter.jobseeker;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.git.hubreeh.R;
import com.git.hubreeh.helper.MyApplication;
import com.git.hubreeh.helper.PrefData;
import com.git.hubreeh.model.jobseeker.ChatBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 1/6/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ChatBean.ResBean> chatModels = new ArrayList<>();
    private int TYPE_SEND = 1;
    private int TYPE_RECEIVE = 2;

    private int TYPE_SEND_IMAEG = 3;
    private int TYPE_RECEVIE_IMAGE = 4;

    private int TYPE_SEND_VIDEO = 5;
    private int TYPE_RECEVIE_VIDEO = 6;

    private int TYPE_SEND_AUDIO = 7;
    private int TYPE_RECEVIE_AUDIO = 8;

    private int TYPE_SEND_FILE = 9;
    private int TYPE_RECEVIE_FILE = 10;

    String name;

    public ChatAdapter(Context context, List<ChatBean.ResBean> chatModels, String name) {
        this.context = context;
        this.chatModels = chatModels;
        this.name = name;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SEND) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_chat_bubble, parent, false);
            return new VHSendText(itemView);
        } else if (viewType == TYPE_RECEIVE) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_chat_bubble, parent, false);
            return new VHRecText(itemView);
        } else if (viewType == TYPE_SEND_IMAEG) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_chat_image_bubble, parent, false);
            return new VHSendImage(itemView);
        } else if (viewType == TYPE_RECEVIE_IMAGE) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_chat_image_bubble, parent, false);
            return new VHRecImage(itemView);
        } else if (viewType == TYPE_SEND_VIDEO) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_chat_video_bubble, parent, false);
            return new VHSendVideo(itemView);
        } else if (viewType == TYPE_RECEVIE_VIDEO) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_chat_video_bubble, parent, false);
            return new VHRecVideo(itemView);
        } else if (viewType == TYPE_SEND_AUDIO) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_chat_audio_bubble, parent, false);
            return new VHSendAudio(itemView);
        } else if (viewType == TYPE_RECEVIE_AUDIO) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_chat_audio_bubble, parent, false);
            return new VHRecAudio(itemView);
        } else if (viewType == TYPE_SEND_FILE) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_chat_file_bubble, parent, false);
            return new VHSendFile(itemView);
        } else if (viewType == TYPE_RECEVIE_FILE) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_chat_file_bubble, parent, false);
            return new VHRecFile(itemView);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_SEND) {
            VHSendText vhSendText = (VHSendText) holder;
            vhSendText.tvContent.setText(chatModels.get(position).getMessage());
        } else if (holder.getItemViewType() == TYPE_RECEIVE) {
            VHRecText vhRecText = (VHRecText) holder;
            vhRecText.tvContent.setText(chatModels.get(position).getMessage());
            vhRecText.tvIcon.setText(name.substring(0, 1));

        }

    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }


    @Override
    public int getItemViewType(int position) {
        int type = 0;

        String loginType = MyApplication.readStringPref(PrefData.LOGIN_TYPE);

        if (loginType.equalsIgnoreCase("job")) {

            if (chatModels.get(position).getLogin_type().equalsIgnoreCase("user")) {
                if (chatModels.get(position).getMessage_type().equalsIgnoreCase("text")) {
                    type = TYPE_SEND;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("image")) {
                    type = TYPE_SEND_IMAEG;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("video")) {
                    type = TYPE_SEND_VIDEO;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("audio")) {
                    type = TYPE_SEND_AUDIO;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("file")) {
                    type = TYPE_SEND_FILE;
                }
            } else {
                if (chatModels.get(position).getMessage_type().equalsIgnoreCase("text")) {
                    type = TYPE_RECEIVE;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("image")) {
                    type = TYPE_RECEVIE_IMAGE;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("video")) {
                    type = TYPE_RECEVIE_VIDEO;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("audio")) {
                    type = TYPE_RECEVIE_AUDIO;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("file")) {
                    type = TYPE_RECEVIE_FILE;
                }
            }


        } else if (loginType.equalsIgnoreCase("bus")) {
            if (chatModels.get(position).getLogin_type().equalsIgnoreCase("business")) {
                if (chatModels.get(position).getMessage_type().equalsIgnoreCase("text")) {
                    type = TYPE_SEND;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("image")) {
                    type = TYPE_SEND_IMAEG;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("video")) {
                    type = TYPE_SEND_VIDEO;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("audio")) {
                    type = TYPE_SEND_AUDIO;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("file")) {
                    type = TYPE_SEND_FILE;
                }
            } else {
                if (chatModels.get(position).getMessage_type().equalsIgnoreCase("text")) {
                    type = TYPE_RECEIVE;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("image")) {
                    type = TYPE_RECEVIE_IMAGE;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("video")) {
                    type = TYPE_RECEVIE_VIDEO;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("audio")) {
                    type = TYPE_RECEVIE_AUDIO;
                } else if (chatModels.get(position).getMessage_type().equalsIgnoreCase("file")) {
                    type = TYPE_RECEVIE_FILE;
                }
            }
        }

        return type;
    }

    public class VHSendText extends RecyclerView.ViewHolder {
        @BindView(R.id.tvContent)
        AppCompatTextView tvContent;

        public VHSendText(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VHRecText extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIcon)
        AppCompatTextView tvIcon;
        @BindView(R.id.tvContent)
        AppCompatTextView tvContent;

        public VHRecText(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VHSendImage extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.btnRetry)
        Button btnRetry;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.linearLayout3)
        RelativeLayout linearLayout3;

        public VHSendImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VHRecImage extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.btnRetry)
        Button btnRetry;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.linearLayout2)
        RelativeLayout linearLayout2;
        @BindView(R.id.lin_chat_left)
        LinearLayout linChatLeft;

        public VHRecImage(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VHSendVideo extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.btnRetry)
        Button btnRetry;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.linearLayout3)
        RelativeLayout linearLayout3;

        public VHSendVideo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VHRecVideo extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIcon)
        AppCompatTextView tvIcon;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.btnRetry)
        Button btnRetry;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.linearLayout2)
        RelativeLayout linearLayout2;
        @BindView(R.id.lin_chat_left)
        LinearLayout linChatLeft;

        public VHRecVideo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VHSendAudio extends RecyclerView.ViewHolder {
        @BindView(R.id.linearLayout4)
        LinearLayout linearLayout4;
        @BindView(R.id.filename)
        TextView filename;
        @BindView(R.id.tvtime)
        TextView tvtime;

        public VHSendAudio(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VHRecAudio extends RecyclerView.ViewHolder {
        @BindView(R.id.linearLayout4)
        LinearLayout linearLayout4;
        @BindView(R.id.filename)
        TextView filename;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.linearLayout2)
        LinearLayout linearLayout2;
        @BindView(R.id.lin_chat_left)
        LinearLayout linChatLeft;

        public VHRecAudio(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VHSendFile extends RecyclerView.ViewHolder {
        @BindView(R.id.linearLayout4)
        LinearLayout linearLayout4;
        @BindView(R.id.filename)
        TextView filename;
        @BindView(R.id.time)
        TextView time;

        public VHSendFile(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class VHRecFile extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIcon)
        AppCompatTextView tvIcon;
        @BindView(R.id.linearLayout4)
        LinearLayout linearLayout4;
        @BindView(R.id.filename)
        TextView filename;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.linearLayout2)
        LinearLayout linearLayout2;
        @BindView(R.id.lin_chat_left)
        LinearLayout linChatLeft;

        public VHRecFile(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
