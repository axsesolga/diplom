package com.diploma.client.main_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.diploma.client.R;

public class HomeFragment extends Fragment {

    HomeFragment _this;

    public HomeFragment() {
        // Required empty public constructor
    }

    Fragment homeSelectedFragment;

    HomeFragmentPictures homePicturesFragment = new HomeFragmentPictures();
    HomeFragmentAdverts homeAdvertsFragment = new HomeFragmentAdverts();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    View v;

    void init(LayoutInflater inflater, ViewGroup container) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        getFragmentManager().beginTransaction().replace(R.id.home_fragment_container, homePicturesFragment).commit();

        Button picturesButton = (Button) v.findViewById(R.id.homeButtonPictures);
        picturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeSelectedFragment = homePicturesFragment;
                getFragmentManager().beginTransaction().replace(R.id.home_fragment_container, homeSelectedFragment).commit();
            }
        });


        Button advertsButton = (Button) v.findViewById(R.id.homeButtonAdverts);
        advertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeSelectedFragment = homeAdvertsFragment;
                getFragmentManager().beginTransaction().replace(R.id.home_fragment_container, homeSelectedFragment).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (v == null)
            init(inflater, container);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homeSelectedFragment != null)
            getFragmentManager().beginTransaction().replace(R.id.home_fragment_container, homeSelectedFragment).commit();
        else
            getFragmentManager().beginTransaction().replace(R.id.home_fragment_container, homePicturesFragment).commit();
    }


}