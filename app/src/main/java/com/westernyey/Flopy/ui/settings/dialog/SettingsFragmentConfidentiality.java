package com.westernyey.Flopy.ui.settings.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.settings.FragmentSettings;

public class SettingsFragmentConfidentiality extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment_confidentiality, container, false);

        Button btnBac = rootView.findViewById(R.id.btn_bac_settings);

        btnBac.setOnClickListener(v -> {
            Fragment fragment = new FragmentSettings();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });
        return rootView;
    }
}
