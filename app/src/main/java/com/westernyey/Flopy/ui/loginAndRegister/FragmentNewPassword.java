package com.westernyey.Flopy.ui.loginAndRegister;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;

import org.json.JSONObject;

public class FragmentNewPassword extends Fragment {

    private EditText pas1, pas2; // Поля для ввода нового email и паролей
    private String email; // Переменная для хранения email пользователя

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Инициализация интерфейса фрагмента
        View rootView = inflater.inflate(R.layout.fragment_new_password, container, false);

        // Инициализация элементов интерфейса
        Button but_next = rootView.findViewById(R.id.buttonNext); // Кнопка далее
        pas1 = rootView.findViewById(R.id.editPassword1);
        pas2 = rootView.findViewById(R.id.editPassword2);

        // Получение данных из Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.email = bundle.getString("email");
        }

        // Обработка нажатия на кнопку далее
        but_next.setOnClickListener(v -> {
            try {
                String pass1 = pas1.getText().toString();
                String pass2 = pas2.getText().toString();

                if (!TextUtils.isEmpty(pass1) && !TextUtils.isEmpty(pass2)) {
                    // Проверка минимальной длины паролей
                    if (pass1.length() >= 8 && pass2.length() >= 8) {
                        // Проверка совпадения паролей
                        if (pass1.equals(pass2)) {
                            // Отправка запроса на сервер для регистрации
                            JSONObject loginData = new JSONObject();
                            loginData.put("email", email);
                            loginData.put("password", pass1);

                            new RequestUtils(this, "new_password", "POST", loginData.toString(), callbackNewPassword).execute();
                        } else {
                            ToastUtils.showShortToast(getContext(), "Пароли не совпадают");
                            clearFields(pas1, pas2);
                        }
                    } else {
                        ToastUtils.showShortToast(getContext(), "Минимальная длина пароля 8 символов");
                        clearFields(pas1, pas2);
                    }
                } else {
                    ToastUtils.showShortToast(getContext(), "Все поля должны быть заполнены");
                }
            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentNewPassword\", \"method\": \"but_next.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });

        return rootView;
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
    RequestUtils.Callback callbackNewPassword = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int status = jsonObject.getInt("status"); // Получаем статус из ответа

            requireActivity().runOnUiThread(() -> {
                if (status == 0) {
                    showErrorToast("Успешно");
                    Fragment fragmentLogin = new FragmentLogin();
                    FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragmentLogin);
                } else {
                    showErrorToast("Ошибка на стороне сервера ERROR: " + status);
                    clearFields(pas1, pas2);
                }
            });

        } catch (Exception e) {
            requireActivity().runOnUiThread(() -> {
                new RequestUtils(this, "log", "POST",
                        "{\"module\": \"FragmentNewPassword\", \"method\": \"callbackNewPassword\", \"error\": \"" + e + "\"}",
                        callbackLog).execute();
                EmptyResponse();
            });
        }
    };

    // Очистка всех полей
    private void clearFields(EditText... fields) {
        for (EditText field : fields) {
            field.setText("");
        }
    }

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
