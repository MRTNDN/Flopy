package com.westernyey.Flopy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.main;


public class FragmentLogin extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        EditText email = rootView.findViewById(R.id.editEmail);
        EditText password = rootView.findViewById(R.id.editPassword);
        Button but_login = rootView.findViewById(R.id.buttonLogin);
        TextView but_register = rootView.findViewById(R.id.textRegister);

        but_login.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but_login
            String enteredEmail = email.getText().toString();
            String enteredPassword = password.getText().toString();

            if ("admin".equals(enteredEmail) && "admin".equals(enteredPassword)) {
                // Логин и пароль правильные, выполните необходимые действия
                // Например, переход на другой экран или выполнение других операций
                // Ваш код здесь
                Intent intent = new Intent(requireContext(), main.class);


                // Запускаем новую активность
                startActivity(intent);
            } else {
                // Логин или пароль неверные, выполните соответствующие действия
                // Например, отобразите сообщение об ошибке или выполните другие операции
                // Ваш код здесь
                Toast.makeText(requireContext(), "Неверный логин или пароль", Toast.LENGTH_LONG).show();
            }
        });

        but_register.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but_register
//            Fragment fragment = new FragmentLogin();
//            replaceFragment(fragment);
        });
        return rootView;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fr_activity_start, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}