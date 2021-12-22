package com.example.testontouchlictener.data.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorldEntity {

    @PrimaryKey(autoGenerate = true)
    public int idWorld;
    public int levelInWorld;

    public WorldEntity(int idWorld, int levelInWorld) {
        this.idWorld = idWorld;
        this.levelInWorld = levelInWorld;
    }

    public int getIdWorld() {
        return idWorld;
    }

    public int getLevelInWorld() {
        return levelInWorld;
    }

    public void setIdWorld(int idWorld) {
        this.idWorld = idWorld;
    }

    public void setLevelInWorld(int levelInWorld) {
        this.levelInWorld = levelInWorld;
    }
}
