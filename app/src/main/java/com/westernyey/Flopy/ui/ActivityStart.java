package com.westernyey.Flopy.ui;

import android.content.Intent;
import android.os.Bundle;

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
        }
    }
}
