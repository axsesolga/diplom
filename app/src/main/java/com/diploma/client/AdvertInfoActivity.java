package com.diploma.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.network.API;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AdvertInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_info);
        int advertId = getIntent().getIntExtra("EXTRA_ADVERT_ID", 0);
        Advert advert = MainActivity.adverts.get(advertId); // todo  // make get by id

        ((TextView) findViewById(R.id.advertId)).setText(Integer.toString(advert.id));
        ((TextView) findViewById(R.id.advertDesiredValue)).setText(Integer.toString(advert.desired_value));
        ((TextView) findViewById(R.id.advertTitle)).setText(advert.title);
        ((TextView) findViewById(R.id.advertDescription)).setText(advert.description);
        ((TextView) findViewById(R.id.advertAdditionalInfo)).setText(advert.additional_information);
        ((TextView) findViewById(R.id.advertClientId)).setText(Integer.toString(advert.client_id));
        ((TextView) findViewById(R.id.advertListOfGenres)).setText(Integer.toString(advert.list_of_genres.size()));
        ((TextView) findViewById(R.id.advertListOfStyles)).setText(Integer.toString(advert.list_of_styles.size()));
    }


}