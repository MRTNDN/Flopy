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

public class FragmentRegister extends Fragment {

    private EditText new_email, pas1, pas2; // Поля для ввода нового email и паролей

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Инициализация интерфейса фрагмента
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        // Инициализация элементов интерфейса
        Button but_register = rootView.findViewById(R.id.buttonNext); // Кнопка регистрации
        new_email = rootView.findViewById(R.id.editEmailRegister);
        pas1 = rootView.findViewById(R.id.editPassword1);
        pas2 = rootView.findViewById(R.id.editPassword2);

        // Обработка нажатия на кнопку регистрации
        but_register.setOnClickListener(v -> {
            try {
                String email = new_email.getText().toString();
                String pass1 = pas1.getText().toString();
                String pass2 = pas2.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass1) && !TextUtils.isEmpty(pass2)) {
                    // Проверка минимальной длины паролей
                    if (pass1.length() >= 8 && pass2.length() >= 8) {
                        // Проверка совпадения паролей
                        if (pass1.equals(pass2)) {
                            // Отправка запроса на сервер для регистрации
                            JSONObject loginData = new JSONObject();
                            loginData.put("email", email);

                            new RequestUtils(this, "register_person", "POST", loginData.toString(), callbackRegisterPerson).execute();
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
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentRegister\", \"method\": \"but_register.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
    RequestUtils.Callback callbackRegisterPerson = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int status = jsonObject.getInt("status"); // Получаем статус из ответа
            // status
            // 0 - успешно
            // 1 - email уже занят
            // ~ - ошибка сервера
            if (status == 0){

                fragment = new FragmentRegisterCode(); // Создаем новый фрагмент для ввода кода верификации

                // Передаем данные в новый фрагмент через Bundle
                Bundle bundle = new Bundle();
                bundle.putInt("code", jsonObject.getInt("code"));
                bundle.putString("email", new_email.getText().toString());
                bundle.putString("password", pas1.getText().toString());
                fragment.setArguments(bundle);

                // Заменяем текущий фрагмент на фрагмент ввода кода
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
            }

            else if (status == 1){
                showErrorToast("Данный email уже занят");
                clearFields(new_email, pas1, pas2);
            }

            else {
                showErrorToast("Ошибка на стороне сервера ERROR: "+status);
                clearFields(new_email, pas1, pas2);
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentRegister\", \"method\": \"callbackRegisterPerson\", \"error\": \"" + e + "\"}", callbackLog).execute();
            EmptyResponse();
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
