package com.westernyey.Flopy.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.cardModel.Swap;

public class main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Получаем менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Добавляем BackgroundFragment в его контейнер
        fragmentTransaction.add(R.id.background_container, new BackgroundFragment());

        // Добавляем ButtonFragment в его контейнер
        fragmentTransaction.add(R.id.button_container, new ButtonFragment());

        // Создаем экземпляр фрагмента Swap
        Swap swapFragment = new Swap();

        // Создаем параметры размещения для фрагмента
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.TOP; // Устанавливаем размещение фрагмента вверху экрана
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.swap_top_margin); // Устанавливаем отступ от верхней части экрана
        swapFragment.setLayoutParams(layoutParams);

        // Добавляем SwapFragment в контейнер
        fragmentTransaction.add(R.id.swap_container, swapFragment);

        // Применяем транзакцию
        fragmentTransaction.commit();
    }
}
