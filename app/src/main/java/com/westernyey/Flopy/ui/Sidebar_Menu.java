package com.westernyey.Flopy.ui;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.profile.Profile;

public class Sidebar_Menu extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sidebar_menu, container, false);

        Button button1 = rootView.findViewById(R.id.b1);
        Button button2 = rootView.findViewById(R.id.b2);
        Button button3 = rootView.findViewById(R.id.b3);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открываем фрагмент Profile
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.profile_container, new Profile())
                        .commit();
                // Закрываем боковое меню
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return rootView;
    }
}
