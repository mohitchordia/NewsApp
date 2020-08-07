package com.occapp.newsdigest.database;

import androidx.room.Room;

import com.occapp.newsdigest.AppApplication;

import javax.inject.Singleton;

@Singleton
public class DataBaseHelper {
    private static final String DB_NAME = "news_db";
    private final static String TAG = DataBaseHelper.class.getName();
    private static NewsDataBase db;

    /***
     *
     * @return an instance of AppDatabase
     */
    public static NewsDataBase getInstance() {
        if (db == null) {
            // To make thread safe
            synchronized (DataBaseHelper.class) {
                // check again as multiple threads
                if (db == null) {
                    db = Room.databaseBuilder(AppApplication.getContext(),
                            NewsDataBase.class, DB_NAME).build();

                }
            }
        }
        return db;
    }

    public static void dbClose() {
        if (db != null && db.isOpen())
            db.close();
        db = null;
    }
}
