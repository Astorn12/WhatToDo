package com.example.spontanactivities.Dtos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StateDto {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    int id;
    @ColumnInfo(name="stateName")
    String stateName;

    public StateDto(int id, String state) {
        this.id = id;
        this.stateName = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return stateName;
    }

    public void setState(String state) {
        this.stateName = state;
    }
}
