package com.westernyey.Flopy.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.DataUtils;
import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.settings.dialog.ExitConfirmationDialog;
import com.westernyey.Flopy.ui.settings.dialog.InputConfirmationDialog;
import com.westernyey.Flopy.ui.settings.dialog.SettingsFragmentAgreement;
import com.westernyey.Flopy.ui.settings.dialog.SettingsFragmentConfidentiality;
import com.westernyey.Flopy.ui.settings.dialog.SupportConfirmationDialog;
import com.westernyey.Flopy.ui.slider.FragmentSlider;

public class FragmentSettings extends Fragment {

    private ConstraintLayout confidentialityLayout, agreementLayout, delLayout, inputLayout, notificationLayout, blackListLayout, supportLayout, outLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btnBac = rootView.findViewById(R.id.btn_bac_settings);

        // Инициализация всех элементов
        inputLayout = rootView.findViewById(R.id.inputLayout);
        notificationLayout = rootView.findViewById(R.id.notificationLayout);
        blackListLayout = rootView.findViewById(R.id.blackListLayout);
        supportLayout = rootView.findViewById(R.id.supportLayout);
        outLayout = rootView.findViewById(R.id.outLayout);
        confidentialityLayout = rootView.findViewById(R.id.confidentialityLayout);
        agreementLayout = rootView.findViewById(R.id.agreementLayout);
        delLayout = rootView.findViewById(R.id.delLayout);

        btnBac.setOnClickListener(v -> {
            Fragment fragment = new FragmentSlider();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        // скрыть аккаунт

        inputLayout.setOnClickListener(v -> {
            InputConfirmationDialog dialog = new InputConfirmationDialog(() -> {
            });
            dialog.show(getParentFragmentManager(), "InputConfirmationDialog");
        });

        notificationLayout.setOnClickListener(v -> {
            ToastUtils.showShortToast(requireContext(), "Пока не работает");
        });

        blackListLayout.setOnClickListener(v -> {
            Fragment fragment = new FragmentSlider();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        supportLayout.setOnClickListener(v -> {
            SupportConfirmationDialog dialog = new SupportConfirmationDialog(() -> {
            });
            dialog.show(getParentFragmentManager(), "SupportConfirmationDialog");
        });

        outLayout.setOnClickListener(v -> {
            ExitConfirmationDialog dialog = new ExitConfirmationDialog(() -> {
                DataUtils.saveUserId(requireContext(), 0);
                DataUtils.saveEntry(requireContext(), true);
                requireActivity().finish();
            });
            dialog.show(getParentFragmentManager(), "ExitConfirmationDialog");
        });

        confidentialityLayout.setOnClickListener(v -> {
            Fragment fragment = new SettingsFragmentConfidentiality();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        agreementLayout.setOnClickListener(v -> {
            Fragment fragment = new SettingsFragmentAgreement();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        delLayout.setOnClickListener(v -> {
            Fragment fragment = new FragmentSlider();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        return rootView;
    }
}
