package com.example.dailynews.database;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.dailynews.classes.NewsOffLine;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class NewsService extends Service {
    ArrayList<NewsOffLine> newsOffLineHeadlineArrayList = new ArrayList<>();
    ArrayList<NewsOffLine> newsOffLineSportsArrayList = new ArrayList<>();
    ArrayList<NewsOffLine> newsOffLineScienceArrayList = new ArrayList<>();
    ArrayList<NewsOffLine> newsOffLineBussinessArrayList = new ArrayList<>();
    ArrayList<NewsOffLine> newsOffLineHealthArrayList = new ArrayList<>();
    ArrayList<NewsOffLine> newsOffLineEntartinmentArrayList = new ArrayList<>();
    ArrayList<NewsOffLine> newsOffLineTechnologyArrayList = new ArrayList<>();

    NewsDB newsDB;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        newsOffLineHeadlineArrayList = LoadingData.dataBaseHeadLineOffline;
        newsOffLineBussinessArrayList = LoadingData.dataBaseBussinessOffline;
        newsOffLineEntartinmentArrayList = LoadingData.dataBaseEntartinmentOffline;
        newsOffLineScienceArrayList = LoadingData.dataBaseScienceOffline;
        newsOffLineSportsArrayList = LoadingData.dataBaseSportOffline;
        newsOffLineHealthArrayList = LoadingData.dataBaseHealthOffline;
        newsOffLineTechnologyArrayList = LoadingData.dataBaseTechnologyOffline;
    }
    @Override
    public void onStart(Intent intent, int startid) {
        setHeadlineNewsOff(getApplicationContext());
        setHealthNewsOff(getApplicationContext());
        setBussinessNewsOff(getApplicationContext());
        setEntartinmentNewsOff(getApplicationContext());
        setScienceNewsOff(getApplicationContext());
        setSportsNewsOff(getApplicationContext());
        setTechnologyNewsOff(getApplicationContext());

    }
    @Override
    public void onDestroy() {
    }
    public void setHeadlineNewsOff(Context context){
        newsDB = new NewsDB(context);
        newsDB.deleteHead();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<20;i++){
                    String img_news1 = LoadingData.headLinesList.get(i).getImg_news();
                    String img_source1 = LoadingData.headLinesList.get(i).getImg_source();
                    int finalI = i;
                    RequestOptions requestOptions = new RequestOptions().override(350,150).fitCenter();
                    CustomTarget<Bitmap> theBitmap = Glide.with(context).asBitmap().apply(requestOptions).load(img_news1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineHeadlineArrayList.get(finalI).setImg_news(getBytesImg(resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
                    CustomTarget<Bitmap> theBitmap1 = Glide.with(context).asBitmap().load(img_source1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineHeadlineArrayList.get(finalI).setImg_source(getBytesImg(resource));

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for(int i =0; i<20;i++){
                            if(newsOffLineHeadlineArrayList.get(i).getImg_source() == null){
                                continue;

                            }
                            if(newsOffLineHeadlineArrayList.get(i).getImg_news() == null){
                                continue;
                            }

                            newsDB.insertHeadline(newsOffLineHeadlineArrayList.get(i));

                        }

                    }
                },5000);

            }
        },5000);
    }
    public void setSportsNewsOff(Context context){
        newsDB = new NewsDB(context);
        newsDB.deleteSpo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<20;i++){

                    String img_news1 = LoadingData.sportsList.get(i).getImg_news();
                    String img_source1 = LoadingData.sportsList.get(i).getImg_source();
                    int finalI = i;
                    RequestOptions requestOptions = new RequestOptions().override(350,150).fitCenter();
                    CustomTarget<Bitmap> theBitmap = Glide.with(context).asBitmap().apply(requestOptions).load(img_news1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineSportsArrayList.get(finalI).setImg_news(getBytesImg(resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
                    CustomTarget<Bitmap> theBitmap1 = Glide.with(context).asBitmap().load(img_source1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineSportsArrayList.get(finalI).setImg_source(getBytesImg(resource));

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for(int i =0; i<20;i++){
                            if(newsOffLineSportsArrayList.get(i).getImg_source() == null){
                                continue;

                            }
                            if(newsOffLineSportsArrayList.get(i).getImg_news() == null){
                                continue;
                            }

                            newsDB.insertSports(newsOffLineSportsArrayList.get(i));

                        }

                    }
                },5000);

            }
        },5000);
    }
    public void setBussinessNewsOff(Context context){
        newsDB = new NewsDB(context);
        newsDB.deleteBun();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<20;i++){

                    String img_news1 = LoadingData.bussinessList.get(i).getImg_news();
                    String img_source1 = LoadingData.bussinessList.get(i).getImg_source();
                    int finalI = i;
                    RequestOptions requestOptions = new RequestOptions().override(350,150).fitCenter();
                    CustomTarget<Bitmap> theBitmap = Glide.with(context).asBitmap().apply(requestOptions).load(img_news1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineBussinessArrayList.get(finalI).setImg_news(getBytesImg(resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
                    CustomTarget<Bitmap> theBitmap1 = Glide.with(context).asBitmap().load(img_source1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineBussinessArrayList.get(finalI).setImg_source(getBytesImg(resource));

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for(int i =0; i<20;i++){
                            if(newsOffLineBussinessArrayList.get(i).getImg_source() == null){
                                continue;

                            }
                            if(newsOffLineBussinessArrayList.get(i).getImg_news() == null){
                                continue;
                            }

                            newsDB.insertBusiness(newsOffLineBussinessArrayList.get(i));

                        }

                    }
                },5000);

            }
        },5000);
    }
    public void setScienceNewsOff(Context context){
        newsDB = new NewsDB(context);
        newsDB.deleteSci();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<20;i++){

                    String img_news1 = LoadingData.scienceList.get(i).getImg_news();
                    String img_source1 = LoadingData.scienceList.get(i).getImg_source();
                    int finalI = i;
                    RequestOptions requestOptions = new RequestOptions().override(350,150).fitCenter();
                    CustomTarget<Bitmap> theBitmap = Glide.with(context).asBitmap().apply(requestOptions).load(img_news1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineScienceArrayList.get(finalI).setImg_news(getBytesImg(resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
                    CustomTarget<Bitmap> theBitmap1 = Glide.with(context).asBitmap().load(img_source1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineScienceArrayList.get(finalI).setImg_source(getBytesImg(resource));

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for(int i =0; i<20;i++){
                            if(newsOffLineScienceArrayList.get(i).getImg_source() == null){
                                continue;

                            }
                            if(newsOffLineScienceArrayList.get(i).getImg_news() == null){
                                continue;
                            }

                            newsDB.insertScience(newsOffLineScienceArrayList.get(i));

                        }

                    }
                },5000);

            }
        },5000);
    }
    public void setHealthNewsOff(Context context){
        newsDB = new NewsDB(context);
        newsDB.deleteHea();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<20;i++){

                    String img_news1 = LoadingData.healthList.get(i).getImg_news();
                    String img_source1 = LoadingData.healthList.get(i).getImg_source();
                    int finalI = i;
                    RequestOptions requestOptions = new RequestOptions().override(350,150).fitCenter();
                    CustomTarget<Bitmap> theBitmap = Glide.with(context).asBitmap().apply(requestOptions).load(img_news1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineHealthArrayList.get(finalI).setImg_news(getBytesImg(resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
                    CustomTarget<Bitmap> theBitmap1 = Glide.with(context).asBitmap().load(img_source1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineHealthArrayList.get(finalI).setImg_source(getBytesImg(resource));

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for(int i =0; i<20;i++){
                            if(newsOffLineHealthArrayList.get(i).getImg_source() == null){
                                continue;

                            }
                            if(newsOffLineHealthArrayList.get(i).getImg_news() == null){
                                continue;
                            }

                            newsDB.insertHealth(newsOffLineHealthArrayList.get(i));

                        }

                    }
                },5000);

            }
        },5000);
    }
    public void setEntartinmentNewsOff(Context context){
        newsDB = new NewsDB(context);
        newsDB.deleteEnt();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<20;i++){

                    String img_news1 = LoadingData.entartinmentList.get(i).getImg_news();
                    String img_source1 = LoadingData.entartinmentList.get(i).getImg_source();
                    int finalI = i;
                    RequestOptions requestOptions = new RequestOptions().override(350,150).fitCenter();
                    CustomTarget<Bitmap> theBitmap = Glide.with(context).asBitmap().apply(requestOptions).load(img_news1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineEntartinmentArrayList.get(finalI).setImg_news(getBytesImg(resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
                    CustomTarget<Bitmap> theBitmap1 = Glide.with(context).asBitmap().load(img_source1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineEntartinmentArrayList.get(finalI).setImg_source(getBytesImg(resource));

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for(int i =0; i<20;i++){
                            if(newsOffLineEntartinmentArrayList.get(i).getImg_source() == null){
                                continue;

                            }
                            if(newsOffLineEntartinmentArrayList.get(i).getImg_news() == null){
                                continue;
                            }

                            newsDB.insertEntertainment(newsOffLineEntartinmentArrayList.get(i));

                        }

                    }
                },5000);

            }
        },5000);
    }
    public void setTechnologyNewsOff(Context context){
        newsDB = new NewsDB(context);
        newsDB.deleteTec();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<20;i++){

                    String img_news1 = LoadingData.technologyList.get(i).getImg_news();
                    String img_source1 = LoadingData.technologyList.get(i).getImg_source();
                    int finalI = i;
                    RequestOptions requestOptions = new RequestOptions().override(350,150).fitCenter();
                    CustomTarget<Bitmap> theBitmap = Glide.with(context).asBitmap().apply(requestOptions).load(img_news1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineTechnologyArrayList.get(finalI).setImg_news(getBytesImg(resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
                    CustomTarget<Bitmap> theBitmap1 = Glide.with(context).asBitmap().load(img_source1).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            newsOffLineTechnologyArrayList.get(finalI).setImg_source(getBytesImg(resource));

                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for(int i =0; i<20;i++){
                            if(newsOffLineTechnologyArrayList.get(i).getImg_source() == null){
                                continue;

                            }
                            if(newsOffLineTechnologyArrayList.get(i).getImg_news() == null){
                                continue;
                            }

                            newsDB.insertTechnology(newsOffLineTechnologyArrayList.get(i));

                        }

                    }
                },5000);

            }
        },5000);
    }


    public static byte[] getBytesImg(Bitmap bitmap){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0, stream);
        return stream.toByteArray();
    }

}
