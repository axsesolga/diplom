package com.diploma.client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.diploma.client.data.LoginDataSource;
import com.diploma.client.data.LoginRepository;
import com.diploma.client.data.model.User;
import com.diploma.client.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    User user;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();
        ((Button) findViewById(R.id.openProfileAndSettingsActivity)).setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            setUser();
        }
    }

    void initButtons() {
        Button loginButton = (Button) findViewById(R.id.openLoginActivity);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        Button profileSettingsButton = (Button) findViewById(R.id.openProfileAndSettingsActivity);
        profileSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                startActivity(intent);
            }
        });
/*
        Button openUploadPictureButton = (Button) findViewById(R.id.openUploadPictureActivity);
        openUploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, .class);
                startActivity(intent);
            }
        });

        Button openCreateAdvertButton = (Button) findViewById(R.id.openCreateAdvertActivity);
        openCreateAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, .class);
                startActivity(intent);
            }
        });
        */
 
    }

    public void setUser() {
        user = LoginRepository.getInstance(new LoginDataSource()).getUser();
        ((Button) findViewById(R.id.openLoginActivity)).setEnabled(false);
        ((Button) findViewById(R.id.openProfileAndSettingsActivity)).setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user == null) {

        } else {

        }

    }
}