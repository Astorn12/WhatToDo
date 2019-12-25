package com.example.spontanactivities.Model;


import android.content.Context;

import com.example.spontanactivities.Data.AppDatabase;
import com.example.spontanactivities.Data.DatabaseAdapter;
import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.SpontanTagDto;
import com.example.spontanactivities.Dtos.TagDto;

import java.util.LinkedList;
import java.util.List;

public class Spontan {
    int id;
    String name;
    List<Tag> tags;


    public Spontan(String name, List<Tag> tags) {
        this.name = name;
        this.tags = tags;
    }

    public Spontan(SpontanDto spontanDto, Context context){
        this.id=spontanDto.id;
        this.name=spontanDto.name;
        this.tags=getSpontansTags(context);

    }

    private List<Tag> getSpontansTags(Context context) {
        DatabaseAdapter databaseAdapter= new DatabaseAdapter();
        AppDatabase appDatabase=databaseAdapter.getDatabase(context);
        List<SpontanTagDto> spontanTagDtos=appDatabase.spontanTagDao().getAllBySpontanIds(this.id);
        List<SpontanTagDto> allSpontanTagDto=appDatabase.spontanTagDao().getAll();
        List<Integer>  tagsIds= new LinkedList<>();

        for(SpontanTagDto spontanTagDto: spontanTagDtos){
            tagsIds.add(spontanTagDto.getTagId());
        }


        List<TagDto> tagsFromDB= appDatabase.tagDao().getByIdList(tagsIds);
        List<Tag> tagsToReturn= new LinkedList<>();

        for(TagDto tagDto: tagsFromDB){
            tagsToReturn.add(new Tag(tagDto));
        }

        return tagsToReturn;
    }

    public String getName() {
        return name;
    }

    public List<Tag> getTags() {
        return tags;
    }

   public void getPossibleTags(){
   }

    public int getId() {
        return id;
    }
}
