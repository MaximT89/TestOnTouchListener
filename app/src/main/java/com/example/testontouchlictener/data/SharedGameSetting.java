package com.example.testontouchlictener.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharedGameSetting {

    // Переменная для названия файла в файлах приложения
    private static final String PREFS_FILE = "AppWordGame";

    // Тут названия для переменных для сохранения
    public static final String PREF_CURRENT_WORLD = "CurrentWorld";
    public static final String PREF_MAX_WORLD = "MaxWorld";


    public static final int MODE = Context.MODE_PRIVATE;

    public static void setPrefCurrentWorld(Context context, int currentWorld) {
        getEditor(context).putInt(PREF_CURRENT_WORLD, currentWorld).apply();
    }

    public static int getPrefCurrentWorld(Context context) {
        return getPreferences(context).getInt(PREF_CURRENT_WORLD, 1);
    }

    public static void setPrefMaxWorld(Context context, int maxWorld) {
        getEditor(context).putInt(PREF_MAX_WORLD, maxWorld).apply();
    }

    public static int getPrefMaxWorld(Context context) {
        return getPreferences(context).getInt(PREF_MAX_WORLD, 1);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }


}
