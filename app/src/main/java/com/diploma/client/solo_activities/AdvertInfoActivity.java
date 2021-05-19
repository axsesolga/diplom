package com.diploma.client.solo_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.solo_activities.users.ClientProfileInfoActivity;

public class AdvertInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_info);
        int advertId = Integer.parseInt(getIntent().getStringExtra("EXTRA_ADVERT_ID"));


        Advert advert = MainActivity.getAdvertById(advertId); // get by id

        ((TextView) findViewById(R.id.advertId)).setText(Integer.toString(advert.id));
        ((TextView) findViewById(R.id.AdvertCreateDesiredValue)).setText(Integer.toString(advert.desired_value));
        ((TextView) findViewById(R.id.AdvertCreateTitle)).setText(advert.title);
        ((TextView) findViewById(R.id.AdvertCreateDescription)).setText(advert.description);
        ((TextView) findViewById(R.id.AdvertCreateAdditionalInfo)).setText(advert.additional_information);

        StringBuilder genres_str = new StringBuilder();
        for (Artwork.Genre genre: advert.genres)
            genres_str.append(genre.name).append("\n");

        StringBuilder styles_str = new StringBuilder();
        for (Artwork.Style style: advert.styles)
            styles_str.append(style.name).append("\n");

        ((TextView) findViewById(R.id.advertListOfGenres)).setText(genres_str.toString());
        ((TextView) findViewById(R.id.advertListOfStyles)).setText(styles_str.toString());



        Button openClient = (Button) findViewById(R.id.advertClientIdButton);
        openClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientProfileInfoActivity.class);
                intent.putExtra("OTHER_CLIENT_INFO", String.valueOf(advert.client_id));
                startActivity(intent);
            }
        });

    }


}