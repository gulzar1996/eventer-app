package com.eventer.app.Chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eventer.app.R;

import java.util.List;

/**
 * Created by Gulzar on 26-10-2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final int MESSAGE_SENT = 1;
    private static final int MESSAGE_RECEIVED = 2;
    private List<Chat> chatList;
    private String mId;

    public ChatAdapter(List<Chat> chatList, String mId) {
        this.chatList = chatList;
        this.mId = mId;
    }

    @Override public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_sent, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_received, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.txtMessage.setText(chat.getMessage());
        holder.senderName.setText(chat.getsenderName());
    }

    @Override public int getItemCount() {
        return chatList.size();
    }

    @Override public int getItemViewType(int position) {
        if (chatList.get(position).getAuthor().equals(mId)) return MESSAGE_SENT;

        return MESSAGE_RECEIVED;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMessage;
        TextView senderName;
        public ViewHolder(View view) {
            super(view);
            txtMessage = (TextView) view.findViewById(R.id.txt_message);
            senderName= (TextView) view.findViewById(R.id.senderName);
        }
    }
}
