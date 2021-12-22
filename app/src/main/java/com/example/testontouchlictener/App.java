package com.example.testontouchlictener;

import android.app.Application;

import androidx.room.Room;

import com.example.testontouchlictener.data.SharedGameSetting;
import com.example.testontouchlictener.data.room.database.WorldDatabase;
import com.example.testontouchlictener.data.room.entity.WorldEntity;

public class App extends Application {

    public static App instance;

    private WorldDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        mDatabase = Room.databaseBuilder(this, WorldDatabase.class, "database")
                .allowMainThreadQueries()
                .build();

        // Добавляем в базу данных первый мир и настройки
        if (mDatabase.worldDao().getWorldById(1) == null) {
            WorldEntity world = new WorldEntity(1, 1);
            mDatabase.worldDao().insertWorld(world);

            SharedGameSetting.setPrefCurrentWorld(this, 1);
            SharedGameSetting.setPrefMaxWorld(this, 1);
        }
    }

    public static App getInstance() {
        return instance;
    }

    public WorldDatabase getDatabase() {
        return mDatabase;
    }
}
