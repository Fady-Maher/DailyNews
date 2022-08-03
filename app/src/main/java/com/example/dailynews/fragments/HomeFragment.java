package com.example.dailynews.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dailynews.database.LoadingData;
import com.example.dailynews.interfaces.HomeFragmentListener;
import com.example.dailynews.classes.News;
import com.example.dailynews.database.NewsDB;
import com.example.dailynews.classes.NewsOffLine;
import com.example.dailynews.interfaces.OnClickListenerItem;
import com.example.dailynews.interfaces.OnClickOffListenerItem;
import com.example.dailynews.R;
import com.example.dailynews.adapters.RecycleNewsAdapter;
import com.example.dailynews.adapters.RecycleOffNewsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import static android.content.Context.CONNECTIVITY_SERVICE;

public class HomeFragment extends Fragment implements OnClickListenerItem, OnClickOffListenerItem {
    static ArrayList<News> newsArrayList;
    static RecycleNewsAdapter recycleNewsAdapter = null;
    RecyclerView recyclerView = null;
    static ArrayList<NewsOffLine> dataBaseArrayList;
    private static String nameOfType = "";
    ProgressBar progressBar;
    HomeFragmentListener homeFragmentListener;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String id;
    NewsDB newsDB = null;
    static ArrayList<NewsOffLine> newsOffLineArrayList;
    static RecycleOffNewsAdapter recycleOffNewsAdapter = null;
    boolean check = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentListener) {
            homeFragmentListener = (HomeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchFragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homeFragmentListener = null;
    }


    public static void filter(String item) {
        ArrayList<News> NSearch = new ArrayList<>();
        for (News n : newsArrayList) {
            if (n.getTitle_news().toLowerCase().contains(item.toLowerCase())) {
                NSearch.add(n);
            }
        }
        recycleNewsAdapter.filterList(NSearch);
    }

    public static void filterOff(String item) {
        ArrayList<NewsOffLine> NSearch = new ArrayList<>();
        for (NewsOffLine n : newsOffLineArrayList) {
            if (n.getTitle_news().toLowerCase().contains(item.toLowerCase())) {
                NSearch.add(n);
            }
        }
        recycleOffNewsAdapter.filterList(NSearch);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("news").child(mAuth.getCurrentUser().getUid());
        newsDB = new NewsDB(getContext());
        newsOffLineArrayList = new ArrayList<>();
        newsArrayList = new ArrayList<>();
        dataBaseArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newsArrayList.clear();
        progressBar = view.findViewById(R.id.pro_bar);
        recyclerView = view.findViewById(R.id.rv_news);
        recycleNewsAdapter = new RecycleNewsAdapter(newsArrayList, getActivity(), this);
        recycleOffNewsAdapter = new RecycleOffNewsAdapter(newsOffLineArrayList, getActivity(), this);

        if (nameOfType.equals("")) {
            if (haveNetwork()) {
                newsArrayList.clear();
                newsArrayList.addAll(LoadingData.headLinesList);
                recycleNewsAdapter.notifyDataSetChanged();
            } else {
                newsOffLineArrayList.clear();
                newsOffLineArrayList.addAll(newsDB.getAllHeadline());
                recycleOffNewsAdapter.notifyDataSetChanged();
            }

        } else if (nameOfType.equals("business")) {
            if (haveNetwork()) {
                newsArrayList.clear();
                newsArrayList.addAll(LoadingData.bussinessList);
                recycleNewsAdapter.notifyDataSetChanged();
            } else {
                newsOffLineArrayList.clear();
                newsOffLineArrayList.addAll(newsDB.getAllBusiness());
                recycleOffNewsAdapter.notifyDataSetChanged();
            }


        } else if (nameOfType.equals("sports")) {
            if (haveNetwork()) {
                newsArrayList.clear();
                newsArrayList.addAll(LoadingData.sportsList);
                recycleNewsAdapter.notifyDataSetChanged();
            } else {
                newsOffLineArrayList.clear();
                newsOffLineArrayList.addAll(newsDB.getAllSports());
                recycleOffNewsAdapter.notifyDataSetChanged();
            }

        } else if (nameOfType.equals("entertainment")) {
            if (haveNetwork()) {
                newsArrayList.clear();
                newsArrayList.addAll(LoadingData.entartinmentList);
                recycleNewsAdapter.notifyDataSetChanged();
            } else {
                newsOffLineArrayList.clear();
                newsOffLineArrayList.addAll(newsDB.getAllEntertainment());
                recycleOffNewsAdapter.notifyDataSetChanged();
            }

        } else if (nameOfType.equals("health")) {
            if (haveNetwork()) {
                newsArrayList.clear();
                newsArrayList.addAll(LoadingData.healthList);
                recycleNewsAdapter.notifyDataSetChanged();
            } else {
                newsOffLineArrayList.clear();
                newsOffLineArrayList.addAll(newsDB.getAllHealth());
                recycleOffNewsAdapter.notifyDataSetChanged();
            }

        } else if (nameOfType.equals("science")) {
            if (haveNetwork()) {
                newsArrayList.clear();
                newsArrayList.addAll(LoadingData.scienceList);
                recycleNewsAdapter.notifyDataSetChanged();
            } else {
                newsOffLineArrayList.clear();
                newsOffLineArrayList.addAll(newsDB.getAllScience());
                recycleOffNewsAdapter.notifyDataSetChanged();
            }

        } else if (nameOfType.equals("technology")) {
            if (haveNetwork()) {
                newsArrayList.clear();
                newsArrayList.addAll(LoadingData.technologyList);
                recycleNewsAdapter.notifyDataSetChanged();
            } else {
                newsOffLineArrayList.clear();
                newsOffLineArrayList.addAll(newsDB.getAllTechnology());
                recycleOffNewsAdapter.notifyDataSetChanged();
            }

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if (haveNetwork()) {
            recyclerView.setAdapter(recycleNewsAdapter);
            recyclerView.setHasFixedSize(true);
        } else {
            recyclerView.setAdapter(recycleOffNewsAdapter);
            recyclerView.setHasFixedSize(true);
        }


        return view;
    }


    public static void upadteData(String topic) {
        nameOfType = topic;
    }

    @Override
    public void onClick(News news) {
        homeFragmentListener.sendUrl(news.getNewsUrl());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ViewNews()).commit();
    }

    @Override
    public void share(News news) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, news.getNewsUrl());
        intent.setType("text/plain");
        Intent intent1 = Intent.createChooser(intent, null);
        startActivity(intent1);
    }

    @Override
    public void like(News news) {
        checkFavNews(news);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (check) {
                    Snackbar.make(getView(), "Added Before...!", Snackbar.LENGTH_LONG).show();
                } else {
                    id = mDatabase.push().getKey();
                    news.setId(id);
                    mDatabase.child(id).setValue(news).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Added To Favourite", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        }, 1000);


    }

    void checkFavNews(News news) {
        check = false;
        mDatabase = FirebaseDatabase.getInstance().getReference("news").child(mAuth.getCurrentUser().getUid()).child("favourite");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    News n = dataSnapshot.getValue(News.class);
                    if (n.getTitle_news().equals(news.getTitle_news())) {
                        check = true;
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected()) have_WIFI = true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))
                if (info.isConnected()) have_MobileData = true;
        }
        return have_WIFI || have_MobileData;
    }

    @Override
    public void onClick(NewsOffLine news) {
        homeFragmentListener.sendDescription(news.getDescription());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ViewNews()).commit();

    }
}