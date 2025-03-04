package com.westernyey.Flopy.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cripochec.Flopy.ui.utils.DataUtils;
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

        // Получение данных о первом входе пользователя из памяти телефона
        boolean entry = DataUtils.getEntry(this);

        if (entry) {
            // Если entry true, загружаем FragmentProfileSettings
            fragmentTransaction.replace(R.id.fr_activity_main, new FragmentProfileSettings());
        } else {
            // В противном случае загружаем FragmentSlider
            fragmentTransaction.add(R.id.fr_activity_main, new FragmentSlider());
        }

        // Применяем транзакцию
        fragmentTransaction.commitAllowingStateLoss();
    }

    // Метод для отслеживания нажатий для скрытия клавиатуры
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                int[] scrcoords = new int[2];
                v.getLocationOnScreen(scrcoords);
                float x = ev.getRawX() + v.getLeft() - scrcoords[0];
                float y = ev.getRawY() + v.getTop() - scrcoords[1];

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
        if (imm != null && view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
