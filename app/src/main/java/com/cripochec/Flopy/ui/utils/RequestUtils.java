package com.cripochec.Flopy.ui.utils;

import androidx.fragment.app.Fragment;
import java.io.IOException;
import java.lang.ref.WeakReference;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Call;


public class RequestUtils {
    private final OkHttpClient client = new OkHttpClient();
    private final WeakReference<Fragment> fragmentRef;
    private final String requestLine;
    private final String method;
    private final String data;
    private final Callback callback;

    public RequestUtils(Fragment fragment, String requestLine, String method, String data, Callback callback) {
        this.fragmentRef = new WeakReference<>(fragment);
        this.requestLine = requestLine;
        this.method = method;
        this.data = data;
        this.callback = callback;
    }

    public void execute() {
//        локалка
        String URL_SERVER = "http://192.168.0.32:5000/" + requestLine;

//        сервер
//        String URL_SERVER = "http://90.156.231.211:5000/" + requestLine;

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data);
        Request.Builder requestBuilder = new Request.Builder()
                .url(URL_SERVER);
        // Указываем requestBody только для POST, PUT, PATCH и т.д.
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