package com.example.dailynews.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynews.classes.Country;
import com.example.dailynews.R;
import com.example.dailynews.interfaces.CountryEventListener;

import java.util.List;

public class RightRVCountriesAdapter extends RecyclerView.Adapter<RightRVCountriesAdapter.CountriesRightViewHolder> {
    List<Country> rightCountriesList;
    CountryEventListener countryRightEventListener;

    public RightRVCountriesAdapter(List<Country> rightCountriesList) {
        this.rightCountriesList = rightCountriesList;
    }

    @NonNull
    @Override
    public CountriesRightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countries_shape_rv_raw, parent, false);
        return new CountriesRightViewHolder(view, countryRightEventListener);
    }

    public void setCountryRightEventListener(CountryEventListener countryRightEventListener) {
        this.countryRightEventListener = countryRightEventListener;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesRightViewHolder holder, int position) {
        Country country = rightCountriesList.get(position);
        holder.imageView.setImageResource(country.getFlagImage());
    }

    @Override
    public int getItemCount() {
        return rightCountriesList.size();
    }

    public static class CountriesRightViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CountriesRightViewHolder(@NonNull View itemView, CountryEventListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.country_image_flag);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            listener.onClick(pos);
                        }
                    }
                }
            });

        }
    }
}
