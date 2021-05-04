package com.diploma.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.diploma.client.data.LoginDataSource;
import com.diploma.client.data.LoginRepository;
import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Picture;
import com.diploma.client.data.model.User;
import com.diploma.client.network.API;
import com.diploma.client.ui.login.LoginActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    User user;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();


        // need to init Genres Types Styles, almost non changing values. Update per app run is enough
        new getStaticArtworkData().execute();
        updateAdvertsPictures();

    }

    static public ArrayList<Advert> adverts;
    static public ArrayList<Picture> pictures;

    static class getStaticArtworkData extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {

            try {
                Artwork.Genre.allGenres = API.getAllGenres();
                Artwork.Style.allStyles = API.getAllStyles();
                Artwork.Type.allTypes = API.getAllTypes();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void updateAdvertsPictures()
    {
        new updateAdvertsPicturesAsync().execute();
    }
    static class updateAdvertsPicturesAsync extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {

            try {
                MainActivity.adverts = API.getAllAdverts();
                MainActivity.pictures = API.getAllPictures();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
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
                if (user.isArtist()) {
                    Intent intent = new Intent(context, ArtistProfileActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ClientProfileActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button openUploadPictureButton = (Button) findViewById(R.id.openUploadPictureActivity);
        openUploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UploadPictureActivity.class);
                startActivity(intent);
            }
        });

        Button openCreateAdvertButton = (Button) findViewById(R.id.openCreateAdvertActivity);
        openCreateAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateAdvertActivity.class);
                startActivity(intent);
            }
        });

        Button openAdvertInfoButton = (Button) findViewById(R.id.openAdvertInfoActivity);
        openAdvertInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdvertInfoActivity.class);
                intent.putExtra("EXTRA_ADVERT_ID", 0);
                startActivity(intent);
            }
        });

        Button openPictureInfoButton = (Button) findViewById(R.id.openPictureInfoActivity);
        openPictureInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PictureInfoActivity.class);
                intent.putExtra("EXTRA_PICTURE_ID", 0);
                startActivity(intent);
            }
        });


        Button openChatButton = (Button) findViewById(R.id.openChatActivity);
        openChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatListViewActivity.class);
                startActivity(intent);
            }
        });
    }


    // В зависимости от кого какой пользователя активен активируем или убираем часть функционала

    @Override
    protected void onResume() {
        super.onResume();
        user = LoginRepository.getInstance(new LoginDataSource()).getUser();

        if (user == null) {
            setNoneUserInterface();
        } else {
            if (user.isArtist()) {
                setArtistInterface();
            } else {
                setClientInterface();
            }
        }
    }

    public void setArtistInterface() {
        ((Button) findViewById(R.id.openProfileAndSettingsActivity)).setEnabled(true);
        ((Button) findViewById(R.id.openLoginActivity)).setEnabled(false);
        ((Button) findViewById(R.id.openChatActivity)).setEnabled(true);
    }

    public void setClientInterface() {
        ((Button) findViewById(R.id.openProfileAndSettingsActivity)).setEnabled(true);
        ((Button) findViewById(R.id.openLoginActivity)).setEnabled(false);
        ((Button) findViewById(R.id.openChatActivity)).setEnabled(true);
    }

    public void setNoneUserInterface() {
        ((Button) findViewById(R.id.openProfileAndSettingsActivity)).setEnabled(false);
        ((Button) findViewById(R.id.openLoginActivity)).setEnabled(true);
        ((Button) findViewById(R.id.openChatActivity)).setEnabled(false);
    }
}