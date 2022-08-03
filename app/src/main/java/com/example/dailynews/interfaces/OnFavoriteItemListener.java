package com.example.dailynews.interfaces;

import com.example.dailynews.classes.News;

public interface OnFavoriteItemListener {
    public void onDelete(int position);
    public void onClick(News news);
    public void share(News news);
}
