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
        
    }
}