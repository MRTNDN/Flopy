package com.westernyey.Flopy.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.login.FragmentLogin;

public class FragmentRegisterCode extends Fragment {
    private int code;
    private int userId;
    private String email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_code, container, false);

        Button but_send = rootView.findViewById(R.id.buttonCodeSend);
        TextView email_text_view = rootView.findViewById(R.id.emailTextView);
        EditText edit_code = rootView.findViewById(R.id.editCode);

        // Получение данных из Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            code = bundle.getInt("code");
            userId = bundle.getInt("userId");
            email = bundle.getString("email");
        }

        email_text_view.setText(email);



        but_send.setOnClickListener(v -> {
            String enteredCode = edit_code.getText().toString();
            // Обработка нажатия на кнопку but_send
            if (!enteredCode.isEmpty() && Integer.parseInt(enteredCode) == code) {
                Fragment fragment = new FragmentLogin();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
            } else {
                // Handle case when the entered code is empty or doesn't match the stored code
            }
        });

        // Использование данных
        // Дальнейшие действия с данными

        return rootView;
    }
}