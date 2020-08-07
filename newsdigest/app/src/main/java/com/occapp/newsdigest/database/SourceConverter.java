package com.occapp.newsdigest.database;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.occapp.newsdigest.network.model.NewsSource;


import java.lang.reflect.Type;

public class SourceConverter {

    @TypeConverter
    public String fromList(NewsSource articles) {
        if (articles == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<NewsSource>() {
        }.getType();
        return gson.toJson(articles, type);
    }

    @TypeConverter
    public NewsSource toList(String itemString) {
        if (itemString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<NewsSource>() {
        }.getType();
        return gson.fromJson(itemString, type);
    }

}
