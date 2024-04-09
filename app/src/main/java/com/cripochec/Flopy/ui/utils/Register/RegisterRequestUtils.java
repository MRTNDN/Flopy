package com.cripochec.Flopy.ui.utils.Register;

import android.os.AsyncTask;
import android.util.Log;

import com.westernyey.Flopy.ui.register.FragmentRegister;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RegisterRequestUtils extends AsyncTask<String, Void, String> {

    private final FragmentRegister fragment;

    public RegisterRequestUtils(FragmentRegister fragment) {
        this.fragment = fragment;
    }


    @Override
    protected String doInBackground(String... params) {
        String email = params[0];
        String password = params[1];
        try {
            String URL_SERVER = "http://90.156.231.211:5000/register_data";
            URL url = new URL(URL_SERVER);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            String data = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
            OutputStream os = con.getOutputStream();

            os.write(data.getBytes());
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } catch (IOException e) {
            Log.e("RequestUtils", "Error", e);
            return "ERROR " + e;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            // Разбор JSON-строки для извлечения данных
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            boolean status = jsonObject.getBoolean("status");
            int userId = jsonObject.getInt("user_id");

            // Вызов метода updateData() фрагмента для передачи данных
            fragment.updateData(code, status, userId);
        } catch (JSONException e) {
            Log.e("RequestUtils", "Error parsing JSON", e);
        }
    }
}
