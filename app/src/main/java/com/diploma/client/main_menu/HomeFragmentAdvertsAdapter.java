package com.diploma.client.main_menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diploma.client.MainActivity;
import com.diploma.client.R;
import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artwork;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class HomeFragmentAdvertsAdapter extends RecyclerView.Adapter<HomeFragmentAdvertsAdapter.AdvertViewHolder> implements Filterable {
    ArrayList<Advert> displayed_adverts;

    public HomeFragmentAdvertsAdapter(ArrayList<Advert> adverts) {
        this.displayed_adverts = adverts;
    }

    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.home_advert_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new AdvertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder holder, int position) {
        holder.bind(displayed_adverts.get(position));
    }

    @Override
    public int getItemCount() {
        return displayed_adverts.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    ArrayList<Artwork.Genre> genres = null;
    ArrayList<Artwork.Style> styles = null;
    int min_value;
    int max_value = 10000000;

    public void setFilterValues(ArrayList<Artwork.Genre> genres, ArrayList<Artwork.Style> styles, String min_value, String max_value) {
        if (genres == null || genres.size() == 0)
            this.genres = null;
        else
            this.genres = genres;

        if (styles == null || styles.size() == 0)
            this.styles = null;
        else
            this.styles = styles;

        try {
            this.min_value = Integer.parseInt(min_value);
        } catch (Exception e) {
            this.min_value = 0;
        }
        try {
            this.max_value = Integer.parseInt(max_value);
        } catch (Exception e) {
            this.max_value = 10000000;
        }

        if (this.min_value > this.max_value)
            throw new InvalidParameterException("Wrong desired price range.");

    }


    private boolean checkFilters(Advert advert) {
        int return_value = 0;

        // 1
        if (genres != null) {
            for (Artwork.Genre genre : advert.genres)
                for (Artwork.Genre _genre : genres)
                    if (genre.id == _genre.id)
                        return_value += 1;
        } else return_value += 1;

        // 2
        if (styles != null) {
            for (Artwork.Style style : advert.styles)
                for (Artwork.Style _style : styles)
                    if (style.id == _style.id)
                        return_value += 1;
        } else return_value += 1; // если нет выбранных стилей то все подойдут
        //3
        if (advert.desired_value >= min_value && advert.desired_value <= max_value)
            return_value += 1;

        return return_value == 3;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Advert> newList = new ArrayList<>();
            for (Advert item : MainActivity.adverts) {
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
            displayed_adverts.clear();
            displayed_adverts = (ArrayList<Advert>) results.values;
            notifyDataSetChanged();
        }
    };

    class AdvertViewHolder extends RecyclerView.ViewHolder {

        Advert advert;
        TextView advertCardTitleTextView; // nickname
        TextView advertCardValueTextView; // user type
        TextView advertCardDescriptionTextView; // Client

        public AdvertViewHolder(@NonNull View itemView) {
            super(itemView);

            advertCardTitleTextView = itemView.findViewById(R.id.advertCardTitle);
            advertCardValueTextView = itemView.findViewById(R.id.advertCardValue);
            advertCardDescriptionTextView = itemView.findViewById(R.id.advertCardDescription);
        }

        void bind(Advert advert) {
            this.advert = advert;
            advertCardTitleTextView.setText(advert.title);
            advertCardValueTextView.setText(String.valueOf(advert.desired_value));
            advertCardDescriptionTextView.setText(advert.description);
        }
    }
}
