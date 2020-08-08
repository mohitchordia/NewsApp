package com.occapp.newsdigest.components;

import com.occapp.newsdigest.activities.home.HomeModel;
import com.occapp.newsdigest.database.AppDatabase;
import com.occapp.newsdigest.database.NewsDao;

import dagger.Component;


@Component(modules = {AppDatabase.class, NewsDao.class})
public interface ComponentsOur {
    HomeModel getHomeModel();
}
