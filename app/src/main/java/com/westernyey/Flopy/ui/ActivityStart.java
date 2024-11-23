package com.westernyey.Flopy.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cripochec.Flopy.ui.utils.DataUtils;
import com.westernyey.Flopy.R;

// Первичное activity где выполняется вход или регистрация в аккаунт
public class ActivityStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Проверка осуществлялся ли ранее вход в аккаунт

        // Получение id пользователя из памяти телефона
        int id = DataUtils.getUserId(this);
        // Если id не равно 0 то переходим в главное activity приложения
        if (id != 0) {
            Intent intent = new Intent(this, ActivityMain.class);
            startActivity(intent);
            finish();
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
