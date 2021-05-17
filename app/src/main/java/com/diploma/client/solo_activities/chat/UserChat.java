package com.diploma.client.solo_activities.chat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.User;
import com.diploma.client.network.API;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class UserChat extends AppCompatActivity {
    User mainUser = MainActivity.getUser();
    User secondUser;

    static ArrayList<UserChatMessage> getAllMessages() {
        MainActivity.updateMessages();
        return MainActivity.messages;
    }


    ArrayList<UserChatMessage> getUpdatedMessages() {
        class SortByDate implements Comparator<UserChatMessage> {
            @Override
            public int compare(UserChatMessage a, UserChatMessage b) {
                return a.serverReceivedTime.compareTo(b.serverReceivedTime);
            }
        }

        ArrayList<UserChatMessage> messages= new ArrayList<>();

        for (UserChatMessage message : getAllMessages()) {
            if (message.senderId == mainUser.user_id && message.receiverId == secondUser.user_id
                    || message.senderId == secondUser.user_id && message.receiverId == mainUser.user_id)
                messages.add(message);
        }
        Collections.sort(messages, new SortByDate());
        return messages;
    }
    LinearLayoutManager layoutManager;
RecyclerView rvMessages;
    UserChatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        secondUser = MainActivity.getUserById(Integer.parseInt(getIntent().getStringExtra("EXTRA_SECOND_USER")));
        assert secondUser != null;
        assert mainUser != null;


        Button sendMessageButton = (Button) this.findViewById(R.id.button_gchat_send);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        rvMessages = this.findViewById(R.id.recycler_gchat);
        adapter = new UserChatAdapter(this, getUpdatedMessages(), mainUser, secondUser);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(adapter);
    }



    public void sendMessage()
    {
        EditText chatEditText = (EditText) this.findViewById(R.id.edit_gchat_message);
        String text = chatEditText.getText().toString();
        String main_user_id = String.valueOf(mainUser.user_id);
        String second_user_id = String.valueOf(secondUser.user_id);

        try {
            new sendMessageAsync().execute(text, main_user_id, second_user_id).get();
            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
            chatEditText.setText("");
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Message failed", Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adapter.messages = getUpdatedMessages();
        rvMessages.getAdapter().notifyItemInserted( adapter.messages.size()-1);
        rvMessages.scrollToPosition(adapter.messages.size()-1);
        layoutManager.setStackFromEnd(true);



    }
    static class sendMessageAsync extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... params) {
            String messageText = params[0];
            int senderId = Integer.parseInt(params[1]);
            int reciverId = Integer.parseInt(params[2]);
            try {
                API.sendMessage(messageText,senderId,reciverId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}