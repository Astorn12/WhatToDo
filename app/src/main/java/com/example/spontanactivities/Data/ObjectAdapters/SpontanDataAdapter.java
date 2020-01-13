package com.example.spontanactivities.Data.ObjectAdapters;

import android.content.Context;

import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.Daos.Interfaces.SpontanDao;
import com.example.spontanactivities.Data.Daos.Interfaces.TagDao;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.Model.Spontan;
import com.example.spontanactivities.Model.Tag;

import java.util.LinkedList;
import java.util.List;

public class SpontanDataAdapter implements ObjectDataAdapter<Spontan> {
    @Override
    public List<Spontan> getAll(Context context) {
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        AppDatabase appDatabase= databaseAdapter.getDatabase(context);
        SpontanDao spontanDao= appDatabase.spontanDao();
        List<SpontanDto>spontanDtosList= spontanDao.getAll();

        List<Spontan> spontanFromDatabaseList= new LinkedList<>();

        for(SpontanDto spontanDto: spontanDtosList){
            spontanFromDatabaseList.add(new Spontan(spontanDto,context));
        }

        return spontanFromDatabaseList;
    }
}
