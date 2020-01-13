package com.example.spontanactivities.Dtos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class PhotoDto {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="url")
    public String url;

    public PhotoDto(int id, String url) {
        this.id = id;
        this.url = url;
    }
    @Ignore
    public PhotoDto(String url) {
        this.url = url;
    }

}
