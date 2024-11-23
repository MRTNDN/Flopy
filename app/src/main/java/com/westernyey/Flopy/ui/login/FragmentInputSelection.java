package com.westernyey.Flopy.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;


public class FragmentInputSelection extends Fragment {

    private ImageView but1, but2, but3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input_selection, container, false);

        // but1 - кнопка входа через google
        but1 = rootView.findViewById(R.id.but_input_google);
        // but2 - кнопка входа через vk
        but2 = rootView.findViewById(R.id.but_input_vk);
        // but3 - кнопка входа через login
        but3 = rootView.findViewById(R.id.but_input_login);



        but1.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but1
            ToastUtils.showShortToast(requireContext(), "В разработке");
        });

        but2.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but2
            ToastUtils.showShortToast(requireContext(), "В разработке");
        });

        but3.setOnClickListener(v -> {
            // Обработка нажатия на кнопку but3
            Fragment fragment = new FragmentLogin();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_start, fragment);
        });

        return rootView;
    }
}
