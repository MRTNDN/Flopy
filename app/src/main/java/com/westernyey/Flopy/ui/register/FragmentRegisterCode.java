package com.westernyey.Flopy.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.login.FragmentLogin;

public class FragmentRegisterCode extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_code, container, false);

        Button but_send = rootView.findViewById(R.id.buttonCodeSend);

        but_send.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but_send
            Fragment fragment = new FragmentLogin();
            replaceFragment(fragment);
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