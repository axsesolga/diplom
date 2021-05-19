package com.diploma.client.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.solo_activities.users.ArtistProfileInfoActivity;
import com.diploma.client.solo_activities.users.ArtistProfileEditActivity;
import com.diploma.client.solo_activities.users.ClientProfileInfoActivity;
import com.diploma.client.solo_activities.users.ClientProfileEditActivity;

public class ProfileSettingsFragment extends Fragment {
    public ProfileSettingsFragment() {
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_settings_view, container, false);


        Button openInfoButton = (Button) v.findViewById(R.id.openInfo);
        openInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.getUser().isArtist())
                    startActivity(new Intent(getActivity(), ArtistProfileInfoActivity.class));
                else
                    startActivity(new Intent(getActivity(), ClientProfileInfoActivity.class));
            }
        });

        Button openEditInfoButton = (Button) v.findViewById(R.id.openInfoEdit);
        openEditInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.getUser().isArtist())
                    startActivity(new Intent(getActivity(), ArtistProfileEditActivity.class));
                //else
                    //startActivity(new Intent(getActivity(), ClientProfileEditActivity.class));
            }
        });



        return v;
    }
}
