package com.diploma.client.solo_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.diploma.client.R;
import com.diploma.client.data.model_edit.SelectGenresActivity;
import com.diploma.client.data.model_edit.SelectStylesActivity;

public class CreateAdvertActivity extends AppCompatActivity {
    EditText desiredValue;
    EditText advertTitle;
    EditText advertDescription;
    EditText advertAdditionalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        desiredValue = findViewById(R.id.AdvertCreateDesiredValue);
        advertTitle = findViewById(R.id.AdvertCreateTitle);
        advertDescription = findViewById(R.id.AdvertCreateDescription);
        advertAdditionalInfo = findViewById(R.id.AdvertCreateAdditionalInfo);

        Button openGenresSelectionButton = findViewById(R.id.AdvertCreateSelectGenres);
        openGenresSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), SelectGenresActivity.class), 0);
            }
        });

        Button openStylesSelectionButton = findViewById(R.id.AdvertCreateSelectStyles);
        openStylesSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), SelectStylesActivity.class), 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}