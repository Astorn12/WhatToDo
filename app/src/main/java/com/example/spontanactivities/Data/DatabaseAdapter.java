package com.example.spontanactivities.Data;

import android.content.Context;

import androidx.room.Room;

public  class DatabaseAdapter {

   //private final String databasename = "spontandatabase";

    public AppDatabase getDatabase(Context context) {
        AppDatabase appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();// SHOULD NOT BE USED IN PRODUCTION !!!
        return appDatabase;
    }

}
