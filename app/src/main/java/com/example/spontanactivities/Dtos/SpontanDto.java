package com.example.spontanactivities.Dtos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.example.spontanactivities.Model.Spontan;


@Entity(foreignKeys = @ForeignKey(entity=StateDto.class,
parentColumns="id",
childColumns="stateId"))
public class SpontanDto {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name="stateId",index = true)
    public int  stateId;


    public SpontanDto(int id, String name, int stateId) {
        this.id = id;
        this.name = name;
        this.stateId = stateId;
    }


    @Ignore
   public SpontanDto(String name){
        this.name=name;

   }

    public SpontanDto(Spontan spontan){
        this.id=spontan.getId();
        this.name=spontan.getName();
    }


}
