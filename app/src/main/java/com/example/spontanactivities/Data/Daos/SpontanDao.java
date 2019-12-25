package com.example.spontanactivities.Data.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import androidx.room.Query;

import com.example.spontanactivities.Dtos.SpontanDto;



import java.util.List;




@Dao
public interface SpontanDao {

    @Query("SELECT * FROM spontandto")
    List<SpontanDto> getAll();

    @Query("SELECT * FROM spontandto WHERE id IN (:userIds)")
    List<SpontanDto> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM spontandto WHERE name LIKE :first")
    SpontanDto findByName(String first);

    @Query("SELECT * FROM spontandto WHERE id LIKE :id")
    SpontanDto findById(int id);

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<SpontanDto> spontanDtos);

    @Insert
    void insertAll(SpontanDto... spontanDtos);

    @Insert
    long insert(SpontanDto spontanDto);

    @Delete
    void delete(SpontanDto user);

}
