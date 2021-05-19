package com.diploma.client.solo_activities.users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Artist;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.solo_activities.chat.UserChat;

public class ArtistProfileInfoActivity extends AppCompatActivity {
    Artist artist;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_profile);
        context = this;
        // setting user
        String extra = getIntent().getStringExtra("OTHER_ARTIST_INFO");
        if (extra != null) {
            artist = MainActivity.getArtistByArtistId(Integer.parseInt(extra));

            if (MainActivity.getUser() != null && MainActivity.getUser().user_id != artist.user_id) {
                Button send_message = (Button) findViewById(R.id.sendMessageArtistButton);
                send_message.setVisibility(View.VISIBLE);
                send_message.setEnabled(true);
                send_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), UserChat.class);
                        intent.putExtra("EXTRA_SECOND_USER", String.valueOf(artist.user_id));
                        startActivity(intent);
                    }
                });
            }


        } else
            artist = (Artist) MainActivity.getUser();


        ((TextView) findViewById(R.id.profileArtistUsername)).setText(artist.login);
        ((TextView) findViewById(R.id.profileArtistMail)).setText(artist.login);
        ((TextView) findViewById(R.id.profileArtistName)).setText(artist.name);
        ((TextView) findViewById(R.id.profileArtistType)).setText(artist.userType);


        ((TextView) findViewById(R.id.artistProfileNickname)).setText(artist.nickname);
        ((TextView) findViewById(R.id.artistProfileVKurl)).setText(artist.VkUrl);
        ((TextView) findViewById(R.id.artistProfileASurl)).setText(artist.ArtStationUrl);
        ((TextView) findViewById(R.id.artistProfileOtherUrl)).setText(artist.OtherUrl);
        ((TextView) findViewById(R.id.artistProfileAvailable)).setText(artist.available ? "available" : "not available");

        StringBuilder genres_str = new StringBuilder();
        for (Artwork.Genre genre : artist.genres)
            genres_str.append(genre.name).append("\n");

        StringBuilder styles_str = new StringBuilder();
        for (Artwork.Style style : artist.styles)
            styles_str.append(style.name).append("\n");

        ((TextView) findViewById(R.id.artistProfileGenres)).setText(genres_str.toString());
        ((TextView) findViewById(R.id.artistProfileStyles)).setText(styles_str.toString());
        //((TextView) findViewById(R.id.artistProfilePrice)).setText(Integer.toString(artist.price.size()));


    }

}