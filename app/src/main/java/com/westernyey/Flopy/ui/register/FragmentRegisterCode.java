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

import com.cripochec.Flopy.ui.utils.JsonUtils.JsonUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.ActivityMain;

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
                                Intent intent = new Intent(requireContext(), ActivityMain.class);
                                JsonUtils.saveID(requireContext(), id_person);
                                JsonUtils.saveEntry(requireContext(), true);
                                // Запускаем новую активность
                                startActivity(intent);
                            } else {
                                ToastUtils.showShortToast(getContext(), "Ошибка, попробуйте заново");
                                edit_code.setText("");
                            }
                        },
                        3000
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
            if (jsonObject.has("status") && jsonObject.has("id_person")) {
                this.status = jsonObject.getBoolean("status");
                this.id_person = jsonObject.getInt("id_person");
            } else {
                // Обработка случая, когда ключи "status" и "id_person" отсутствуют в ответе
                handleEmptyResponse();
            }
        } catch (JSONException e) {
            // Обработка ошибки парсинга JSON
            handleJsonParsingError(e);
        }
    }

    private void handleEmptyResponse() {
        ToastUtils.showShortToast(getContext(), "Ошибка сервера: отсутствуют данные");
    }

    private void handleJsonParsingError(JSONException e) {
        e.printStackTrace();
        ToastUtils.showShortToast(getContext(), "Ошибка парсинга данных: " + e.getMessage());
    }
}