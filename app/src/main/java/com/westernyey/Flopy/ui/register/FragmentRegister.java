package com.westernyey.Flopy.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.Register.RegisterRequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.westernyey.Flopy.R;


public class FragmentRegister extends Fragment {

    // Переменные для хранения данных
    private int code;
    private boolean status;
    private int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        Button but_register = rootView.findViewById(R.id.buttonRegisterUser);

        EditText new_email = rootView.findViewById(R.id.editEmailRegister);
        EditText pas1 = rootView.findViewById(R.id.editPassword1Register);
        EditText pas2 = rootView.findViewById(R.id.editPassword2Register);

        // Обработка нажатия на кнопку but_register
        but_register.setOnClickListener(v -> {
            // Проверка на запониность
            if (!new_email.getText().toString().isEmpty() && !pas1.getText().toString().isEmpty() && !pas2.getText().toString().isEmpty()) {
                // Проверка на длину
                if (pas1.getText().toString().length() >= 8 && pas2.getText().toString().length() >= 8) {
                    // Проверка на совпадение паролей
                    if (pas1.getText().toString().equals(pas2.getText().toString())) {
                        new RegisterRequestUtils(this).execute(new_email.getText().toString(), pas1.getText().toString());
                        if (status){
                            // Передача данных в FragmentRegisterCode
                            Fragment fragment = new FragmentRegisterCode();
                            Bundle bundle = new Bundle();
                            bundle.putInt("code", code);
                            bundle.putInt("userId", userId);
                            bundle.putString("email", new_email.getText().toString());
                            fragment.setArguments(bundle);
                            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
                        } else {
                            ToastUtils.showShortToast(getContext(), "Данный email уже зарегистрирован");
                            new_email.setText("");
                            pas1.setText("");
                            pas2.setText("");
                        }
                    } else {
                        ToastUtils.showShortToast(getContext(), "Пароли не совпадают");
                        pas1.setText("");
                        pas2.setText("");
                    }
                } else {
                    ToastUtils.showShortToast(getContext(), "Минимальня длина пароля 8 символов");
                    pas1.setText("");
                    pas2.setText("");
                }
            } else {
                ToastUtils.showShortToast(getContext(), "Все поля должны быть заполнены");
            }
        });

        return rootView;
    }

    // Метод для обновления данных
    public void updateData(int code, boolean status, int userId) {
        this.code = code;
        this.status = status;
        this.userId = userId;
        // Дальнейшие действия с данными
    }
}