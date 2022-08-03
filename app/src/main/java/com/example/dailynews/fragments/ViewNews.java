package com.example.dailynews.fragments;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.dailynews.R;
import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ViewNews extends Fragment {

    WebView webView;
    static String url;
    TextView textView;
    static String description;


    private String mParam1;
    private String mParam2;

    public ViewNews() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_news, container, false);
        webView = view.findViewById(R.id.web_news);
        textView = view.findViewById(R.id.description_off);
        if (haveNetwork()) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);
            textView.setVisibility(View.GONE);
        } else {
            webView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(description);
            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG).show();
        }

        return view;
    }

    public static void instance(String l) {
        url = l;
    }

    public static void getDescription(String desc) {
        description = desc;
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
}