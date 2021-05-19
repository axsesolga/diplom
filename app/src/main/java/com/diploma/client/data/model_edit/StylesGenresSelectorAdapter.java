package com.diploma.client.data.model_edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.client.R;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.solo_activities.chat.ChatListUsersListAdapter;

import java.util.ArrayList;


public class StylesGenresSelectorAdapter extends RecyclerView.Adapter<StylesGenresSelectorAdapter.ViewHolder>
{
    public ArrayList<Artwork.ArtworkProperties> sg_items;
    public boolean[] checked;

    public StylesGenresSelectorAdapter(ArrayList<? extends Artwork.ArtworkProperties> sg) {
        this.sg_items = (ArrayList<Artwork.ArtworkProperties>) sg;
        checked = new boolean[sg_items.size()];
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.genre_style_rv_card;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new StylesGenresSelectorAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(sg_items.get(position));

        holder.gs_CheckBox.setChecked(checked[position]);
        holder.gs_CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[position] = !checked[position];
            }
        });
    }

    @Override
    public int getItemCount() {
        return sg_items.size();
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
