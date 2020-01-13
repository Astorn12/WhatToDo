package com.example.spontanactivities.Data;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.spontanactivities.Data.Daos.Interfaces.PhotoDao;
import com.example.spontanactivities.Data.Daos.Interfaces.SpontanDao;
import com.example.spontanactivities.Data.Daos.Interfaces.SpontanTagDao;
import com.example.spontanactivities.Data.Daos.Interfaces.StateDao;
import com.example.spontanactivities.Data.Daos.TagDaoImp;
import com.example.spontanactivities.Dtos.PhotoDto;
import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.SpontanTagDto;
import com.example.spontanactivities.Dtos.StateDto;
import com.example.spontanactivities.Dtos.TagDto;


@Database(entities = {SpontanDto.class, TagDto.class, SpontanTagDto.class, StateDto.class, PhotoDto.class}, version = 4,exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {



    public abstract SpontanDao spontanDao();
    public abstract TagDaoImp tagDao();
    public abstract SpontanTagDao spontanTagDao();
    public abstract StateDao stateDao();
    public abstract PhotoDao photoDao();


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    /*@Override
    public void clearAllTables() {

    }*/
}
