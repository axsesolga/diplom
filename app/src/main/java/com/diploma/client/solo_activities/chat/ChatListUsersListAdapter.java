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

import java.util.ArrayList;

public class ChatListUsersListAdapter extends RecyclerView.Adapter<ChatListUsersListAdapter.UserChatViewHolder> {
    ArrayList<User> users;
    ViewGroup parent;

    public ChatListUsersListAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.chat_list_user_card;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new UserChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserChatViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserChatViewHolder extends RecyclerView.ViewHolder {
        User user;
        TextView nickNameTextView; // nickname
        TextView userTypeTextView; // user type

        public UserChatViewHolder(@NonNull View itemView) {
            super(itemView);


            nickNameTextView = itemView.findViewById(R.id.tv_nickname);
            userTypeTextView = itemView.findViewById(R.id.tv_is_artist);

        }

        void bind(User user) {
            this.user = user;
            nickNameTextView.setText(user.name);
            userTypeTextView.setText(user.userType);
        }
    }

    public interface onItemClickListener {
        public void onClick(View view, int index);
    }

}
