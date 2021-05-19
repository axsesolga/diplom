package com.diploma.client.main_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.diploma.client.CustomTouchListener;
import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Picture;
import com.diploma.client.data.model_edit.SelectGenresActivity;
import com.diploma.client.data.model_edit.SelectStylesActivity;
import com.diploma.client.solo_activities.PictureInfoActivity;
import com.diploma.client.solo_activities.chat.ChatListUsersListAdapter;

import java.util.ArrayList;


public class HomeFragmentPictures extends Fragment {
    ArrayList<Picture> displayedPictures;
    ArrayList<Picture> all_pictures;

    ArrayList<Artwork.Genre> genre_filters = new ArrayList<>();
    ArrayList<Artwork.Style> style_filters = new ArrayList<>();

    public HomeFragmentPictures() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    void filter() {
        adapter.setFilterValues(genre_filters, style_filters);
        adapter.getFilter().filter(null);
        adapter.notifyDataSetChanged();
    }

    private RecyclerView rv_PictureList;
    HomeFragmentPicturesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_pictures, container, false);
        displayedPictures = new ArrayList<>(MainActivity.pictures);
        all_pictures = MainActivity.pictures;


        // filter buttons

        Button genresSelectButton = (Button) v.findViewById(R.id.searchPicturesGenres);
        genresSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), SelectGenresActivity.class), 20);

            }
        });

        Button stylesSelectButton = (Button) v.findViewById(R.id.searchPicturesStyles);
        stylesSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), SelectStylesActivity.class), 30);

            }
        });


        //buttons
        adapter = new HomeFragmentPicturesAdapter(displayedPictures);

        rv_PictureList = v.findViewById(R.id.rv_pictures);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        rv_PictureList.setLayoutManager(layoutManager);
        rv_PictureList.setAdapter(adapter);


        rv_PictureList.addOnItemTouchListener(new CustomTouchListener(getContext(), new ChatListUsersListAdapter.onItemClickListener() {
            // При нажатии на элемент списка устанавливается текущий порядок и размер проигрывания
            @Override
            public void onClick(View view, int index) {
                Intent intent = new Intent(getContext(), PictureInfoActivity.class);
                intent.putExtra("EXTRA_PICTURE_ID", String.valueOf(adapter.displayed_pictures.get(index).id));
                startActivity(intent);
            }
        }));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.updateAdvertsPictures();
        adapter.notifyDataSetChanged();
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 20: // genres selected
                if (resultCode == -1) {
                    genre_filters = new ArrayList<>();
                    StringBuilder str = new StringBuilder();
                    str.append("Жанры: ");
                    String res = data.getExtras().getString("GENRES"); // ids sep by comma
                    if (res.isEmpty())
                        return;
                    for (String str_id : res.split(",")) {
                        int id = Integer.parseInt(str_id);
                        Artwork.Genre genre = Artwork.Genre.getById(id);
                        genre_filters.add(genre);
                        str.append(genre.name);
                        str.append(",");
                    }
                    filter();
                    ((TextView) getView().findViewById(R.id.pictureSearchShowGenres)).setText(str.toString());
                }
                break;

            case 30: // styles selected
                if (resultCode == -1) {
                    style_filters = new ArrayList<>();
                    StringBuilder str = new StringBuilder();
                    str.append("Стили: ");
                    String res = data.getExtras().getString("STYLES"); // ids sep by comma
                    if (res.isEmpty())
                        return;
                    for (String str_id : res.split(",")) {
                        int id = Integer.parseInt(str_id);
                        Artwork.Style style = Artwork.Style.getById(id);
                        style_filters.add(style);
                        str.append(style.name);
                        str.append(",");
                    }
                    filter();
                    ((TextView) getView().findViewById(R.id.pictureSearchShowStyles)).setText(str.toString());
                }
                break;
        }
    }

}