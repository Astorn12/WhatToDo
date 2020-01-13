package com.example.spontanactivities.Data.ObjectAdapters;

import android.content.Context;

import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.Daos.Interfaces.TagDao;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.Model.Tag;

import java.util.LinkedList;
import java.util.List;

public class TagDataAdapter implements ObjectDataAdapter<Tag> {


    @Override
    public  List<Tag> getAll(Context context) {
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        AppDatabase appDatabase= databaseAdapter.getDatabase(context);
        TagDao tagDao= appDatabase.tagDao();
        List<TagDto>tagDtoList= tagDao.getAll();

        List<Tag> tagFromDatabaseList= new LinkedList<>();

        for(TagDto tagDto: tagDtoList){
            tagFromDatabaseList.add(new Tag(tagDto));
        }

        return tagFromDatabaseList;
    }
}
