package com.cripochec.Flopy.ui.utils.JsonUtils;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class JsonUtils {

    private static final String ID_FILE = "IDInfo.json";
    private static final String PERSON_INFO_FILE = "PersonInfo.json";
    private static final String ENTRY_FILE = "EntryInfo.json";

    public static void saveID(Context context, int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            String jsonString = jsonObject.toString();
            FileOutputStream fos = context.openFileOutput(ID_FILE, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(jsonString);
            writer.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int loadID(Context context) {
        try {
            FileInputStream fis = context.openFileInput(ID_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            fis.close();
            JSONObject jsonObject = new JSONObject(sb.toString());
            return jsonObject.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void saveEntry(Context context, boolean status) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            String jsonString = jsonObject.toString();
            FileOutputStream fos = context.openFileOutput(ENTRY_FILE, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(jsonString);
            writer.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean loadEntry(Context context) {
        try {
            FileInputStream fis = context.openFileInput(ENTRY_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            fis.close();
            JSONObject jsonObject = new JSONObject(sb.toString());
            return jsonObject.getBoolean("status");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public static void savePersonInfoData(Context context, String name, int age, String gender, String goal,
                                String city, String zodiac_sign, String height, String education,
                                String children, String smoking, String alcohol, boolean verification) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("age", age);
            jsonObject.put("gender", gender);
            jsonObject.put("goal", goal);
            jsonObject.put("city", city);
            jsonObject.put("zodiac_sign", zodiac_sign);
            jsonObject.put("height", height);
            jsonObject.put("education", education);
            jsonObject.put("children", children);
            jsonObject.put("smoking", smoking);
            jsonObject.put("alcohol", alcohol);
            jsonObject.put("verification", verification);
            String jsonString = jsonObject.toString();
            FileOutputStream fos = context.openFileOutput(PERSON_INFO_FILE, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(jsonString);
            writer.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PersonInfo loadPersonInfoData(Context context) {
        try {
            FileInputStream fis = context.openFileInput(PERSON_INFO_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            fis.close();
            JSONObject jsonObject = new JSONObject(sb.toString());

            // Получение данных о персоне из JSON объекта
            String name = jsonObject.optString("name");
            int age = jsonObject.optInt("age");
            String gender = jsonObject.optString("gender");
            String goal = jsonObject.optString("goal");
            String city = jsonObject.optString("city");
            String zodiac_sign = jsonObject.optString("zodiac_sign");
            String height = jsonObject.optString("height");
            String education = jsonObject.optString("education");
            String children = jsonObject.optString("children");
            String smoking = jsonObject.optString("smoking");
            String alcohol = jsonObject.optString("alcohol");
            boolean verification = jsonObject.optBoolean("verification");

            // Создание объекта PersonInfo и возврат его
            return new PersonInfo(name, age, gender, goal, city, zodiac_sign, height, education, children, smoking, alcohol, verification);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
