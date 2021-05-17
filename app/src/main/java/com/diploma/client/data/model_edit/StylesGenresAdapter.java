package com.diploma.client.data.model_edit;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.User;

import java.util.ArrayList;


public class StylesGenresAdapter extends RecyclerView.Adapter<StylesGenresAdapter.ViewHolder>
{
    ArrayList<Artwork.ArtworkProperties> styles;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return styles.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        Artwork.ArtworkProperties ap;

        TextView gs_Name; // nickname
        TextView gs_Description; // user type
        CheckBox gs_CheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gs_Name = itemView.findViewById(R.id.gs_Name);
            gs_Description = itemView.findViewById(R.id.gs_Description);
            gs_CheckBox = itemView.findViewById(R.id.gs_CheckBox);

        }

        void bind(Artwork.ArtworkProperties ap) {
            this.ap = ap;
            gs_Name.setText(ap.name);
            gs_Description.setText(ap.description);
            gs_CheckBox.setChecked(false);
        }
    }
}
