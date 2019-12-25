package com.example.spontanactivities.Data.Daos;

import android.content.Context;

import androidx.room.Dao;

import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.SpontanTagDto;
import com.example.spontanactivities.Dtos.TagDto;
import com.example.spontanactivities.Model.Tag;

import java.util.LinkedList;
import java.util.List;

@Dao
public abstract class TagDaoImp implements TagDao {
    public List<Tag> getTagListBySpontanId(Context context, int spontanId){
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        List<SpontanTagDto> list=databaseAdapter.getDatabase(context).spontanTagDao().getAllBySpontanIds(spontanId);

        List<Integer> tagIdList= new LinkedList<>();
        for(SpontanTagDto std: list){
            tagIdList.add(std.getTagId());
        }

        List<Tag> tagList= new LinkedList<>();
        for(TagDto td:this.getByIdList(tagIdList)){
            tagList.add(new Tag(td));
        }

        return tagList;
    }
}
