package com.example.spontanactivities.Data.Daos.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.spontanactivities.Dtos.PhotoDto;
import com.example.spontanactivities.Dtos.SpontanDto;

import java.util.List;

@Dao
public interface PhotoDao  {
    @Query("SELECT * FROM photodto")
    List<PhotoDto> getAll();

    @Query("SELECT * FROM photodto WHERE id IN (:photoIds)")
    List<PhotoDto> loadAllByIds(int[] photoIds);


    @Query("SELECT * FROM photodto WHERE id LIKE :id")
    PhotoDto findById(int id);

    @Insert
    long[] insertAll(List<PhotoDto> photoDtos);

    @Insert
    void insertAll(PhotoDto... photoDtos);

    @Insert
    long insert(PhotoDto photoDto);

    @Delete
    void delete(PhotoDto photoDto);
}
