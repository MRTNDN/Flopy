package com.westernyey.Flopy.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cripochec.Flopy.ui.utils.JsonUtils.JsonUtils;
import com.westernyey.Flopy.R;

public class ActivityStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        int id = JsonUtils.loadID(this);
        if (id != 0) {
            Intent intent = new Intent(this, ActivityMain.class);
            startActivity(intent);
        }
    }
}
