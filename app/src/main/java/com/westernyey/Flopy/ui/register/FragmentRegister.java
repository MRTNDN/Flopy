package com.westernyey.Flopy.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;


public class FragmentRegister extends Fragment {

    // Переменные для хранения данных
    private int code;
    private boolean status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        Button but_register = rootView.findViewById(R.id.buttonRegisterUser);
        EditText new_email = rootView.findViewById(R.id.editEmailRegister);
        EditText pas1 = rootView.findViewById(R.id.editPassword1Register);
        EditText pas2 = rootView.findViewById(R.id.editPassword2Register);

        // Обработка нажатия на кнопку but_register
        but_register.setOnClickListener(v -> {
            if (!new_email.getText().toString().isEmpty() && !pas1.getText().toString().isEmpty() && !pas2.getText().toString().isEmpty()) {
                if (pas1.getText().toString().length() >= 1 && pas2.getText().toString().length() >= 1) {
                    if (pas1.getText().toString().equals(pas2.getText().toString())) {
//                        new RequestUtils(FragmentRegister.this, "entry_email", "POST", "{\"email\": \"" + new_email.getText().toString() + "\"}").execute();
                        new android.os.Handler().postDelayed(
                                () -> {
                                    if (status){
                                        Fragment fragment = new FragmentRegisterCode();
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("code", code);
                                        bundle.putString("email", new_email.getText().toString());
                                        bundle.putString("password", pas1.getText().toString());
                                        fragment.setArguments(bundle);
                                        FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
                                    } else {
                                        ToastUtils.showShortToast(getContext(), "Данный email уже зарегистрирован");
                                        new_email.setText("");
                                        pas1.setText("");
                                        pas2.setText("");
                                    }
                                },
                                2000
                        );
                    } else {
                        ToastUtils.showShortToast(getContext(), "Пароли не совпадают");
                        pas1.setText("");
                        pas2.setText("");
                    }
                } else {
                    ToastUtils.showShortToast(getContext(), "Минимальная длина пароля 8 символов");
                    pas1.setText("");
                    pas2.setText("");
                }
            } else {
                ToastUtils.showShortToast(getContext(), "Все поля должны быть заполнены");
            }
        });


        return rootView;
    }

//    // Метод для обновления данных
//    public void updateData(String result) {
//        try {
//            JSONObject jsonObject = new JSONObject(result);
//            this.code = jsonObject.getInt("code");
//            this.status = jsonObject.getBoolean("status");
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void handleEmptyResponse() {
        ToastUtils.showShortToast(getContext(), "Ошибка сервера, попробуйте заново");
    }
}