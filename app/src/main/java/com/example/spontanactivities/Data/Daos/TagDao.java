package com.example.spontanactivities.Data.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.spontanactivities.Dtos.TagDto;

import java.util.List;

@Dao
public interface TagDao  {

    @Query("SELECT * FROM tagdto")
    List<TagDto> getAll();



    @Query("SELECT * FROM tagdto WHERE id IN (:userIds)")
    List<TagDto> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM tagdto WHERE name LIKE :name")
    TagDto findByName(String name);

    @Insert
    void insertAll(TagDto... tags);

    @Insert
    long[] insertAll(List<TagDto> tagDtos);

    @Delete
    void delete(TagDto tag);

    @Query("SELECT * FROM tagdto WHERE id IN (:tagsIds)")
    List<TagDto> getByIdList(List<Integer> tagsIds);
}
