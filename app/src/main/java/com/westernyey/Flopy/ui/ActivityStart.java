package com.westernyey.Flopy.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cripochec.Flopy.ui.utils.DataUtils;
import com.westernyey.Flopy.R;

public class ActivityStart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        int id = DataUtils.getUserId(this);
        if (id != 0) {
            Intent intent = new Intent(this, ActivityMain.class);
            startActivity(intent);
        }
    }
}
