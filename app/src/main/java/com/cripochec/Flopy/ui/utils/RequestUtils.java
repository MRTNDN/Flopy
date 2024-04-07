package com.cripochec.Flopy.ui.utils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RequestUtils {
    private final String IP_SERVER = "90.156.231.211:5000";
    public static String request() {
        try {
            // URL сервера, куда вы будете отправлять данные
            String url = "http://90.156.231.211:5000/process_data";

            // Данные для отправки на сервер (замените на свои)
            String data = "{\"email\": \"example@example.com\", \"password\": \"mypassword\"}";

            // Создаем объект URL
            URL obj = new URL(url);

            // Открываем соединение HttpURLConnection
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Устанавливаем метод запроса
            con.setRequestMethod("POST");

            // Устанавливаем заголовок Content-Type
            con.setRequestProperty("Content-Type", "application/json");

            // Включаем вывод данных
            con.setDoOutput(true);

            // Получаем поток для записи данных
            OutputStream os = con.getOutputStream();

            // Записываем данные в поток
            os.write(data.getBytes());
            os.flush();
            os.close();

            // Получаем код ответа от сервера
            int responseCode = con.getResponseCode();

            // Создаем BufferedReader для чтения ответа от сервера
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            // Читаем ответ от сервера
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Выводим ответ от сервера
            System.out.println(response.toString());
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "null";
    }
}
