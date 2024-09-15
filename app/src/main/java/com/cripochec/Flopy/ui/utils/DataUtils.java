package com.cripochec.Flopy.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataUtils {
    private static final String SHARED_PREF_NAME = "Prefs";
    private static final String KEY_ID = "userId";
    private static final String ENTRY = "entry";
    private static final String THEME = "theme";
    private static final String FULLNESS = "fullness";
    private static final String LAST_FRAGMENT_POSITION = "lastFragmentPosition";
    private static final String PHOTO1 = "photo1";
    private static final String PHOTO2 = "photo2";
    private static final String PHOTO3 = "photo3";
    private static final String PHOTO4 = "photo4";

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

//    Заполниность профиля
    public static void saveFullness(Context context, int fullness) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(FULLNESS, fullness);
        editor.apply();
    }

    public static int getFullness(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(FULLNESS, 0); // Возвращаем 0 если идентификатор не найден
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


//    Фото пользователя 1
    public static void savePhoto1(Context context, String photo1) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHOTO1, photo1);
        editor.apply();
    }

    public static String getPhoto1(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHOTO1, "add_photo"); // Возвращаем "add_photo" если идентификатор не найден
    }


//    Фото пользователя 2
    public static void savePhoto2(Context context, String photo2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHOTO2, photo2);
        editor.apply();
    }

    public static String getPhoto2(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHOTO2, "add_photo"); // Возвращаем "add_photo.xml" если идентификатор не найден
    }


//    Фото пользователя 3
    public static void savePhoto3(Context context, String photo3) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHOTO3, photo3);
        editor.apply();
    }

    public static String getPhoto3(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHOTO3, "add_photo"); // Возвращаем "add_photo" если идентификатор не найден
    }


//    Фото пользователя 4
    public static void savePhoto4(Context context, String photo4) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHOTO4, photo4);
        editor.apply();
    }

    public static String getPhoto4(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHOTO4, "add_photo"); // Возвращаем "add_photo" если идентификатор не найден
    }
}
