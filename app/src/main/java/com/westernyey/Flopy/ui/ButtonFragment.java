package com.westernyey.Flopy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;
import com.westernyey.Flopy.R;

public class ButtonFragment extends Fragment {

    private Button btnOpenMenu;
    private Button btnOpenSet;
    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Загрузка макета фрагмента
        View view = inflater.inflate(R.layout.fragment_button, container, false);

        // Инициализация кнопок и бокового меню
        btnOpenMenu = view.findViewById(R.id.btn_open_menu);
        btnOpenSet = view.findViewById(R.id.set_open_menu);
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);

        // Настройка обработчика нажатия кнопки
        btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        btnOpenSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment newFragment = new settings();

                // Замена фрагмента в контейнере 'profile_container'
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.profile_container, newFragment)
                        .commit();
            }
        });

        return view;
    }
}
