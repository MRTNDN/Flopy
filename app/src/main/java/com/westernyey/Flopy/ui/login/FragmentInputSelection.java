package com.westernyey.Flopy.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.westernyey.Flopy.R;


public class FragmentInputSelection extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input_selection, container, false);

        // but1 - кнопка входа через google
        ImageView but1 = rootView.findViewById(R.id.but_input_google);
        // but2 - кнопка входа через vk
        ImageView but2 = rootView.findViewById(R.id.but_input_vk);
        // but3 - кнопка входа через login
        ImageView but3 = rootView.findViewById(R.id.but_input_login);



        but1.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but1
        });

        but2.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but2
        });

        but3.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but3
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
