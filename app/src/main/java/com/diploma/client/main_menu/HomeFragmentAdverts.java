package com.diploma.client.main_menu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.client.CustomTouchListener;
import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model_edit.SelectGenresActivity;
import com.diploma.client.data.model_edit.SelectStylesActivity;
import com.diploma.client.solo_activities.AdvertInfoActivity;
import com.diploma.client.solo_activities.chat.ChatListUsersListAdapter;

import java.util.ArrayList;


public class HomeFragmentAdverts extends Fragment {
    ArrayList<Advert> displayed_adverts;
    ArrayList<Advert> all_adverts;


    EditText des_value_text_min;
    EditText des_value_text_max;
    ArrayList<Artwork.Genre> genre_filters = new ArrayList<>();
    ArrayList<Artwork.Style> style_filters = new ArrayList<>();

    public HomeFragmentAdverts() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void filter() {
        adapter.setFilterValues(genre_filters, style_filters,
                des_value_text_min.getText().toString(),
                des_value_text_max.getText().toString()
        );
        adapter.getFilter().filter(null);
        adapter.notifyDataSetChanged();
    }

    private RecyclerView rv_AdvertList;
    HomeFragmentAdvertsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_adverts, container, false);
        displayed_adverts = new ArrayList<>(MainActivity.adverts);
        all_adverts = MainActivity.adverts;

        //buttons

        des_value_text_min = (EditText) v.findViewById(R.id.searchAdversValueMin);
        des_value_text_min.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter();
            }
        });
        des_value_text_max = (EditText) v.findViewById(R.id.searchAdversValueMax);
        des_value_text_max.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter();
            }
        });

        Button genresSelectButton = (Button) v.findViewById(R.id.searchAdversGenres);
        genresSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), SelectGenresActivity.class), 20);

            }
        });

        Button stylesSelectButton = (Button) v.findViewById(R.id.searchAdversStyles);
        stylesSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(v.getContext(), SelectStylesActivity.class), 30);

            }
        });


        //buttons
        rv_AdvertList = v.findViewById(R.id.rv_adverts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_AdvertList.setLayoutManager(layoutManager);
        adapter = new HomeFragmentAdvertsAdapter(displayed_adverts);
        rv_AdvertList.setAdapter(adapter);


        rv_AdvertList.addOnItemTouchListener(new CustomTouchListener(getContext(), new ChatListUsersListAdapter.onItemClickListener() {
            // При нажатии на элемент списка устанавливается текущий порядок и размер проигрывания
            @Override
            public void onClick(View view, int index) {
                Intent intent = new Intent(getContext(), AdvertInfoActivity.class);
                intent.putExtra("EXTRA_ADVERT_ID", String.valueOf(displayed_adverts.get(index).id));
                startActivity(intent);
            }
        }));

        return v;
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
                    for (String str_id : res.split(",")) {
                        int id = Integer.parseInt(str_id);
                        Artwork.Genre genre = Artwork.Genre.getById(id);
                        genre_filters.add(genre);
                        str.append(genre.name);
                        str.append(",");
                    }
                    filter();
                    ((TextView) getView().findViewById(R.id.advertSearchShowGenres)).setText(str.toString());
                }
                break;

            case 30: // styles selected
                if (resultCode == -1) {
                    style_filters = new ArrayList<>();
                    StringBuilder str = new StringBuilder();
                    str.append("Стили: ");
                    String res = data.getExtras().getString("STYLES"); // ids sep by comma
                    for (String str_id : res.split(",")) {
                        int id = Integer.parseInt(str_id);
                        Artwork.Style style = Artwork.Style.getById(id);
                        style_filters.add(style);
                        str.append(style.name);
                        str.append(",");
                    }
                    filter();
                    ((TextView) getView().findViewById(R.id.advertSearchShowStyles)).setText(str.toString());
                }
                break;
        }
    }
}