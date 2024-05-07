package com.westernyey.Flopy.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.profile.FragmentProfile;
import com.westernyey.Flopy.ui.slider.FragmentSlider;

public class Sidebar_Menu extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sidebar_menu, container, false);

        Button btn_profile = rootView.findViewById(R.id.b1);
        Button btn_like = rootView.findViewById(R.id.b2);
        Button btn_massage = rootView.findViewById(R.id.b3);
        Button btn_main = rootView.findViewById(R.id.b4);

        btn_profile.setOnClickListener(v -> {
            // Обработка нажатия на кнопку btn_profile
            Fragment fragment = new FragmentProfile();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);

            // Закрываем боковое меню
            DrawerLayout drawer = requireActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        });

        btn_like.setOnClickListener(v -> {
            // Обработка нажатия на кнопку btn_like
            Fragment fragment = new FragmentProfile();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);

            // Закрываем боковое меню
            DrawerLayout drawer = requireActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        });

        btn_massage.setOnClickListener(v -> {
            // Обработка нажатия на кнопку btn_massage
            Fragment fragment = new FragmentProfile();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);

            // Закрываем боковое меню
            DrawerLayout drawer = requireActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        });

        btn_main.setOnClickListener(v -> {
            // Обработка нажатия на кнопку btn_main
            Fragment fragment = new FragmentSlider();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);

            // Закрываем боковое меню
            DrawerLayout drawer = requireActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        });
        return rootView;
    }
}
