package com.westernyey.Flopy.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.slider.FragmentSlider;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Добавляем BackgroundFragment в контейнер
        fragmentTransaction.add(R.id.background_container, new FragmentBackground());

        // Заменяем FragmentSlider в контейнере fr_activity_main
        fragmentTransaction.replace(R.id.fr_activity_main, new FragmentSlider());

        fragmentTransaction.commit();
    }
}
