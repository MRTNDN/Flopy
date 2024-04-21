package com.cripochec.Flopy.ui.utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PersonInfo {

    private static final String FILE_NAME = "PersonInfo.json";

    public static void saveData(Context context, int id, boolean theme) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("theme", theme);
            String jsonString = jsonObject.toString();
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(jsonString);
            writer.close();
            fos.close();
            Toast.makeText(context, "ID сохранен в JSON файл", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int loadID(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            fis.close();
            JSONObject jsonObject = new JSONObject(sb.toString());
            int id = jsonObject.getInt("id");
            Toast.makeText(context, "ID из JSON файла: " + id, Toast.LENGTH_SHORT).show();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean loadTheme(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            fis.close();
            JSONObject jsonObject = new JSONObject(sb.toString());
            int id = jsonObject.getInt("id");
            boolean theme = jsonObject.getBoolean("theme");
            Toast.makeText(context, "ID из JSON файла: " + id+"/тема: "+theme, Toast.LENGTH_SHORT).show();
            return theme;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
