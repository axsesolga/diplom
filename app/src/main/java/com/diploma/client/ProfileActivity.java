package com.diploma.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.diploma.client.data.LoginDataSource;
import com.diploma.client.data.LoginRepository;
import com.diploma.client.data.model.User;

public class ProfileActivity extends AppCompatActivity {
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // setting user
        user = LoginRepository.getInstance(new LoginDataSource()).getUser();
        ((TextView) findViewById(R.id.profileUsername)).setText(user.login);
        ((TextView) findViewById(R.id.profileMail)).setText(user.login);
        ((TextView) findViewById(R.id.profileName)).setText(user.name);
        ((TextView) findViewById(R.id.profilePictureUrl)).setText(user.profilePictureUrl);
        ((TextView) findViewById(R.id.profileType)).setText(user.userType);

        if (user.isArtist())
        {
            Artist artist = (Artist)user;
            ((TextView) findViewById(R.id.artistProfileNickname)).setText(artist.nickname);
            ((TextView) findViewById(R.id.artistProfileVKurl)).setText(artist.VkUrl);
            ((TextView) findViewById(R.id.artistProfileASurl)).setText(artist.ArtStationUrl);
            ((TextView) findViewById(R.id.artistProfileOtherUrl)).setText(artist.OtherUrl);
            ((TextView) findViewById(R.id.artistProfileAvailable)).setText(artist.available ? "available" : "not available");
            //((TextView) findViewById(R.id.artistProfileGenres)).setText(artist.genres.size());
            //((TextView) findViewById(R.id.artistProfileStyles)).setText(artist.styles.size());
            //((TextView) findViewById(R.id.artistProfilePrice)).setText(artist.styles.size());
        }
        
    }
}