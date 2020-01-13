package com.example.spontanactivities.Data.Daos.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.SpontanTagDto;
import com.example.spontanactivities.Model.Spontan;

import java.util.List;
@Dao
public interface SpontanTagDao {
    @Query("SELECT * FROM spontantagdto")
    List<SpontanTagDto> getAll();



    @Query("SELECT * FROM spontantagdto WHERE spontanId= :spontanid")
    List<SpontanTagDto> getAllBySpontanIds(int spontanid);

    @Query("SELECT * FROM spontantagdto WHERE tagId IN (:tagid)")
    List<SpontanTagDto> loadAllByTagIds(int tagid);

    @Query("DELETE  FROM spontantagdto WHERE tagId IN (:tags) AND spontanId=:spontan")
    void delete(int spontan, List<Integer> tags);




    @Insert
    void insertAll(SpontanTagDto... spontanTagDaos);

    @Insert
    void insertAll(List<SpontanTagDto> spontanTagDaos);

    @Delete
    void delete(SpontanTagDto spontanTagDao);
}
