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
import com.diploma.client.data.model.Artist;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Picture;
import com.diploma.client.data.model_edit.SelectGenresActivity;
import com.diploma.client.data.model_edit.SelectStylesActivity;
import com.diploma.client.network.API;

import java.io.IOException;
import java.util.ArrayList;


public class CreateArtistFragment extends Fragment {
    String base64string;
    ArrayList<Artwork.Genre> genres = new ArrayList<>();
    ArrayList<Artwork.Style> styles = new ArrayList<>();

    public CreateArtistFragment() {
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
        View v = inflater.inflate(R.layout.fragment_create_artist, container, false);


        Button openFileploadButton = (Button) v.findViewById(R.id.chooseFileButton);
        openFileploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 10);

            }
        });

        Button openGenreSelection = (Button) v.findViewById(R.id.pictureSelectGenresButton);
        openGenreSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectGenresActivity.class);
                startActivityForResult(intent, 20);

            }
        });

        Button openStyleSelection = (Button) v.findViewById(R.id.pictureSelectStylesButton);
        openStyleSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectStylesActivity.class);
                startActivityForResult(intent, 30);
            }
        });

        Button createPictureButton = (Button) v.findViewById(R.id.createPicture);
        createPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = ((EditText) getView().findViewById(R.id.pictureUploadDescription)).getText().toString();

                if (base64string == null)
                    return;

                Picture picture = new Picture(-1, base64string, description, ((Artist)MainActivity.getUser()).artist_id, genres, styles);
                try {
                    if (new uploadImageToServer().execute(picture).get()) {
                        Toast.makeText(getContext(), "Picture uploaded to server", Toast.LENGTH_LONG).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == -1) {

                    try {
                        String res = Picture.fileToBase64(data.getData(), getActivity().getContentResolver());
                        base64string = res;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

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
                    ((TextView) getView().findViewById(R.id.pictureShowGenres)).setText(str.toString());
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
                    ((TextView) getView().findViewById(R.id.pictureShowStyles)).setText(str.toString());
                }
                break;
        }
    }

    static class uploadImageToServer extends AsyncTask<Picture, Void, Boolean> {
        protected Boolean doInBackground(Picture... params) {
            try {
                API.createNewPicture((Picture) params[0]);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }


}