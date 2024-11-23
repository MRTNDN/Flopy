package com.westernyey.Flopy.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.DataUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.ActivityMain;

import org.json.JSONObject;

public class FragmentRegisterCode extends Fragment {
    private int code; // Переменная для хранения кода подтверждения
    private String email, password; // Переменные для хранения email и пароля пользователя
    private EditText edit_code; // Поле для ввода кода подтверждения

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Инициализация интерфейса фрагмента
        View rootView = inflater.inflate(R.layout.fragment_register_code, container, false);

        Button but_send = rootView.findViewById(R.id.buttonCodeSend); // Кнопка для отправки кода
        TextView email_text_view = rootView.findViewById(R.id.emailTextView); // Текстовое поле для отображения email
        edit_code = rootView.findViewById(R.id.editCode); // Поле для ввода кода подтверждения

        // Получение данных из Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.code = bundle.getInt("code");
            this.email = bundle.getString("email");
            this.password = bundle.getString("password");
        }

        email_text_view.setText(email); // Отображаем email в текстовом поле

        // Обработка нажатия на кнопку отправки кода
        but_send.setOnClickListener(v -> {
            try {
                String enteredCode = edit_code.getText().toString(); // Получаем введенный код

                if (!TextUtils.isEmpty(enteredCode) && Integer.parseInt(enteredCode) == code) {
                    // Отправка запроса на сервер для добавления нового пользователя
                    JSONObject loginData = new JSONObject();
                    loginData.put("email", email);
                    loginData.put("password", password);

                    new RequestUtils(this, "add_new_person_route", "POST", loginData.toString(), callbackNewPerson).execute();
                } else {
                    // Вывод сообщения о неверном коде
                    ToastUtils.showShortToast(getContext(), "Неверный код");
                    edit_code.setText(""); // Очистка поля ввода кода
                }
            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentRegisterCode\", \"method\": \"but_send.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });

        return rootView; // Возвращаем корневой вид фрагмента
    }

    // Обработка логирования на сервере
    RequestUtils.Callback callbackLog = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") != 0){
                showErrorToast("Ошибка логирования на сервере.");
            }
        } catch (Exception e) {
            showErrorToast("Ошибка логирования на клиенте.");
        }
    };

    // Обработка ответа от сервера
    RequestUtils.Callback callbackNewPerson = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            // status
            // 0 - успешно
            // ~ - ошибка сервера
            if (jsonObject.getInt("status") == 0) {
                DataUtils.saveUserId(requireContext(), jsonObject.getInt("id_person")); // Сохраняем ID пользователя

                // Переход на главную активность
                Intent intent = new Intent(requireContext(), ActivityMain.class);
                startActivity(intent);
            } else {
                showErrorToast("Ошибка на стороне сервера ERROR: "+jsonObject.getInt("status"));
                edit_code.setText(""); // Очистка поля ввода кода
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentRegisterCode\", \"method\": \"callbackNewPerson\", \"error\": \"" + e + "\"}", callbackLog).execute();
            EmptyResponse();
        }
    };

    // Обработка ошибки запроса
    public void EmptyResponse() {
        requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(),
                "Ошибка callback.")); // Показываем сообщение об ошибке
    }

    // Метод для показа сообщения об ошибке
    private void showErrorToast(String message) {
        requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(), message));
    }
}
