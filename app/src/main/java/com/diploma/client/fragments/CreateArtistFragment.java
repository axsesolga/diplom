package com.diploma.client.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diploma.client.R;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Picture;
import com.diploma.client.network.API;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateArtistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateArtistFragment extends Fragment {
    String base64string;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateArtistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateArtistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateArtistFragment newInstance(String param1, String param2) {
        CreateArtistFragment fragment = new CreateArtistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        Button createPictureButton = (Button) v.findViewById(R.id.createPicture);
        createPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String artistId = ((EditText) getView().findViewById(R.id.pictureUploadArtistId)).getText().toString();
                String description = ((EditText) getView().findViewById(R.id.pictureUploadDescription)).getText().toString();
                String genreId = ((EditText) getView().findViewById(R.id.pictureUploadGenres)).getText().toString();
                String styleId = ((EditText) getView().findViewById(R.id.pictureUploadStyles)).getText().toString();

                ArrayList<Artwork.Genre> genres = new ArrayList<>();
                genres.add(Artwork.Genre.getById(Integer.parseInt(genreId)));


                ArrayList<Artwork.Style> styles = new ArrayList<>();
                styles.add(Artwork.Style.getById(Integer.parseInt(styleId)));

                if (base64string == null)
                    return;

                Picture picture = new Picture(-1, base64string, description, Integer.parseInt(artistId), genres, styles);
                try {
                    if (new uploadImageToServer().execute(picture).get()) {
                        //nothing yet
                        String ok = "ok";
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
                        ((TextView) getView().findViewById(R.id.filePath)).setText(res);
                        base64string = res;
                    } catch (IOException e) {
                        e.printStackTrace();
                        ((TextView)  getView().findViewById(R.id.filePath)).setText(e.getMessage());
                    }
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