package com.cripochec.Flopy.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataUtils {
//    public static final String IP = "http://192.168.0.54:8000/";
//    public static final String IP = "http://192.168.0.102:8000/";
//    public static final String IP = "http://192.168.0.105:8000/";
    public static final String IP = "http://192.168.0.107:8000/";
//    public static final String IP = "http://192.168.182.87:8000/";
//    public static final String IP = "http://192.168.43.199:8000/";
    private static final String SHARED_PREF_NAME = "Prefs";
    private static final String KEY_ID = "userId";
    private static final String ENTRY = "entry";
    private static final String THEME = "theme";
    private static final String LAST_FRAGMENT_POSITION = "lastFragmentPosition";
    private static final String FILTER_GENDER = "filter_gender";
    private static final String FILTER_MIN_AGE = "filter_min_age";
    private static final String FILTER_MAX_AGE = "max_age";
    private static final String FILTER_STATUS = "filter_status";

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

//    Фильтр по полу
    public static void saveFilterGender(Context context, int filter_gender) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(FILTER_GENDER, filter_gender);
        editor.apply();
    }

    public static int getFilterGender(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(FILTER_GENDER, 0); // Возвращаем 0 если идентификатор не найден
    }

//    Фильтр по минимальному возрасту
    public static void saveFilterMinAge(Context context, int filter_min_age) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(FILTER_MIN_AGE, filter_min_age);
        editor.apply();
    }

    public static int getFilterMinAge(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(FILTER_MIN_AGE, 18); // Возвращаем 18 если идентификатор не найден
    }

//    Фильтр по максимальному возрасту
    public static void saveFilterMaxAge(Context context, int filter_max_age) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(FILTER_MAX_AGE, filter_max_age);
        editor.apply();
    }

    public static int getFilterMaxAge(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(FILTER_MAX_AGE, 27); // Возвращаем 27 если идентификатор не найден
    }

//    Фильтр по цели
    public static void saveFilterStatus(Context context, int filter_status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(FILTER_STATUS, filter_status);
        editor.apply();
    }

    public static int getFilterStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(FILTER_STATUS, 0); // Возвращаем 0 если идентификатор не найден
    }
}
