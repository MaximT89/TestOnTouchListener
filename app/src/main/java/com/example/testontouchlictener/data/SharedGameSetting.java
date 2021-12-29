package com.example.testontouchlictener.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedGameSetting {

    // Переменная для названия файла в файлах приложения
    private static final String PREFS_FILE = "AppWordGame";

    // Текущий игровой мир
    public static final String PREF_CURRENT_WORLD = "CurrentWorld";

    // Максимальный мир до которого дошел игрок
    public static final String PREF_MAX_WORLD = "MaxWorld";

    // Текущее количество игровых очков
    public static final String PREF_CURRENT_POINTS = "CurrentPoint";

    public static final int MODE = Context.MODE_PRIVATE;

    public static void setPrefCurrentPoints(Context context, int currentPoint){
        getEditor(context).putInt(PREF_CURRENT_POINTS, currentPoint).apply();
    }

    public static int getPrefCurrentPoints(Context context){
        return getPreferences(context).getInt(PREF_CURRENT_POINTS, 1);
    }


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
