package com.example.dailynews.interfaces;

import com.example.dailynews.classes.News;

public interface OnClickListenerItem {
    public void onClick(News news);
    public void share(News news);
    public void like(News news);
}
