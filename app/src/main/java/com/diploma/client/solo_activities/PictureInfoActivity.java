package com.diploma.client.solo_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Picture;
import com.diploma.client.solo_activities.users.ArtistProfileInfoActivity;
import com.diploma.client.solo_activities.users.ClientProfileInfoActivity;

public class PictureInfoActivity extends AppCompatActivity {
    Picture picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_info);

        int pictureId = Integer.parseInt(getIntent().getStringExtra("EXTRA_PICTURE_ID"));

        this.picture = MainActivity.getPictureById(pictureId);
        ((TextView) findViewById(R.id.pictureId)).setText(Integer.toString(picture.id));
        ((TextView) findViewById(R.id.pictureDescription)).setText(picture.description);



        Button openPicture = (Button) findViewById(R.id.pictureArtistButton);
        openPicture.setText(Integer.toString(picture.artist_id));
        openPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ArtistProfileInfoActivity.class);
                intent.putExtra("OTHER_ARTIST_INFO", String.valueOf(picture.artist_id));
                startActivity(intent);
            }
        });

        ((TextView) findViewById(R.id.pictureArtistButton)).setText(Integer.toString(picture.artist_id));

        StringBuilder genres_str = new StringBuilder();
        for (Artwork.Genre genre: picture.genres)
            genres_str.append(genre.name).append("\n");

        StringBuilder styles_str = new StringBuilder();
        for (Artwork.Style style: picture.styles)
            styles_str.append(style.name).append("\n");

        ((TextView) findViewById(R.id.pictureGenres)).setText(genres_str.toString());
        ((TextView) findViewById(R.id.pictureStyles)).setText(styles_str.toString());

        ImageView image =(ImageView)findViewById(R.id.pictureImage);
        image.setImageBitmap(picture.getBitmap());

    }
}