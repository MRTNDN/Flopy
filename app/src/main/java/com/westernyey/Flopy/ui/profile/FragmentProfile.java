package com.westernyey.Flopy.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.SettingsFragment;

public class FragmentProfile extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Инициализация кнопок и бокового меню
        Button btnOpenMenu = rootView.findViewById(R.id.btn_open_menu);
        Button btnOpenSet = rootView.findViewById(R.id.btn_open_settings);
        Button btnOpenProfSett = rootView.findViewById(R.id.btn_open_profile_settings);

        btnOpenMenu.setOnClickListener(v -> {
            DrawerLayout drawerLayout = requireActivity().findViewById(R.id.drawer_layout);
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        btnOpenSet.setOnClickListener(v -> {
            Fragment fragment = new SettingsFragment();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        btnOpenProfSett.setOnClickListener(v -> {
            Fragment fragment = new FragmentProfileSettings();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });
        return rootView;
    }
}
