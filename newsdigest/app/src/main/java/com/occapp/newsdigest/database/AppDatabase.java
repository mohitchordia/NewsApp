package com.occapp.newsdigest.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.occapp.newsdigest.network.model.NewsArticles;

import dagger.Module;

@Database(entities = {NewsArticles.class}, version = 1, exportSchema = false)
@TypeConverters({SourceConverter.class})
@Module
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}