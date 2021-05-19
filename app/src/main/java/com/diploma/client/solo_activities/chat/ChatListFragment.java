package com.diploma.client.solo_activities.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.client.CustomTouchListener;
import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.User;

import java.util.ArrayList;
import java.util.HashSet;


public class ChatListFragment extends Fragment {
    int mainUserId;

    HashSet<User> users_set = new HashSet<>();
    ArrayList<User> users = new ArrayList<>();

    private RecyclerView rv_UsersList;

    void updateAllUsers() {
        users_set = new HashSet<>();
        MainActivity.updateMessagesAndUsers();
        for (UserChatMessage userChatMessage : MainActivity.messages) {
            int second_user = userChatMessage.senderId == mainUserId ? userChatMessage.receiverId : userChatMessage.senderId;
            if (second_user != mainUserId) {
                User user = MainActivity.getUserById(second_user);
                if (user != null)
                    users_set.add(MainActivity.getUserById(second_user));
            }
        }
        this.users = new ArrayList<>(users_set);
    }


    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAllUsers();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_chat_list, container, false);

            mainUserId = MainActivity.getUser().user_id;
            updateAllUsers();

            rv_UsersList = v.findViewById(R.id.rv);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            rv_UsersList.setLayoutManager(layoutManager);
            rv_UsersList.setAdapter(new ChatListUsersListAdapter(users));
        }


        rv_UsersList.addOnItemTouchListener(new CustomTouchListener(getContext(), new ChatListUsersListAdapter.onItemClickListener() {
            // При нажатии на элемент списка устанавливается текущий порядок и размер проигрывания
            @Override
            public void onClick(View view, int index) {
                Intent intent = new Intent(getContext(), UserChat.class);
                intent.putExtra("EXTRA_SECOND_USER", String.valueOf(users.get(index).user_id));
                startActivity(intent);
            }
        }));

        return v;
    }

}

