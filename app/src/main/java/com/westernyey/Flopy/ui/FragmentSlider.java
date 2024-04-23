package com.westernyey.Flopy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.cardModel.Swap;

public class FragmentSlider extends Fragment {

    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slider, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Добавляем BackgroundFragment в контейнер
        fragmentTransaction.add(R.id.background_container, new FragmentBackground());
        // Добавляем SwapFragment в контейнер
        fragmentTransaction.add(R.id.swap_container, new Swap());
        fragmentTransaction.commit();

        // Инициализация кнопок и бокового меню
        Button btnOpenMenu = rootView.findViewById(R.id.btn_open_menu);
        Button btnOpenSet = rootView.findViewById(R.id.btn_open_settings);
        Button btnOpenFil = rootView.findViewById(R.id.btn_open_filters);
        drawerLayout = rootView.findViewById(R.id.drawer_layout);

        btnOpenMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        btnOpenFil.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        btnOpenSet.setOnClickListener(v -> {
            Fragment fragment = new SettingsFragment();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        return rootView;
    }
}
