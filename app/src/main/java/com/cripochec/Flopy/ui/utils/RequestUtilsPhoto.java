package com.cripochec.Flopy.ui.utils;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RequestUtilsPhoto {
    private final OkHttpClient client = new OkHttpClient();
    private final WeakReference<Fragment> fragmentRef;
    private final String requestLine;
    private final String method;
    private final File file;  // Вместо строки данных будем передавать файл
    private final int idPerson;
    private final int dominating;
    private final Callback callback;

    public RequestUtilsPhoto(Fragment fragment, String requestLine, String method, File file, int idPerson, int dominating, Callback callback) {
        this.fragmentRef = new WeakReference<>(fragment);
        this.requestLine = requestLine;
        this.method = method;
        this.file = file;
        this.idPerson = idPerson;
        this.dominating = dominating;
        this.callback = callback;
    }

    public void execute() {
        String URL_SERVER = "http://192.168.0.104:5000/" + requestLine;

        // Формируем multipart body
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_person", String.valueOf(idPerson))
                .addFormDataPart("dominating", String.valueOf(dominating))
                .addFormDataPart("photo", file.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), file))  // Передаем файл
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                .url(URL_SERVER)
                .addHeader("Content-Type", "multipart/form-data");  // Заголовок multipart/form-data

        // Указываем requestBody для POST-запросов
        if (!method.equals("GET")) {
            requestBuilder.method(method, requestBody);
        } else {
            requestBuilder.method(method, null);
        }

        Request request = requestBuilder.build();

        // Отправляем запрос асинхронно
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handleResponse("ERROR " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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