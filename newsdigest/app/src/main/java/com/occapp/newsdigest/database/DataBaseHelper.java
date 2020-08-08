package com.occapp.newsdigest.database;

import android.arch.persistence.room.Room;

import com.occapp.newsdigest.AppApplication;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataBaseHelper {

    private static final String DB_NAME = "news_db";
    private final static String TAG = DataBaseHelper.class.getName();
    private static AppDatabase db;

    /***
     *
     * @return an instance of AppDatabase
     */
    public static AppDatabase getInstance() {
        if (db == null) {
            // To make thread safe
            synchronized (DataBaseHelper.class) {
                // check again as multiple threads
                if (db == null) {
                    db = Room.databaseBuilder(AppApplication.getContext(),
                            AppDatabase.class, DB_NAME).build();

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
