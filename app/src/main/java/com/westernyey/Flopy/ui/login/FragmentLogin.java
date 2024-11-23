package com.westernyey.Flopy.ui.login;

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
import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.ActivityMain;
import com.westernyey.Flopy.ui.register.FragmentRegister;

import org.json.JSONObject;

public class FragmentLogin extends Fragment {

    private EditText email, password; // Поле для ввода email и пароля

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        email = rootView.findViewById(R.id.editEmail); // Находим поле для ввода email
        password = rootView.findViewById(R.id.editPassword); // Находим поле для ввода пароля
        Button but_login = rootView.findViewById(R.id.buttonLogin); // Находим кнопку для входа
        TextView but_register = rootView.findViewById(R.id.textRegister); // Находим текст для перехода к регистрации

        but_login.setOnClickListener(v -> {
            try {
                // Обработка нажатия на кнопку but_login
                String enteredEmail = email.getText().toString(); // Получаем введенный email
                String enteredPassword = password.getText().toString(); // Получаем введенный пароль

                if (!TextUtils.isEmpty(enteredEmail) && !TextUtils.isEmpty(enteredPassword)){

                    // Отправляем запрос на сервер для входа
                    JSONObject loginData = new JSONObject();
                    loginData.put("email", enteredEmail);
                    loginData.put("password", enteredPassword);
                    new RequestUtils(this, "entry_person", "POST", loginData.toString(), callbackEntryPerson).execute();

                } else {
                    // Показываем сообщение об ошибке, если поля пусты
                    ToastUtils.showShortToast(getContext(), "Все поля должны быть заполнены");
                    clearEditText(); // Очищаем поля ввода
                }
            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentLogin\", \"method\": \"but_login.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }
        });

        but_register.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but_register
            Fragment fragment = new FragmentRegister(); // Создаем новый фрагмент для регистрации
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment); // Заменяем текущий фрагмент на фрагмент регистрации
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
    RequestUtils.Callback callbackEntryPerson = (fragment, result) -> {
        try {
            // Получение JSON объекта из ответа сервера
            JSONObject jsonObject = new JSONObject(result);
            int status = jsonObject.getInt("status"); // Получаем статус из ответа

            // status
            // 0 - успешно
            // 1 - email не найден
            // 2 - неверный логин или пароль
            // ~ - ошибка сервера
            if (status == 0){
                DataUtils.saveUserId(requireContext(), jsonObject.getInt("id_person")); // Сохраняем ID пользователя
                DataUtils.saveEntry(requireContext(), false); // Сохраняем информацию о входе

                Intent intent = new Intent(requireContext(), ActivityMain.class);
                startActivity(intent); // Запускаем основную активность
            }

            else if (status == 1){
                showErrorToast("email не найден");
                clearEditText(); // Очищаем поля ввода
            }

            else if (status == 2){
                showErrorToast("Неверный логин или пароль");
                clearEditText(); // Очищаем поля ввода
            }

            else {
                showErrorToast("Ошибка на стороне сервера ERROR: "+status);
                clearEditText(); // Очищаем поля ввода
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentLogin\", \"method\": \"callbackEntryPerson\", \"error\": \"" + e + "\"}", callbackLog).execute();
            EmptyResponse();
        }
    };

    // Очистка полей для ввода текста
    public void clearEditText() {
        email.setText(""); // Очищаем поле email
        password.setText(""); // Очищаем поле пароля
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
