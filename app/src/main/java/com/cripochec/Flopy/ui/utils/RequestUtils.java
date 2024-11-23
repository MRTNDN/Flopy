package com.cripochec.Flopy.ui.utils;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestUtils {
    private final OkHttpClient client = new OkHttpClient();
    private final WeakReference<Fragment> fragmentRef;
    private final String requestLine;
    private final String method;
    private final String data;
    private final List<File> files;  // Для файлов
    private final Callback callback;

    // Конструктор для обычных запросов (без файлов)
    public RequestUtils(Fragment fragment, String requestLine, String method, String data, Callback callback) {
        this.fragmentRef = new WeakReference<>(fragment);
        this.requestLine = requestLine;
        this.method = method;
        this.data = data;
        this.files = null;  // Нет файлов
        this.callback = callback;
    }

    // Конструктор для запросов с файлами
    public RequestUtils(Fragment fragment, String requestLine, String method, String data, List<File> files, Callback callback) {
        this.fragmentRef = new WeakReference<>(fragment);
        this.requestLine = requestLine;
        this.method = method;
        this.data = data;
        this.files = files;  // Список файлов
        this.callback = callback;
    }

    public void execute() {
        String URL_SERVER = DataUtils.IP + requestLine;

        Request.Builder requestBuilder = new Request.Builder().url(URL_SERVER);

        RequestBody requestBody;
        if (files != null && !files.isEmpty()) {
            // Если есть файлы, создаём multipart body
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            // Добавляем JSON данные как часть multipart
            if (data != null && !data.isEmpty()) {
                multipartBuilder.addFormDataPart("json", data);
            }

            // Добавляем файлы
            for (File file : files) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                multipartBuilder.addFormDataPart("photo", file.getName(), fileBody);
            }

            requestBody = multipartBuilder.build();
            requestBuilder.method(method, requestBody);
        } else {
            // Если файлов нет, отправляем обычный JSON
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data);
            requestBuilder.method(method.equals("GET") ? method : method, method.equals("GET") ? null : requestBody);
            requestBuilder.addHeader("Content-Type", "application/json");
        }

        Request request = requestBuilder.build();

        // Отправляем запрос асинхронно
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Обрабатываем ситуацию, когда запрос не удался
                handleResponse("ERROR " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Обрабатываем успешный ответ
                if (response.isSuccessful()) {
                    handleResponse(response.body().string());
                } else {
                    handleResponse("ERROR " + response.code() + " " + response.message());
                }
            }
        });
    }

    private void handleResponse(String result) {
        Fragment fragment = fragmentRef.get();
        if (fragment != null) {
            callback.onResponse(fragment, result);
        }
    }

    public interface Callback {
        void onResponse(Fragment fragment, String result);
    }
}
