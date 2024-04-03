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
import androidx.fragment.app.FragmentManager;

import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.match.MatchFragment;
import com.westernyey.Flopy.ui.profile.Profile;
import com.westernyey.Flopy.ui.youwereliked.Youwereliked;

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
                // Скрываем или удаляем фрагмент swap, если он отображается
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment swapFragment = fragmentManager.findFragmentById(R.id.swap_container);
                if (swapFragment != null) {
                    fragmentManager.beginTransaction().hide(swapFragment).commit();
                }

                // Открываем новый фрагмент Profile
                fragmentManager.beginTransaction()
                        .replace(R.id.profile_container, new Profile())
                        .commit();

                // Закрываем боковое меню
                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Скрываем или удаляем фрагмент swap, если он отображается
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment swapFragment = fragmentManager.findFragmentById(R.id.swap_container);
                if (swapFragment != null) {
                    fragmentManager.beginTransaction().hide(swapFragment).commit();
                }

                // Открываем новый фрагмент Profile
                fragmentManager.beginTransaction()
                        .replace(R.id.profile_container, new MatchFragment())
                        .commit();

                // Закрываем боковое меню
                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Скрываем или удаляем фрагмент swap, если он отображается
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    Fragment swapFragment = fragmentManager.findFragmentById(R.id.swap_container);
                    if (swapFragment != null) {
                        fragmentManager.beginTransaction().hide(swapFragment).commit();
                    }

                    // Открываем новый фрагмент Profile
                    fragmentManager.beginTransaction()
                            .replace(R.id.profile_container, new Youwereliked())
                            .commit();

                    // Закрываем боковое меню
                    DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
            }
        });

        return rootView;
    }
}
