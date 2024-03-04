package com.westernyey.Flopy.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.westernyey.Flopy.R;

public class main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Начать транзакцию для добавления фрагментов
        getSupportFragmentManager().beginTransaction()
                .add(R.id.background_container, new BackgroundFragment())
                .add(R.id.button_container, new ButtonFragment())
                .commit();
    }
}
