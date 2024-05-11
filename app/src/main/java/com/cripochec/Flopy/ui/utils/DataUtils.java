package com.cripochec.Flopy.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataUtils {
    private static final String SHARED_PREF_NAME = "Prefs";
    private static final String KEY_ID = "userId";
    private static final String ENTRY = "entry";
    private static final String THEME = "theme";

//    id
    public static void saveUserId(Context context, int userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, userId);
        editor.apply();
    }

    public static int getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, 0); // Возвращаем 0 если идентификатор не найден
    }

//    Первичный вход
    public static void saveEntry(Context context, Boolean entry) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ENTRY, entry);
        editor.apply();
    }

    public static boolean getEntry(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(ENTRY, true); // Возвращаем false если идентификатор не найден
    }

//    Тема
    public static void saveTheme(Context context, Boolean theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(THEME, theme);
        editor.apply();
    }

    public static boolean getTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(THEME, false); // Возвращаем false если идентификатор не найден
    }
}
