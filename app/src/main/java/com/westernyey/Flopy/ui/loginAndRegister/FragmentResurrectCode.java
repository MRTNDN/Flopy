package com.westernyey.Flopy.ui.loginAndRegister;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;

import org.json.JSONObject;

public class FragmentResurrectCode extends Fragment {
    private int code; // Переменная для хранения кода подтверждения
    private String email; // Переменная для хранения email пользователя
    private EditText edit_code; // Поле для ввода кода подтверждения

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Инициализация интерфейса фрагмента
        View rootView = inflater.inflate(R.layout.fragment_resurrect_code, container, false);

        Button but_send = rootView.findViewById(R.id.buttonCodeSend); // Кнопка для отправки кода
        TextView email_text_view = rootView.findViewById(R.id.emailTextView); // Текстовое поле для отображения email
        TextView timer_code_text_view = rootView.findViewById(R.id.textTimerCode); // Таймер для повторной отпраки кода
        edit_code = rootView.findViewById(R.id.editCode); // Поле для ввода кода подтверждения

        // Получение данных из Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.code = bundle.getInt("code");
            this.email = bundle.getString("email");
        }

        email_text_view.setText(email); // Отображаем email в текстовом поле

        timer_code_text_view.setEnabled(false); // Отключаем нажатие
        timer_code_text_view.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_overlay)); // Делаем текст серым
        startTimer(timer_code_text_view); // Запускаем таймер

        // Обработка нажатия на кнопку отправки кода
        but_send.setOnClickListener(v -> {
            try {
                String enteredCode = edit_code.getText().toString(); // Получаем введенный код

                if (!TextUtils.isEmpty(enteredCode) && Integer.parseInt(enteredCode) == code) {
                    Fragment fragment = new FragmentNewPassword(); // Создаем новый фрагмент для создания нового пароля

                    // Передаем данные в новый фрагмент через Bundle
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("email", email);
                    fragment.setArguments(bundle2);

                    // Заменяем текущий фрагмент на фрагмент ввода кода
                    FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
                } else {
                    // Вывод сообщения о неверном коде
                    ToastUtils.showShortToast(getContext(), "Неверный код");
                    edit_code.setText(""); // Очистка поля ввода кода
                }
            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentRegisterCode\", \"method\": \"but_send.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });

        // Обработка нажатия на кнопку отправки повторной отправки кода
        timer_code_text_view.setOnClickListener(v -> {
            try {
                edit_code.setText(""); // Очистка поля ввода кода

                // Отправка запроса на сервер для регистрации
                JSONObject loginData = new JSONObject();
                loginData.put("email", email);

                timer_code_text_view.setEnabled(false); // Отключаем нажатие
                timer_code_text_view.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_overlay)); // Делаем текст серым
                startTimer(timer_code_text_view); // Запускаем таймер

                new RequestUtils(this, "one_time_code", "POST", loginData.toString(), callbackOneTimeCode).execute();

            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentResurrectCode\", \"method\": \"timer_code_text_view.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });

        return rootView; // Возвращаем корневой вид фрагмента
    }


    private void startTimer(TextView timerText) {
        new CountDownTimer(40000, 1000) { // 40 секунд с шагом в 1 секунду
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timerText.setText("Повторно через " + (millisUntilFinished / 1000) + "с.");
            }

            public void onFinish() {
                timerText.setText("Отправить повторно");
                timerText.setTextColor(ContextCompat.getColor(requireContext(), R.color.white)); // Делаем текст белым
                timerText.setEnabled(true); // Разблокируем кнопку
            }
        }.start();
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
    RequestUtils.Callback callbackOneTimeCode = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);

            this.code = jsonObject.getInt("code");

        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentResurrectCode\", \"method\": \"callbackOneTimeCode\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
