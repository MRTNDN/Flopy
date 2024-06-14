package com.westernyey.Flopy.ui.register;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentRegisterCode extends Fragment {
    private int code; // Переменная для хранения кода подтверждения
    private int status; // Переменная для хранения статуса ответа сервера
    private String email; // Переменная для хранения email пользователя
    private String password; // Переменная для хранения пароля пользователя
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
            String enteredCode = edit_code.getText().toString(); // Получаем введенный код

            if (!enteredCode.isEmpty() && Integer.parseInt(enteredCode) == code) {
                // Отправка запроса на сервер для добавления нового пользователя
                new RequestUtils(this, "add_new_person_route", "POST",
                        "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}", callback).execute();
            } else {
                // Вывод сообщения о неверном коде
                ToastUtils.showShortToast(getContext(), "Неверный код");
                edit_code.setText(""); // Очистка поля ввода кода
            }
        });

        return rootView; // Возвращаем корневой вид фрагмента
    }

    // Обработка ответа от сервера
    RequestUtils.Callback callback = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status"); // Получаем статус из ответа

            if (status == 0) {
                // Если статус 0, регистрация успешна
                int id_person = jsonObject.getInt("id_person"); // Получаем ID пользователя
                DataUtils.saveUserId(requireContext(), id_person); // Сохраняем ID пользователя

                // Переход на главную активность
                Intent intent = new Intent(requireContext(), ActivityMain.class);
                startActivity(intent);
            } else {
                // Обработка остальных случаев
                handleEmptyResponse(); // Обработка пустого ответа
                edit_code.setText(""); // Очистка поля ввода кода
            }
        } catch (JSONException e) {
            throw new RuntimeException(e); // Обработка исключения JSON
        }
    };

    // Обработка пустого ответа от сервера
    public void handleEmptyResponse() {
        requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(),
                "Ошибка сервера, попробуйте заново." + "\nКод: " + status)); // Показываем сообщение об ошибке
    }
}
