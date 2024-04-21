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

import com.cripochec.Flopy.ui.utils.PersonInfo;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.main;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentRegisterCode extends Fragment {
    private int code;
    private boolean status;
    private int id_person;
    private String email;
    private String password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_code, container, false);

        Button but_send = rootView.findViewById(R.id.buttonCodeSend);
        TextView email_text_view = rootView.findViewById(R.id.emailTextView);
        EditText edit_code = rootView.findViewById(R.id.editCode);

        // Получение данных из Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.code = bundle.getInt("code");
            this.email = bundle.getString("email");
            this.password = bundle.getString("password");
        }

        email_text_view.setText(email);

        but_send.setOnClickListener(v -> {
            String enteredCode = edit_code.getText().toString();
            // Обработка нажатия на кнопку but_send
            if (!enteredCode.isEmpty() && Integer.parseInt(enteredCode) == code) {
                new RequestUtils(FragmentRegisterCode.this, "add_new_person_route", "POST", "{\"email\": \"" + email + "\", \"password\": \"" + password +"\"}").execute();

                new android.os.Handler().postDelayed(
                        () -> {
                            if (status){
                                Intent intent = new Intent(requireContext(), main.class);
                                PersonInfo.saveData(requireContext(), id_person, false);
                                // Запускаем новую активность
                                startActivity(intent);
                            } else {
                                ToastUtils.showShortToast(getContext(), "Ошибка, попробуйте заново");
                                edit_code.setText("");
                            }
                        },
                        2000
                );
            } else {
                ToastUtils.showShortToast(getContext(), "Неверный код");
                edit_code.setText("");
            }
        });
        return rootView;
    }

    // Метод для обновления данных
    public void updateData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getBoolean("status");
            this.id_person = jsonObject.getInt("id_person");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleEmptyResponse() {
        ToastUtils.showShortToast(getContext(), "Ошибка сервера, попробуйте заново");
    }
}