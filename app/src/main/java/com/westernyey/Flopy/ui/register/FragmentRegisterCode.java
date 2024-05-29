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

import com.cripochec.Flopy.ui.utils.DataUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.ActivityMain;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentRegisterCode extends Fragment {
    private int code;
    private int status;
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
            ToastUtils.showShortToast(getContext(), "Код: "+code+"\nemail: "+email+"\npas: "+password);
        }

        email_text_view.setText(email);

        but_send.setOnClickListener(v -> {
            String enteredCode = edit_code.getText().toString();
            // Обработка нажатия на кнопку but_send
            if (!enteredCode.isEmpty() && Integer.parseInt(enteredCode) == code) {
                new RequestUtils(this, "add_new_person_route", "POST",
                        "{\"email\": \"" + email + "\", \"password\": \"" + password +"\"}", callback).execute();


                new android.os.Handler().postDelayed(
                        () -> {
                            if (status == 0){
                                Intent intent = new Intent(requireContext(), ActivityMain.class);
                                DataUtils.saveUserId(requireContext(), id_person);
                                // Запускаем новую активность
                                startActivity(intent);
                            } else {
                                handleEmptyResponse();
                                edit_code.setText("");
                            }
                        },
                        250
                );
            } else {
                ToastUtils.showShortToast(getContext(), "Неверный код");
                edit_code.setText("");
            }
        });
        return rootView;
    }


    RequestUtils.Callback callback = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");
            if (status == 0){
                this.id_person = jsonObject.getInt("id_person");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };


    public void handleEmptyResponse() {
        ToastUtils.showShortToast(getContext(), "Ошибка сервера, попробуйте заново."+ "\nКод: "+status);
    }
}