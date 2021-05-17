package com.diploma.client.solo_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Picture;

public class PictureInfoActivity extends AppCompatActivity {
    Picture picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_info);

        int pictureId = getIntent().getIntExtra("EXTRA_PICTURE_ID", 0);
        this.picture = MainActivity.pictures.get(pictureId); // make get by id
        ((TextView) findViewById(R.id.pictureId)).setText(Integer.toString(picture.id));
        ((TextView) findViewById(R.id.pictureUrl)).setText(picture.base64string);
        ((TextView) findViewById(R.id.pictureDescription)).setText(picture.description);
        ((TextView) findViewById(R.id.pictureArtistId)).setText(Integer.toString(picture.artist_id));
        ((TextView) findViewById(R.id.pictureGenres)).setText(Integer.toString(picture.genres.size()));
        ((TextView) findViewById(R.id.pictureStyles)).setText(Integer.toString(picture.styles.size()));

        ImageView image =(ImageView)findViewById(R.id.pictureImage);
        image.setImageBitmap(picture.getBitmap());

    }
}