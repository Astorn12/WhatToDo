package com.example.spontanactivities.Dtos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"spontanId", "tagId"})
public class SpontanTagDto {

    @ColumnInfo(name="spontanId")
     public int spontanId;
    @ColumnInfo(name="tagId")
     public int tagId;

    public SpontanTagDto(int spontanId, int tagId) {
        this.spontanId = spontanId;
        this.tagId = tagId;
    }

    public int getSpontanId() {
        return spontanId;
    }

    public int getTagId() {
        return tagId;
    }
}
