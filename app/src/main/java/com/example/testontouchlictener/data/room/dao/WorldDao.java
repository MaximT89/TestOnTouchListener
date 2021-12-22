package com.example.testontouchlictener.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testontouchlictener.data.room.entity.WorldEntity;

@Dao
public interface WorldDao {

    @Query("SELECT * FROM WorldEntity WHERE idWorld = :idWorld")
    WorldEntity getWorldById(int idWorld);

    @Update
    void updateWorld(WorldEntity world);

    @Insert
    void insertWorld(WorldEntity world);

}
