package com.example.testontouchlictener.data.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.testontouchlictener.data.room.dao.WorldDao;
import com.example.testontouchlictener.data.room.entity.WorldEntity;

@Database(entities = {WorldEntity.class}, version = 1)
public abstract class WorldDatabase extends RoomDatabase {
    public abstract WorldDao worldDao();
}
