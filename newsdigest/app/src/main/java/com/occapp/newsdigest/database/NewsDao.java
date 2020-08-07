package com.occapp.newsdigest.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.occapp.newsdigest.network.model.NewsArticles;

import java.util.List;

import dagger.Module;

import static androidx.room.OnConflictStrategy.REPLACE;

@Module
@Dao
public interface NewsDao {


    @Query("SELECT * FROM newsarticles")
    LiveData<List<NewsArticles>> getAll();

    @Insert(onConflict = REPLACE)
    void insertAll(NewsArticles... newsArticles);

    @Delete
    void delete(List<NewsArticles> newsArticles);

    @Insert(onConflict = REPLACE)
    void insertList(List<NewsArticles> newsArticles);

}
