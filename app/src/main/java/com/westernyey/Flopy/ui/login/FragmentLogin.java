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

    private EditText email;
    private EditText password;
    private int status;


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        email = rootView.findViewById(R.id.editEmail);
        password = rootView.findViewById(R.id.editPassword);
        Button but_login = rootView.findViewById(R.id.buttonLogin);
        TextView but_register = rootView.findViewById(R.id.textRegister);

        but_login.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but_login
            String enteredEmail = email.getText().toString();
            String enteredPassword = password.getText().toString();
            if (!enteredEmail.isEmpty() && !enteredPassword.isEmpty()){
                new RequestUtils(this, "entry_person", "POST",
                        "{\"email\": \""+enteredEmail+"\", \"password\": \""+enteredPassword+"\"}", callback).execute();
            } else {
                ToastUtils.showShortToast(getContext(), "Все поля должны быть заполнены");
                clearEditText();
            }

        });

        but_register.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but_register
            Fragment fragment = new FragmentRegister();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
        });
        return rootView;
    }


    RequestUtils.Callback callback = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");
            if (status == 0){
                int id_person = jsonObject.getInt("id_person");
                Intent intent = new Intent(requireContext(), ActivityMain.class);
                DataUtils.saveUserId(requireContext(), id_person);
                DataUtils.saveEntry(requireContext(), false);
                // Запускаем новую активность
                startActivity(intent);
            } else if (status == 1){
//                getActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(), "email не найден"));
                clearEditText();
            } else if (status == 2){
//                ToastUtils.showShortToast(getContext(), "Неверный логин или пароль");
                clearEditText();
            } else if (status == 3){
                handleEmptyResponse();
                clearEditText();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };


    public void clearEditText() {
        email.setText("");
        password.setText("");
    }


    public void handleEmptyResponse() {
        ToastUtils.showShortToast(getContext(), "Ошибка сервера, попробуйте заново."+ "\nКод: "+status);
    }
}