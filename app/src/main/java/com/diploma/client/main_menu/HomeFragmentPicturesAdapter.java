package com.diploma.client.main_menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Picture;

import java.util.ArrayList;

public class HomeFragmentPicturesAdapter extends RecyclerView.Adapter<HomeFragmentPicturesAdapter.PictureViewHolder> implements Filterable {
    ArrayList<Picture> displayed_pictures;

    public HomeFragmentPicturesAdapter(ArrayList<Picture> pictures) {
        this.displayed_pictures = pictures;
    }

    @NonNull
    @Override
    public HomeFragmentPicturesAdapter.PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.home_picture_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        int width = parent.getMeasuredWidth() / 2;
        view.setMinimumWidth(width);

        return new HomeFragmentPicturesAdapter.PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentPicturesAdapter.PictureViewHolder holder, int position) {
        holder.bind(displayed_pictures.get(position));
    }


    @Override
    public int getItemCount() {
        return displayed_pictures.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    ArrayList<Artwork.Genre> genres = null;
    ArrayList<Artwork.Style> styles = null;

    public void setFilterValues(ArrayList<Artwork.Genre> genres, ArrayList<Artwork.Style> styles) {
        if (genres == null || genres.size() == 0)
            this.genres = null;
        else
            this.genres = genres;

        if (styles == null || styles.size() == 0)
            this.styles = null;
        else
            this.styles = styles;
    }


    private boolean checkFilters(Picture picture) {


        if (!MainActivity.getArtistByArtistId(picture.artist_id).available)
            return false;

        if (genres != null) {
            for (Artwork.Genre _genre : genres) {
                boolean found = false;
                for (Artwork.Genre genre : picture.genres)
                    if (genre.id == _genre.id) {
                        found = true;
                        break;
                    }
                if (!found)
                    return false;
            }
        }

        // 2
        if (styles != null) {
            for (Artwork.Style _style : styles) {
                boolean found = false;
                for (Artwork.Style style : picture.styles)
                    if (style.id == _style.id) {
                        found = true;
                        break;
                    }
                if (!found)
                    return false;
            }
        }

        return true;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Picture> newList = new ArrayList<>();
            for (Picture item : MainActivity.pictures) {
                if (checkFilters(item)) {
                    newList.add(item);
                }
            }


            FilterResults results = new FilterResults();
            results.values = newList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            displayed_pictures.clear();
            displayed_pictures = (ArrayList<Picture>) results.values;
            notifyDataSetChanged();
        }
    };

    class PictureViewHolder extends RecyclerView.ViewHolder {
        Picture picture;
        ImageView imageView;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.soloPictureImage);
        }

        void bind(Picture picture) {
            this.picture = picture;
            Bitmap original_picture = picture.getBitmap();


            Bitmap resized_image = original_picture;
            imageView.setImageBitmap(resized_image);
        }
    }
}
