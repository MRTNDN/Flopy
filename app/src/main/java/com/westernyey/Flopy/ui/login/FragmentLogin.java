package com.westernyey.Flopy.ui.login;

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
import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.ActivityMain;
import com.westernyey.Flopy.ui.register.FragmentRegister;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentLogin extends Fragment {

    private EditText email; // Поле для ввода email
    private EditText password; // Поле для ввода пароля
    private int status; // Переменная для хранения статуса ответа сервера

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Инициализация интерфейса фрагмента
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        email = rootView.findViewById(R.id.editEmail); // Находим поле для ввода email
        password = rootView.findViewById(R.id.editPassword); // Находим поле для ввода пароля
        Button but_login = rootView.findViewById(R.id.buttonLogin); // Находим кнопку для входа
        TextView but_register = rootView.findViewById(R.id.textRegister); // Находим текст для перехода к регистрации

        but_login.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but_login
            String enteredEmail = email.getText().toString(); // Получаем введенный email
            String enteredPassword = password.getText().toString(); // Получаем введенный пароль

            if (!enteredEmail.isEmpty() && !enteredPassword.isEmpty()){
                // Отправляем запрос на сервер для входа
                new RequestUtils(this, "entry_person", "POST",
                        "{\"email\": \""+enteredEmail+"\", \"password\": \""+enteredPassword+"\"}", callback).execute();
            } else {
                // Показываем сообщение об ошибке, если поля пусты
                ToastUtils.showShortToast(getContext(), "Все поля должны быть заполнены");
                clearEditText(); // Очищаем поля ввода
            }
        });

        but_register.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but_register
            Fragment fragment = new FragmentRegister(); // Создаем новый фрагмент для регистрации
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment); // Заменяем текущий фрагмент на фрагмент регистрации
        });

        return rootView; // Возвращаем корневой вид фрагмента
    }

    // Обработка ответа от сервера
    RequestUtils.Callback callback = (fragment, result) -> {
        try {
            // Получение JSON объекта из ответа сервера
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status"); // Получаем статус из ответа

            if (status == 0){
                // Если статус 0, вход успешен
                int id_person = jsonObject.getInt("id_person"); // Получаем ID пользователя
                DataUtils.saveUserId(requireContext(), id_person); // Сохраняем ID пользователя
                DataUtils.saveEntry(requireContext(), false); // Сохраняем информацию о входе

                Intent intent = new Intent(requireContext(), ActivityMain.class); // Создаем интент для перехода в основную активность
                startActivity(intent); // Запускаем основную активность
            } else if (status == 1){
                // Если статус 1, email не найден
                requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(), "email не найден")); // Показываем сообщение об ошибке
                clearEditText(); // Очищаем поля ввода
            } else if (status == 2){
                // Если статус 2, неверный логин или пароль
                requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(), "Неверный логин или пароль")); // Показываем сообщение об ошибке
                clearEditText(); // Очищаем поля ввода
            } else {
                // Во всех остальных случаях, ошибка сервера
                handleEmptyResponse(); // Обрабатываем пустой ответ
                clearEditText(); // Очищаем поля ввода
            }
        } catch (JSONException e) {
            // Обрабатываем исключение JSON
            throw new RuntimeException(e);
        }
    };

    // Очистка полей для ввода текста
    public void clearEditText() {
        email.setText(""); // Очищаем поле email
        password.setText(""); // Очищаем поле пароля
    }

    // Обработка пустого ответа от сервера
    public void handleEmptyResponse() {
        requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(),
                "Ошибка сервера, попробуйте заново."+ "\nКод: "+status)); // Показываем сообщение об ошибке
    }
}
