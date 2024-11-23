package com.westernyey.Flopy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cripochec.Flopy.ui.utils.DataUtils;
import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.profile.FragmentProfileSettings;
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

        // Применяем транзакцию
        fragmentTransaction.commit();

        // Получение данных о первом входе пользователя из памяти телефона
        boolean entry = DataUtils.getEntry(this);
        // Если entry true то переходим на фрагмент занесения информации о пользователе
        if (entry){
            Fragment fragment = new FragmentProfileSettings();
            FragmentUtils.replaceFragment(this.getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        }
    }

    // Метод для отслеживания нажатий для скрытия клавиатуры
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Если пользователь коснулся экрана
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // Получаем текущий фокус
            View v = getCurrentFocus();
            // Проверяем, находится ли фокус на EditText
            if (v instanceof EditText) {
                // Получаем координаты нажатия
                int[] scrcoords = new int[2];
                v.getLocationOnScreen(scrcoords);
                float x = ev.getRawX() + v.getLeft() - scrcoords[0];
                float y = ev.getRawY() + v.getTop() - scrcoords[1];

                // Если нажатие вне EditText — скрываем клавиатуру
                if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                    hideSoftKeyboard(this);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // Метод для скрытия клавиатуры
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
