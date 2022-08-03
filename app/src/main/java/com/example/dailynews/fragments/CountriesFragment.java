package com.example.dailynews.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dailynews.classes.Country;
import com.example.dailynews.database.LoadingData;
import com.example.dailynews.database.NewsService;
import com.example.dailynews.R;
import com.example.dailynews.adapters.LeftRVCountriesAdapter;
import com.example.dailynews.adapters.RightRVCountriesAdapter;
import com.example.dailynews.interfaces.CountryEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CountriesFragment extends Fragment {
    RecyclerView leftRecyclerView, rightRecyclerView;
    LeftRVCountriesAdapter leftRVCountriesAdapter;
    RightRVCountriesAdapter rightRVCountriesAdapter;
    ArrayList<Country> leftCountryArrayList, rightCountryArrayList;
    ProgressBar progressBar;

    public CountriesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        initializeData(view);
        fillArrayList();
        leftRecyclerView.setVisibility(View.VISIBLE);
        rightRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        setLeftRecyclerView();
        setRightRecyclerView();
        return view;
    }

    public void initializeData(View view) {
        progressBar = view.findViewById(R.id.countries_bar);
        leftRecyclerView = view.findViewById(R.id.left_rv_countries);
        rightRecyclerView = view.findViewById(R.id.right_rv_countries);
        rightCountryArrayList = new ArrayList<>();
        leftCountryArrayList = new ArrayList<>();
    }

    public void fillArrayList() {
        leftCountryArrayList.add(new Country(R.drawable.eg, "eg"));
        leftCountryArrayList.add(new Country(R.drawable.my, "my"));
        leftCountryArrayList.add(new Country(R.drawable.ph, "ph"));
        rightCountryArrayList.add(new Country(R.drawable.us, "us"));
        rightCountryArrayList.add(new Country(R.drawable.de, "de"));
        rightCountryArrayList.add(new Country(R.drawable.ie, "ie"));

    }

    public void setLeftRecyclerView() {
        leftRVCountriesAdapter = new LeftRVCountriesAdapter(leftCountryArrayList);
        leftRecyclerView.setAdapter(leftRVCountriesAdapter);
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        leftRecyclerView.setHasFixedSize(true);
        leftRecyclerView.setNestedScrollingEnabled(false);
        leftRVCountriesAdapter.setCountryLeftEventListener(new CountryEventListener() {
            @Override
            public void onClick(int pos) {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                        progressBar.setVisibility(View.GONE);

                    }
                }, 3000);
                Country country = leftCountryArrayList.get(pos);
                LoadingData.getHeadLines(country.getCountryFlag());
                LoadingData.getBussinessNews(country.getCountryFlag());
                LoadingData.getEntartinmentNews(country.getCountryFlag());
                LoadingData.getSportNews(country.getCountryFlag());
                LoadingData.getHealthList(country.getCountryFlag());
                LoadingData.getTechnology(country.getCountryFlag());
                LoadingData.getScienceNews(country.getCountryFlag());
                getActivity().stopService(new Intent(getActivity(), NewsService.class));
                getActivity().startService(new Intent(getActivity(), NewsService.class));
                rightRecyclerView.setVisibility(View.GONE);
                leftRecyclerView.setVisibility(View.GONE);
                SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences("SaveCountry", MODE_PRIVATE).edit();
                sharedPreferences.putString("country", country.getCountryFlag()).commit();


            }
        });
    }

    public void setRightRecyclerView() {
        rightRVCountriesAdapter = new RightRVCountriesAdapter(rightCountryArrayList);
        rightRecyclerView.setAdapter(rightRVCountriesAdapter);
        rightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rightRecyclerView.setHasFixedSize(true);
        rightRecyclerView.setNestedScrollingEnabled(false);
        rightRVCountriesAdapter.setCountryRightEventListener(new CountryEventListener() {
            @Override
            public void onClick(int pos) {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                        progressBar.setVisibility(View.GONE);

                    }
                }, 3000);
                Country country = rightCountryArrayList.get(pos);
                LoadingData.getHeadLines(country.getCountryFlag());
                LoadingData.getBussinessNews(country.getCountryFlag());
                LoadingData.getEntartinmentNews(country.getCountryFlag());
                LoadingData.getSportNews(country.getCountryFlag());
                LoadingData.getHealthList(country.getCountryFlag());
                LoadingData.getTechnology(country.getCountryFlag());
                LoadingData.getScienceNews(country.getCountryFlag());
                getActivity().stopService(new Intent(getActivity(), NewsService.class));
                getActivity().startService(new Intent(getActivity(), NewsService.class));
                rightRecyclerView.setVisibility(View.GONE);
                leftRecyclerView.setVisibility(View.GONE);
                SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences("SaveCountry", MODE_PRIVATE).edit();
                sharedPreferences.putString("country", country.getCountryFlag()).commit();
            }
        });
    }

}