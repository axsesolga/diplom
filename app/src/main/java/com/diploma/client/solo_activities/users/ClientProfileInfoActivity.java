package com.diploma.client.solo_activities.users;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.LoginDataSource;
import com.diploma.client.data.LoginRepository;
import com.diploma.client.data.model.Client;
import com.diploma.client.network.API;
import com.diploma.client.solo_activities.chat.UserChat;

public class ClientProfileInfoActivity extends AppCompatActivity {
    Client main_client;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        context = this;
        String extra = getIntent().getStringExtra("OTHER_CLIENT_INFO");
        if (extra != null) {
            main_client = MainActivity.getClientByClientId(Integer.parseInt(extra));

            if (MainActivity.getUser() != null && MainActivity.getUser().user_id != main_client.user_id) {
                Button send_message = (Button) findViewById(R.id.sendMessageClientButton);
                send_message.setVisibility(View.VISIBLE);
                send_message.setEnabled(true);
                send_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), UserChat.class);
                        intent.putExtra("EXTRA_SECOND_USER", String.valueOf(main_client.user_id));
                        startActivity(intent);
                    }
                });
            }


        } else
            main_client = (Client) MainActivity.getUser();


        ((TextView) findViewById(R.id.profileClientUsername)).setText(main_client.login);
        ((TextView) findViewById(R.id.profileClientMail)).setText(main_client.login);
        ((TextView) findViewById(R.id.profileClientName)).setText(main_client.name);
        ((TextView) findViewById(R.id.profileClientType)).setText(main_client.userType);

        StringBuilder favs = new StringBuilder();
        for (int id : main_client.favouriteArtsitsIDs)
            favs.append(id);

        ((TextView) findViewById(R.id.clientFavouriteArtists)).setText(favs);
    }

    static class logoutAsynch extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            try {
                LoginRepository.getInstance(new LoginDataSource()).logout();
                API.logout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}