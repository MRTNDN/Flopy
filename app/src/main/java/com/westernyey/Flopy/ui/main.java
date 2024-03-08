package com.westernyey.Flopy.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.profile.Profile;

public class main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Начать транзакцию для добавления фрагментов
        getSupportFragmentManager().beginTransaction()
                .add(R.id.background_container, new BackgroundFragment())
                .add(R.id.button_container, new ButtonFragment())

                .commit();
    }
}
