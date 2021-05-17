package com.diploma.client.solo_activities.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.client.R;
import com.diploma.client.data.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserChatAdapter extends RecyclerView.Adapter {
    User mainUser;
    User secondUser;
    public ArrayList<UserChatMessage> messages;
    Context context;

    public UserChatAdapter(Context context, ArrayList<UserChatMessage> messages, User mainUser, User secondUser) {
        this.messages = messages;
        this.context = context;
        this.mainUser = mainUser;
        this.secondUser = secondUser;
    }


    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        UserChatMessage message = (UserChatMessage) messages.get(position);

        if (message.senderId.equals(mainUser.user_id)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_me, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_other, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserChatMessage message = (UserChatMessage) messages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }



    DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
    class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;


        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_date_other);
            nameText = (TextView) itemView.findViewById(R.id.text_gchat_user_other);
        }

        void bind(UserChatMessage message) {
            messageText.setText(message.text);

            timeText.setText(dateFormat.format(message.serverReceivedTime));
            nameText.setText(secondUser.name);
        }
    }

    class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_date_me);
        }

        void bind(UserChatMessage message) {
            messageText.setText(message.text);
            // Format the stored timestamp into a readable String using method.
            timeText.setText(dateFormat.format(message.serverReceivedTime));
        }
    }



}
