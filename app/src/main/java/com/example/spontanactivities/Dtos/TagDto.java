package com.example.spontanactivities.Dtos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.spontanactivities.Helpers.SerialaizerDeserializer;
import com.example.spontanactivities.Model.Tag;
import com.fasterxml.jackson.core.io.SerializedString;

import java.util.LinkedList;
import java.util.List;


@Entity
public class TagDto {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    int id;
    @ColumnInfo(name="name")
    String name;
    @ColumnInfo(name="imageResource")
    int imageResource;
    @ColumnInfo(name = "JSON")
    String JSON;


    public TagDto(int id,String name, int imageResource,String JSON){
        this.id=id;
        this.name=name;
        this.imageResource=imageResource;
        this.JSON=JSON;
    }
    @Ignore
    public TagDto(String name,int imageResource, String JSON){
        this.name=name;
        this.imageResource=imageResource;
        this.JSON=JSON;
    }

    public TagDto(Tag tag){
        this.name=tag.getName();
        this.imageResource=tag.getImageResource();
        this.JSON= SerialaizerDeserializer.serialize(tag.getTagBehavior());

    }

    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getJSON() {
        return JSON;
    }

    public int getId() {
        return id;
    }

    public static List<Tag> convert (List<TagDto> tagDtos){
        List<Tag> tags= new LinkedList<>();

        for(TagDto tagDto: tagDtos){
            tags.add(new Tag(tagDto));
        }
        return tags;
    }




}
