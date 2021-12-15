package com.example.testontouchlictener.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedGameSetting {

    // Переменная для названия файла в файлах приложения
    private static final String PREFS_FILE = "AppWordGame";

    // Тут названия для переменных для сохранения
    public static final String PREF_NAME = "Name";

    public static void saveNameShared(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(PREF_NAME, name);
        prefEditor.apply();
    }

    public static String getNameShared(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_FILE, MODE_PRIVATE);
        return settings.getString(PREF_NAME, "");
    }
}
