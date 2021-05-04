package com.diploma.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.client.data.LoginDataSource;
import com.diploma.client.data.LoginRepository;
import com.diploma.client.data.model.Client;
import com.diploma.client.network.API;

import java.io.IOException;

public class ClientProfileActivity extends AppCompatActivity {
    Client client = (Client) LoginRepository.getInstance(new LoginDataSource()).getUser();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        context = this;


        ((TextView) findViewById(R.id.profileClientId)).setText(Integer.toString(client.client_id));
        ((TextView) findViewById(R.id.profileClientUserId)).setText(Integer.toString(client.user_id));

        ((TextView) findViewById(R.id.profileClientUsername)).setText(client.login);
        ((TextView) findViewById(R.id.profileClientMail)).setText(client.login);
        ((TextView) findViewById(R.id.profileClientName)).setText(client.name);
        ((TextView) findViewById(R.id.profileClientPictureUrl)).setText(client.profilePictureUrl);
        ((TextView) findViewById(R.id.profileClientType)).setText(client.userType);

        StringBuilder favs = new StringBuilder();
        for (int id : client.favouriteArtsitsIDs)
            favs.append(id);

        ((TextView) findViewById(R.id.clientFavouriteArtists)).setText(favs);

        //logout
        Button logOutButton = (Button) findViewById(R.id.clientLogOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new logoutAsynch().execute();
                finish();
            }
        });
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