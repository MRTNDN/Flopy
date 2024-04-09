package com.westernyey.Flopy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.westernyey.Flopy.R;

public class SettingsFragment extends Fragment {

    private Switch switchTheme;
    private Switch switchNotification1;
    private Switch switchNotification2;
    private Switch switchNotification3;
    private Switch switchNotification4;
    private Spinner spinner;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize views
        switchTheme = view.findViewById(R.id.switch_theme);
        switchNotification1 = view.findViewById(R.id.switch_notification1);
        switchNotification2 = view.findViewById(R.id.switch_notification2);
        switchNotification3 = view.findViewById(R.id.switch_notification3);
        switchNotification4 = view.findViewById(R.id.switch_notification4);
        spinner = view.findViewById(R.id.spinner);
        btnSave = view.findViewById(R.id.btn_save);

        // Set onClickListener for btnSave
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        // Set onCheckedChangeListener for switchTheme
        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeTheme(isChecked);
            }
        });

        // Set onCheckedChangeListener for notification switches
        CompoundButton.OnCheckedChangeListener notificationSwitchListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleNotificationSwitch(buttonView, isChecked);
            }
        };
        switchNotification1.setOnCheckedChangeListener(notificationSwitchListener);
        switchNotification2.setOnCheckedChangeListener(notificationSwitchListener);
        switchNotification3.setOnCheckedChangeListener(notificationSwitchListener);
        switchNotification4.setOnCheckedChangeListener(notificationSwitchListener);

        // Set onItemSelectedListener for spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleSpinnerSelection(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void saveSettings() {

    }

    private void changeTheme(boolean darkTheme) {

    }

    private void handleNotificationSwitch(CompoundButton buttonView, boolean isChecked) {
        String notificationText = buttonView.getText().toString();
        if (isChecked) {

        } else {

        }
    }

    private void handleSpinnerSelection(String selectedItem) {
    }
}
