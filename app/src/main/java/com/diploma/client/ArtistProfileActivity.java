package com.diploma.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.client.data.LoginDataSource;
import com.diploma.client.data.LoginRepository;
import com.diploma.client.data.model.Artist;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Client;
import com.diploma.client.network.API;

import java.io.IOException;

public class ArtistProfileActivity extends AppCompatActivity {
    Artist artist = (Artist) LoginRepository.getInstance(new LoginDataSource()).getUser();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_profile);
        context = this;

        // setting user


        ((TextView) findViewById(R.id.profileArtistId)).setText(Integer.toString(artist.artist_id));
        ((TextView) findViewById(R.id.profileArtistUserId)).setText(Integer.toString(artist.user_id));
        ((TextView) findViewById(R.id.profileArtistUsername)).setText(artist.login);
        ((TextView) findViewById(R.id.profileArtistMail)).setText(artist.login);
        ((TextView) findViewById(R.id.profileArtistName)).setText(artist.name);
        ((TextView) findViewById(R.id.profileArtistPictureUrl)).setText(artist.profilePictureUrl);
        ((TextView) findViewById(R.id.profileArtistType)).setText(artist.userType);


        ((TextView) findViewById(R.id.artistProfileNickname)).setText(artist.nickname);
        ((TextView) findViewById(R.id.artistProfileVKurl)).setText(artist.VkUrl);
        ((TextView) findViewById(R.id.artistProfileASurl)).setText(artist.ArtStationUrl);
        ((TextView) findViewById(R.id.artistProfileOtherUrl)).setText(artist.OtherUrl);
        ((TextView) findViewById(R.id.artistProfileAvailable)).setText(artist.available ? "available" : "not available");
        try {
            ((TextView) findViewById(R.id.artistProfileGenres)).setText(Integer.toString(artist.genres.size()));
            ((TextView) findViewById(R.id.artistProfileStyles)).setText(Integer.toString(artist.styles.size()));
            //((TextView) findViewById(R.id.artistProfilePrice)).setText(Integer.toString(artist.price.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //logout
        Button logOutButton = (Button) findViewById(R.id.artistLogOut);
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
                API.logout();
                LoginRepository.getInstance(new LoginDataSource()).logout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}