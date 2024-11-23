package com.westernyey.Flopy.ui.settings;

import static com.cripochec.Flopy.ui.utils.DataUtils.clearAllData;
import static com.cripochec.Flopy.ui.utils.DataUtils.getIncognito;
import static com.cripochec.Flopy.ui.utils.DataUtils.getLastFragmentPosition;
import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;
import static com.cripochec.Flopy.ui.utils.DataUtils.saveIncognito;
import static com.cripochec.Flopy.ui.utils.DataUtils.saveLastFragmentPosition;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.profile.FragmentProfile;
import com.westernyey.Flopy.ui.settings.blackList.SettingsFragmentBlackList;
import com.westernyey.Flopy.ui.settings.dialog.DellConfirmationDialog;
import com.westernyey.Flopy.ui.settings.dialog.ExitConfirmationDialog;
import com.westernyey.Flopy.ui.settings.dialog.InputConfirmationDialog;
import com.westernyey.Flopy.ui.settings.dialog.SettingsFragmentAgreement;
import com.westernyey.Flopy.ui.settings.dialog.SettingsFragmentConfidentiality;
import com.westernyey.Flopy.ui.slider.FragmentSlider;

import org.json.JSONObject;

public class FragmentSettings extends Fragment {

    private ConstraintLayout confidentialityLayout, agreementLayout, delLayout, inputLayout, notificationLayout, blackListLayout, supportLayout, outLayout;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch incognito_switch;
    private int incognito_status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Инициализация всех элементов
        Button btnBac = rootView.findViewById(R.id.btn_bac_settings);
        inputLayout = rootView.findViewById(R.id.inputLayout);
        notificationLayout = rootView.findViewById(R.id.notificationLayout);
        blackListLayout = rootView.findViewById(R.id.blackListLayout);
        supportLayout = rootView.findViewById(R.id.supportLayout);
        outLayout = rootView.findViewById(R.id.outLayout);
        confidentialityLayout = rootView.findViewById(R.id.confidentialityLayout);
        agreementLayout = rootView.findViewById(R.id.agreementLayout);
        delLayout = rootView.findViewById(R.id.delLayout);
        incognito_switch = rootView.findViewById(R.id.incognito_switch);

        // Обновление положения incognito_switch
        incognito_switch.setChecked(getIncognito(requireContext()) != 0);
        updateSwitchColor(incognito_switch.isChecked());

        // Слушатель для изменения состояния incognito_switch
        incognito_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                incognito_status = 1;
            } else {
                incognito_status = 0;
            }
            new RequestUtils(this, "set_incognito", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\", \"incognito_status\": \""+ incognito_status +"\"}", callbackSetIncognito).execute();
        });

        btnBac.setOnClickListener(v -> {
            if (getLastFragmentPosition(requireContext()).equals("profile")){
                saveLastFragmentPosition(requireContext(), "");
                Fragment fragment = new FragmentProfile();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            } else {
                Fragment fragment = new FragmentSlider();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            }

        });

        inputLayout.setOnClickListener(v -> {
            InputConfirmationDialog bottomSheet = new InputConfirmationDialog();
            bottomSheet.show(getParentFragmentManager(), "InputConfirmationDialog");
        });

        notificationLayout.setOnClickListener(v -> {
            ToastUtils.showShortToast(requireContext(), "В разработке");
        });

        blackListLayout.setOnClickListener(v -> {
            Fragment fragment = new SettingsFragmentBlackList();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        supportLayout.setOnClickListener(v -> {
            InputConfirmationDialog bottomSheet = new InputConfirmationDialog();
            bottomSheet.show(getParentFragmentManager(), "SupportConfirmationDialog");
        });

        outLayout.setOnClickListener(v -> {
            ExitConfirmationDialog dialog = new ExitConfirmationDialog(() -> {
                clearAllData(requireContext());
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
            DellConfirmationDialog dialog = new DellConfirmationDialog(() -> {
                new RequestUtils(this, "delete_account", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\"}", callbackDeleteAccount).execute();
                requireActivity().finish();
            });
            dialog.show(getParentFragmentManager(), "DellConfirmationDialog");
        });

        return rootView;
    }


    // Метод для изменения цвета переключателя
    private void updateSwitchColor(boolean isChecked) {
        if (isChecked) {
            // Получаем цвет purple_200 из ресурсов
            incognito_switch.getThumbDrawable().setTint(ContextCompat.getColor(requireContext(), R.color.purple_200));  // Включен - красный
        } else {
            incognito_switch.getThumbDrawable().setTint(ContextCompat.getColor(requireContext(), R.color.dark_grey));  // Выключен - серый
        }
    }

    // Обработка логирования на сервере
    RequestUtils.Callback callbackLog = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") != 0){
                showErrorToast("Ошибка логирования на сервере.");
            }
        } catch (Exception e) {
            showErrorToast("Ошибка логирования на клиенте.");
        }
    };

    // Обработка ответа на сохранение статуса инкогнито пользователя
    RequestUtils.Callback callbackSetIncognito = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){
                saveIncognito(requireContext(), incognito_status);
                updateSwitchColor(incognito_switch.isChecked());
            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSettings\", \"method\": \"callbackSetIncognito\", \"error\": \"" + e + "\"}", callbackLog).execute();
            EmptyResponse();
        }
    };


    // Обработка ответа на удаление пользователя
    RequestUtils.Callback callbackDeleteAccount = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){
                clearAllData(requireContext());
            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSettings\", \"method\": \"callbackDeleteAccount\", \"error\": \"" + e + "\"}", callbackLog).execute();
            EmptyResponse();
        }
    };


    // Обработка ошибки запроса
    public void EmptyResponse() {
        requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(),
                "Ошибка callback.")); // Показываем сообщение об ошибке
    }

    // Метод для показа сообщения об ошибке
    private void showErrorToast(String message) {
        requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(), message));
    }
}
