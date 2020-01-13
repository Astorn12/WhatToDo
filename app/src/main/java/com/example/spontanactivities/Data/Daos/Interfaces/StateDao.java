package com.example.spontanactivities.Data.Daos.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.spontanactivities.Dtos.SpontanDto;
import com.example.spontanactivities.Dtos.StateDto;

import java.util.List;

@Dao
public interface StateDao {

    @Query("SELECT * FROM statedto")
    List<StateDto> getAll();

    @Query("SELECT * FROM statedto WHERE id IN (:stateIds)")
    List<StateDto> loadAllByIds(int[] stateIds);

    @Query("SELECT * FROM statedto WHERE statename LIKE :statename")
    StateDto findByName(String statename);

    @Query("SELECT * FROM statedto WHERE id LIKE :id")
    StateDto findById(int id);

    @Insert
//(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<StateDto> stateDtos);

    @Insert
    void insertAll(StateDto... stateDtos);

    @Insert
    long insert(StateDto ststeDto);

    @Delete
    void delete(StateDto stateDto);
}
