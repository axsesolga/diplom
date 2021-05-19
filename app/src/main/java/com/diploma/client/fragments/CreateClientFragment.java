package com.diploma.client.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Client;
import com.diploma.client.data.model_edit.SelectGenresActivity;
import com.diploma.client.data.model_edit.SelectStylesActivity;
import com.diploma.client.network.API;

import java.io.IOException;
import java.util.ArrayList;


public class CreateClientFragment extends Fragment {

    EditText editDesiredValue;
    EditText editAdvertTitle;
    EditText editAdvertDescription;
    EditText editAdvertAdditionalInfo;
    ArrayList<Artwork.Genre> genres = new ArrayList<>();
    ArrayList<Artwork.Style> styles = new ArrayList<>();

    public CreateClientFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_create_advert, container, false);

        editDesiredValue = v.findViewById(R.id.AdvertCreateDesiredValue);
        editAdvertTitle = v.findViewById(R.id.AdvertCreateTitle);
        editAdvertDescription = v.findViewById(R.id.AdvertCreateDescription);
        editAdvertAdditionalInfo = v.findViewById(R.id.AdvertCreateAdditionalInfo);

        Button openGenresSelectionButton = v.findViewById(R.id.AdvertCreateSelectGenres);
        openGenresSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), SelectGenresActivity.class), 20);
            }
        });

        Button openStylesSelectionButton = v.findViewById(R.id.AdvertCreateSelectStyles);
        openStylesSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), SelectStylesActivity.class), 30);
            }
        });

        Button createPictureButton = (Button) v.findViewById(R.id.createAdvertButton);
        createPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int desiredValue = Integer.parseInt(editDesiredValue.getText().toString());
                String add_info = editAdvertAdditionalInfo.getText().toString();
                String descr = editAdvertDescription.getText().toString();
                String title = editAdvertTitle.getText().toString();


                Advert advert = new Advert(-1, desiredValue, title, descr, add_info, ((Client) MainActivity.getUser()).client_id, genres, styles);
                try {
                    if (new uploadAdvertToServer().execute(advert).get()) {
                        Toast.makeText(getContext(), "Advert uploaded to server", Toast.LENGTH_LONG).show();
                        MainActivity.updateAdvertsPictures();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });
        return v;
    }

    static class uploadAdvertToServer extends AsyncTask<Advert, Void, Boolean> {
        protected Boolean doInBackground(Advert... params) {
            try {
                API.createAdvert((Advert) params[0]);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 20: // genres selected
                if (resultCode == -1) {
                    StringBuilder str = new StringBuilder();
                    String res = data.getExtras().getString("GENRES"); // ids sep by comma
                    for (String str_id : res.split(",")) {
                        int id = Integer.parseInt(str_id);
                        Artwork.Genre genre = Artwork.Genre.getById(id);
                        genres.add(genre);
                        str.append(genre.name);
                        str.append(",");
                    }
                    ((TextView) getView().findViewById(R.id.AdvertCreateShowGenres)).setText(str.toString());
                }
                break;

            case 30: // styles selected
                if (resultCode == -1) {
                    StringBuilder str = new StringBuilder();
                    String res = data.getExtras().getString("STYLES"); // ids sep by comma
                    for (String str_id : res.split(",")) {
                        int id = Integer.parseInt(str_id);
                        Artwork.Style style = Artwork.Style.getById(id);
                        styles.add(style);
                        str.append(style.name);
                        str.append(",");
                    }
                    ((TextView) getView().findViewById(R.id.AdvertCreateShowStyles)).setText(str.toString());
                }
                break;
        }
    }
}