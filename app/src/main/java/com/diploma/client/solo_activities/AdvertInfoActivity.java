package com.diploma.client.solo_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Advert;

public class AdvertInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_info);
        int advertId = getIntent().getIntExtra("EXTRA_ADVERT_ID", 0);
        Advert advert = MainActivity.adverts.get(advertId); // todo  // make get by id

        ((TextView) findViewById(R.id.advertId)).setText(Integer.toString(advert.id));
        ((TextView) findViewById(R.id.AdvertCreateDesiredValue)).setText(Integer.toString(advert.desired_value));
        ((TextView) findViewById(R.id.AdvertCreateTitle)).setText(advert.title);
        ((TextView) findViewById(R.id.AdvertCreateDescription)).setText(advert.description);
        ((TextView) findViewById(R.id.AdvertCreateAdditionalInfo)).setText(advert.additional_information);
        ((TextView) findViewById(R.id.advertClientId)).setText(Integer.toString(advert.client_id));
        ((TextView) findViewById(R.id.advertListOfGenres)).setText(Integer.toString(advert.list_of_genres.size()));
        ((TextView) findViewById(R.id.advertListOfStyles)).setText(Integer.toString(advert.list_of_styles.size()));
    }


}