package com.westernyey.Flopy.ui.register;

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

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentRegister extends Fragment {

    private int status; // Переменная для хранения статуса ответа сервера
    private EditText new_email, pas1, pas2; // Поля для ввода нового email и паролей

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Инициализация интерфейса фрагмента
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        // Инициализация элементов интерфейса
        Button but_register = rootView.findViewById(R.id.buttonRegisterUser); // Кнопка регистрации
        new_email = rootView.findViewById(R.id.editEmailRegister);
        pas1 = rootView.findViewById(R.id.editPassword1Register);
        pas2 = rootView.findViewById(R.id.editPassword2Register);

        // Обработка нажатия на кнопку регистрации
        but_register.setOnClickListener(v -> {
            if (!new_email.getText().toString().isEmpty() && !pas1.getText().toString().isEmpty() && !pas2.getText().toString().isEmpty()) {
                // Проверка минимальной длины паролей
                if (pas1.getText().toString().length() >= 8 && pas2.getText().toString().length() >= 8) {
                    // Проверка совпадения паролей
                    if (pas1.getText().toString().equals(pas2.getText().toString())) {
                        // Отправка запроса на сервер для регистрации
                        new RequestUtils(this, "register_person", "POST",
                                "{\"email\": \"" + new_email.getText().toString() + "\"}", callback).execute();
                    } else {
                        // Вывод сообщения о несовпадении паролей
                        ToastUtils.showShortToast(getContext(), "Пароли не совпадают");
                        clearEditPassword(); // Очистка полей ввода паролей
                    }
                } else {
                    // Вывод сообщения о минимальной длине паролей
                    ToastUtils.showShortToast(getContext(), "Минимальная длина пароля 8 символов");
                    clearEditPassword(); // Очистка полей ввода паролей
                }
            } else {
                // Вывод сообщения о незаполненных полях
                ToastUtils.showShortToast(getContext(), "Все поля должны быть заполнены");
            }
        });

        return rootView; // Возвращаем корневой вид фрагмента
    }

    // Обработка ответа от сервера
    RequestUtils.Callback callback = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status"); // Получаем статус из ответа
            if (status == 0){
                // Если статус 0, регистрация успешна
                int code = jsonObject.getInt("code"); // Получаем код для верификации

                fragment = new FragmentRegisterCode(); // Создаем новый фрагмент для ввода кода верификации

                // Передаем данные в новый фрагмент через Bundle
                Bundle bundle = new Bundle();
                bundle.putInt("code", code);
                bundle.putString("email", new_email.getText().toString());
                bundle.putString("password", pas1.getText().toString());
                fragment.setArguments(bundle);

                // Заменяем текущий фрагмент на фрагмент ввода кода
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
            } else if (status == 1){
                // Если статус 1, email уже занят
                requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(), "Данный email уже занят"));
                clearEditText(); // Очистка всех полей ввода
            } else {
                // Обработка остальных случаев
                handleEmptyResponse(); // Обработка пустого ответа
                clearEditText(); // Очистка всех полей ввода
            }
        } catch (JSONException e) {
            throw new RuntimeException(e); // Обработка исключения JSON
        }
    };

    // Очистка всех полей для ввода текста
    public void clearEditText() {
        new_email.setText("");
        pas1.setText("");
        pas2.setText("");
    }

    // Очистка полей для ввода паролей
    public void clearEditPassword() {
        pas1.setText("");
        pas2.setText("");
    }

    // Обработка пустого ответа от сервера
    public void handleEmptyResponse() {
        requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(),
                "Ошибка сервера, попробуйте заново." + "\nКод: " + status)); // Показываем сообщение об ошибке
    }
}
