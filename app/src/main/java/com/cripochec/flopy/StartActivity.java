package com.cripochec.flopy;

import com.westernyey.Flopy.R;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cripochec.flopy.ui.login.FragmentLogin;
import com.cripochec.flopy.ui.login.InputSelection;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void Change(View view) {
        Fragment fragment;

        int viewId = view.getId();

        if (viewId == R.id.but1) {
            // Обработка нажатия на кнопку but1

        } else if (viewId == R.id.but2) {
            // Обработка нажатия на кнопку but2

        } else if (viewId == R.id.but3) {
            // При нажатии на кнопку but3 переходим к FragmentLogin
            fragment = new FragmentLogin();
            replaceFragment(fragment);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fr_input_selection, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
