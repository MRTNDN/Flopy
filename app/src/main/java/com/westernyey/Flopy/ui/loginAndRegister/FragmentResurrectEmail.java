package com.westernyey.Flopy.ui.loginAndRegister;

import android.os.Bundle;
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

public class FragmentResurrectEmail extends Fragment {

    private EditText emailPerson; // Поля для ввода нового email и паролей

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Инициализация интерфейса фрагмента
        View rootView = inflater.inflate(R.layout.fragment_resurrect_email, container, false);

        // Инициализация элементов интерфейса
        Button but_send_code = rootView.findViewById(R.id.buttonSendCode); // Кнопка отправки кода
        emailPerson = rootView.findViewById(R.id.editEmail);


        // Обработка нажатия на кнопку регистрации
        but_send_code.setOnClickListener(v -> {
            try {
                String email = emailPerson.getText().toString();

                if (!email.equals("")){
                    // Отправка запроса на сервер для востановления пароля
                    JSONObject loginData = new JSONObject();
                    loginData.put("email", email);

                    new RequestUtils(this, "resurrect_email", "POST", loginData.toString(), callbackResurrectEmail).execute();
                } else {
                    ToastUtils.showShortToast(getContext(), "Введите email");
                }

            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentResurrectEmail\", \"method\": \"but_send_code.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
    RequestUtils.Callback callbackResurrectEmail = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int status = jsonObject.getInt("status"); // Получаем статус из ответа
            // status
            // 0 - email не существует
            // 1 - успешно
            // ~ - ошибка сервера
            if (status == 0){
                showErrorToast("Не существует пользователя с таким email");
                clearFields(emailPerson);
            }

            else if (status == 1){
                fragment = new FragmentResurrectCode(); // Создаем новый фрагмент для ввода кода верификации

                // Передаем данные в новый фрагмент через Bundle
                Bundle bundle = new Bundle();
                bundle.putInt("code", jsonObject.getInt("code"));
                bundle.putString("email", emailPerson.getText().toString());
                fragment.setArguments(bundle);

                // Заменяем текущий фрагмент на фрагмент ввода кода
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
            }

            else {
                showErrorToast("Ошибка на стороне сервера ERROR: "+status);
                clearFields(emailPerson);
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentResurrectEmail\", \"method\": \"callbackResurrectEmail\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
