package com.cripochec.Flopy.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataUtils {
    public static final String IP = "http://192.168.0.102:8000/";
    private static final String SHARED_PREF_NAME = "Prefs";
    private static final String KEY_ID = "userId";
    private static final String ENTRY = "entry";
    private static final String THEME = "theme";
    private static final String LAST_FRAGMENT_POSITION = "lastFragmentPosition";
    private static final String INCOGNITO = "incognito";

//    Отчистка SharedPreferences
    public static void clearAllData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

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

//    Последний фрагмент
    public static void saveLastFragmentPosition(Context context, String lastFragmentPosition) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_FRAGMENT_POSITION, lastFragmentPosition);
        editor.apply();
    }

    public static String getLastFragmentPosition(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LAST_FRAGMENT_POSITION, ""); // Возвращаем "" если идентификатор не найден
    }

//    Статус инкогнито
    public static void saveIncognito(Context context, int status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(INCOGNITO, status);
        editor.apply();
    }

    public static int getIncognito(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(INCOGNITO, 0); // Возвращаем 0 если идентификатор не найден
    }
}
