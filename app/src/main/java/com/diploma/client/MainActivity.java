package com.diploma.client;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.diploma.client.data.LoginDataSource;
import com.diploma.client.data.LoginRepository;
import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artist;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Client;
import com.diploma.client.data.model.Picture;
import com.diploma.client.data.model.User;
import com.diploma.client.solo_activities.chat.ChatListFragment;
import com.diploma.client.fragments.CreateArtistFragment;
import com.diploma.client.fragments.CreateClientFragment;
import com.diploma.client.fragments.EmptyFragment;
import com.diploma.client.fragments.FavouriteFragment;
import com.diploma.client.fragments.HomeFragment;
import com.diploma.client.fragments.ProfileSettingsFragment;
import com.diploma.client.network.API;
import com.diploma.client.solo_activities.chat.UserChatMessage;
import com.diploma.client.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public Context context = this;

    public static User getUser() {
        return LoginRepository.getInstance(new LoginDataSource()).getUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();

        // need to init Genres Types Styles, almost non changing values. Update per app run is enough
        new getStaticArtworkData().execute();
        updateAdvertsPictures();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();

    }

    static public ArrayList<Advert> adverts;
    static public ArrayList<Picture> pictures;
    static public ArrayList<UserChatMessage> messages;
    static public ArrayList<Client> clients;
    static public ArrayList<Artist> artists;

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

    public static void updateAdvertsPictures() {
        try {
            new updateAdvertsPicturesAsync().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public static void updateMessages()
    {
        try {
            new updateMessagesAsync().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class updateMessagesAsync extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {

            try {
                MainActivity.clients = (ArrayList<Client>) API.getAllClients();
                MainActivity.artists = (ArrayList<Artist>) API.getAllArtists();
                MainActivity.messages = API.getAllChatMessages();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    HomeFragment homeFragment = new HomeFragment();
    ChatListFragment chatListFragment = new ChatListFragment();
    CreateClientFragment createClientFragment = new CreateClientFragment();
    CreateArtistFragment createArtistFragment = new CreateArtistFragment();
    FavouriteFragment favouriteFragment = new FavouriteFragment();
    EmptyFragment emptyFragment = new EmptyFragment();
    ProfileSettingsFragment profileSettingsFragment = new ProfileSettingsFragment();
    BottomNavigationView bottomNavigationView;



    void initButtons() {

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = emptyFragment;

                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                //finish();
                                selectedFragment = homeFragment;
                                break;

                            case R.id.navigation_chat:
                                //finish();
                                if (getUser() != null)
                                    selectedFragment = chatListFragment;
                                break;

                            case R.id.navigation_Create:
                                //finish();tr
                                if (getUser() == null) {
                                    Toast.makeText(getApplicationContext(), "You must be logged in to create new Pictures or Adverts", Toast.LENGTH_LONG).show();

                                    break;
                                }

                                if (getUser().isArtist())
                                    selectedFragment = createArtistFragment;
                                else
                                    selectedFragment = createClientFragment;


                                break;

                            case R.id.navigation_Fav:
                                //finish();
                                selectedFragment = favouriteFragment;
                                break;

                            case R.id.navigation_Profile:
                                //finish();
                                if (getUser() == null)
                                    startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), 0);

                                if (getUser() != null)
                                    selectedFragment = profileSettingsFragment;
                                else {
                                    // Notify user he must be logged in
                                    Toast.makeText(getApplicationContext(), "You must be logged in to view profile settings", Toast.LENGTH_LONG).show();
                                }
                                break;
                        }


                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                        return true;
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getUser() != null) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileSettingsFragment).commit();
            updateMessages();
        }
    }

    public static User getUserById(int id)
    {
        for (Client client:clients)
            if (client.user_id == id)
                return client;

        for (Artist artist:artists)
            if (artist.user_id == id)
                return artist;
        return null;
    }


// В зависимости от кого какой пользователя активен активируем или убираем часть функционала

}