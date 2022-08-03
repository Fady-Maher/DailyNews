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

public class LeftRVCountriesAdapter extends RecyclerView.Adapter<LeftRVCountriesAdapter.CountriesViewHolder> {
    List<Country> leftCountryList;
    CountryEventListener countryLeftEventListener;

    public LeftRVCountriesAdapter(List<Country> leftCountryList) {
        this.leftCountryList = leftCountryList;
    }

    public void setCountryLeftEventListener(CountryEventListener countryLeftEventListener) {
        this.countryLeftEventListener = countryLeftEventListener;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countries_shape_rv_raw, parent, false);
        return new CountriesViewHolder(view, countryLeftEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        Country country = leftCountryList.get(position);
        holder.imageView.setImageResource(country.getFlagImage());
    }

    @Override
    public int getItemCount() {
        return leftCountryList.size();
    }


    public static class CountriesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CountriesViewHolder(@NonNull View itemView, CountryEventListener listener) {
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
