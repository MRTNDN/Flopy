package com.westernyey.Flopy.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.login.FragmentLogin;
import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;

public class FragmentRegister extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        Button but_register = rootView.findViewById(R.id.buttonRegisterUser);

        EditText new_email = rootView.findViewById(R.id.editEmailRegister);
        EditText pas1 = rootView.findViewById(R.id.editPassword1Register);
        EditText pas2 = rootView.findViewById(R.id.editPassword2Register);

        // Обработка нажатия на кнопку but_register
        but_register.setOnClickListener(v -> {
            String text = RequestUtils.request();
            ToastUtils.showShortToast(getContext(), text);
//            if (pas1.getText().toString().equals(pas2.getText().toString())) {
//                String text = RequestUtils.request();
//                ToastUtils.showShortToast(getContext(), text);
//                Fragment fragment = new FragmentRegisterCode();
//                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
//            } else {
//                ToastUtils.showShortToast(getContext(), "Пароли не совпадают");
//            }
        });
        return rootView;
    }
}