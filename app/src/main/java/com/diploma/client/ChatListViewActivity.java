package com.diploma.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.diploma.client.data.model.ChatMessage;
import com.diploma.client.network.API;

import java.util.concurrent.ExecutionException;

public class ChatListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);


        try {
            ((TextView) findViewById(R.id.chatTextView)).setText(new GetChatAsyncTask().execute("none","none").get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class GetChatAsyncTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... pair) {

            try {
                StringBuilder str = new StringBuilder();
                for (ChatMessage chatMessage : API.getAllChatMessages()) {
                    str.append(chatMessage.text);
                    str.append("\t");
                    str.append(chatMessage.receiverId);
                    str.append("\t");
                    str.append(chatMessage.senderId);
                    str.append("\t");
                    str.append(chatMessage.serverReceivedTime.toString());
                    str.append("\n");
                }
                if (str.length() == 0)
                    return "no messages";

                return str.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error with code. no messages found";
            }
        }
    }
}