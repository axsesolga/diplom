package com.diploma.client.solo_activities.users;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Artist;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Client;
import com.diploma.client.data.model_edit.SelectGenresActivity;
import com.diploma.client.data.model_edit.SelectStylesActivity;
import com.diploma.client.network.API;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ArtistProfileEditActivity extends AppCompatActivity {
    Artist artist = (Artist) MainActivity.getUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_edit_info);

        EditText nicknameEdit = (EditText) findViewById(R.id.artistProfileNicknameEdit);
        nicknameEdit.setText(artist.nickname);

        EditText vkEdit = (EditText) findViewById(R.id.artistProfileVKurlEdit);
        vkEdit.setText(artist.VkUrl);

        EditText asEdit = (EditText) findViewById(R.id.artistProfileASurlEdit);
        asEdit.setText(artist.ArtStationUrl);

        EditText otherEdit = (EditText) findViewById(R.id.artistProfileOtherUrlEdit);
        otherEdit.setText(artist.OtherUrl);

        CheckBox availblekEdit = (CheckBox) findViewById(R.id.artistProfileAvailableEdit);
        availblekEdit.setChecked(artist.available);

        Button genresSelectButton = (Button) findViewById(R.id.artistProfileGenresEdit);
        genresSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), SelectGenresActivity.class), 20);
            }
        });

        Button stylesSelectButton = (Button) findViewById(R.id.artistProfileStylesEdit);
        stylesSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), SelectStylesActivity.class), 30);
            }
        });

        EditText exclEdit = (EditText) findViewById(R.id.artistProfileExclusionsEdit);
        exclEdit.setText(artist.exclusions);



        Button save = (Button) findViewById(R.id.artistConfirmEdit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    artist.nickname = nicknameEdit.getText().toString();
                    artist.VkUrl = vkEdit.getText().toString();
                    artist.ArtStationUrl = asEdit.getText().toString();
                    artist.OtherUrl = otherEdit.getText().toString();
                    artist.available = availblekEdit.isChecked();
                    artist.exclusions = exclEdit.getText().toString();

                    new SaveClient().execute(artist).get();

                    MainActivity.updateUser();
                    Toast.makeText(getApplicationContext(), "Изменения сохранены.", Toast.LENGTH_LONG).show();


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "eror", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    static class SaveClient extends AsyncTask<Artist, Void, Void> {
        protected Void doInBackground(Artist... params) {
            try {
                API.updateArtist(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 20: // genres selected
                if (resultCode == -1) {
                    artist.genres = new ArrayList<>();
                    StringBuilder str = new StringBuilder();
                    String res = data.getExtras().getString("GENRES"); // ids sep by comma
                    for (String str_id : res.split(",")) {
                        int id = Integer.parseInt(str_id);
                        Artwork.Genre genre = Artwork.Genre.getById(id);
                        artist.genres.add(genre);
                        str.append(genre.name);
                        str.append(",");
                    }
                    ((TextView) findViewById(R.id.artistProfileGenresEditView)).setText(str.toString());
                }
                break;

            case 30: // styles selected
                if (resultCode == -1) {
                    artist.styles = new ArrayList<>();
                    StringBuilder str = new StringBuilder();
                    String res = data.getExtras().getString("STYLES"); // ids sep by comma
                    for (String str_id : res.split(",")) {
                        int id = Integer.parseInt(str_id);
                        Artwork.Style style = Artwork.Style.getById(id);
                        artist.styles.add(style);
                        str.append(style.name);
                        str.append(",");
                    }
                    ((TextView) findViewById(R.id.artistProfileStylesEditView)).setText(str.toString());
                }
                break;
        }
    }
}