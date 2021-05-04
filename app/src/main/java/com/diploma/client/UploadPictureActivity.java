package com.diploma.client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Picture;
import com.diploma.client.network.API;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UploadPictureActivity extends AppCompatActivity {

    String base64string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);

        final EditText artistIdEditText = findViewById(R.id.pictureUploadArtistId);
        final EditText nameEditText = findViewById(R.id.registerName);


        Button openFileploadButton = (Button) findViewById(R.id.chooseFileButton);
        openFileploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 10);
            }
        });

        Button createPictureButton = (Button) findViewById(R.id.createPicture);
        createPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String artistId = ((EditText) findViewById(R.id.pictureUploadArtistId)).getText().toString();
                String description = ((EditText) findViewById(R.id.pictureUploadDescription)).getText().toString();
                String genreId = ((EditText) findViewById(R.id.pictureUploadGenres)).getText().toString();
                String styleId = ((EditText) findViewById(R.id.pictureUploadStyles)).getText().toString();

                ArrayList<Artwork.Genre> genres = new ArrayList<>();
                genres.add(Artwork.Genre.getById(Integer.parseInt(genreId)));


                ArrayList<Artwork.Style> styles = new ArrayList<>();
                styles.add(Artwork.Style.getById(Integer.parseInt(styleId)));

                if (base64string == null)
                    return;

                Picture picture = new Picture(-1, base64string, description, Integer.parseInt(artistId), genres, styles);
                try {
                    API.createNewPicture(picture, getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {

                    try {
                        String res = Picture.fileToBase64(data.getData(), getContentResolver());
                        ((TextView) findViewById(R.id.filePath)).setText(res);
                        base64string = res;
                    } catch (IOException e) {
                        e.printStackTrace();
                        ((TextView) findViewById(R.id.filePath)).setText(e.getMessage());
                    }
                }
                break;
        }
    }


}