package com.westernyey.Flopy.ui.settings;

import static com.cripochec.Flopy.ui.utils.DataUtils.getLastFragmentPosition;
import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;
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
import com.westernyey.Flopy.ui.likedYou.FragmentLiked;
import com.westernyey.Flopy.ui.profile.FragmentProfile;
import com.westernyey.Flopy.ui.settings.blackList.SettingsFragmentBlackList;
import com.westernyey.Flopy.ui.settings.dialog.DellConfirmationDialog;
import com.westernyey.Flopy.ui.settings.dialog.ExitConfirmationDialog;
import com.westernyey.Flopy.ui.settings.dialog.InputConfirmationDialog;
import com.westernyey.Flopy.ui.settings.dialog.NotificationConfirmationDialog;
import com.westernyey.Flopy.ui.settings.dialog.SettingsFragmentAgreement;
import com.westernyey.Flopy.ui.settings.dialog.SettingsFragmentConfidentiality;
import com.westernyey.Flopy.ui.settings.dialog.SupportConfirmationDialog;
import com.westernyey.Flopy.ui.slider.FragmentSlider;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentSettings extends Fragment {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch incognito_switch;
    private int incognito_status;
    private boolean isUpdatingSwitch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Инициализация всех элементов
        Button btnBac = rootView.findViewById(R.id.btn_bac_settings);
        ConstraintLayout inputLayout = rootView.findViewById(R.id.inputLayout);
        ConstraintLayout notificationLayout = rootView.findViewById(R.id.notificationLayout);
        ConstraintLayout blackListLayout = rootView.findViewById(R.id.blackListLayout);
        ConstraintLayout supportLayout = rootView.findViewById(R.id.supportLayout);
        ConstraintLayout outLayout = rootView.findViewById(R.id.outLayout);
        ConstraintLayout confidentialityLayout = rootView.findViewById(R.id.confidentialityLayout);
        ConstraintLayout agreementLayout = rootView.findViewById(R.id.agreementLayout);
        ConstraintLayout delLayout = rootView.findViewById(R.id.delLayout);
        incognito_switch = rootView.findViewById(R.id.incognito_switch);

        incognito_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isUpdatingSwitch) return;

            if (isChecked) {
                incognito_status = 1;
            } else {
                incognito_status = 0;
            }
            try {
                JSONObject jsonRequestBody = new JSONObject();
                jsonRequestBody.put("id_person", getUserId(requireContext()));
                jsonRequestBody.put("incognito_status", incognito_status);

                new RequestUtils(this, "set_incognito", "POST", jsonRequestBody.toString() , callbackSetIncognito).execute();
            } catch (JSONException e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSettings\", \"method\": \"set_incognito.request\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });

        btnBac.setOnClickListener(v -> {
            if (getLastFragmentPosition(requireContext()).equals("profile")){
                saveLastFragmentPosition(requireContext(), "");
                Fragment fragment = new FragmentProfile();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            } else if (getLastFragmentPosition(requireContext()).equals("like")) {
                saveLastFragmentPosition(requireContext(), "");
                Fragment fragment = new FragmentLiked();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            } else if (getLastFragmentPosition(requireContext()).equals("messages")) {
                saveLastFragmentPosition(requireContext(), "");
                Fragment fragment = new FragmentLiked();
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
            NotificationConfirmationDialog bottomSheet = new NotificationConfirmationDialog();
            bottomSheet.show(getParentFragmentManager(), "NotificationConfirmationDialog");
        });

        blackListLayout.setOnClickListener(v -> {
            Fragment fragment = new SettingsFragmentBlackList();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        supportLayout.setOnClickListener(v -> {
            SupportConfirmationDialog bottomSheet = new SupportConfirmationDialog();
            bottomSheet.show(getParentFragmentManager(), "SupportConfirmationDialog");
        });

        outLayout.setOnClickListener(v -> {
            ExitConfirmationDialog dialog = new ExitConfirmationDialog();
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
            DellConfirmationDialog dialog = new DellConfirmationDialog();
            dialog.show(getParentFragmentManager(), "DellConfirmationDialog");
        });

        new RequestUtils(this, "get_incognito", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\"}", callbackGetIncognito).execute();
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
                requireActivity().runOnUiThread(() -> {
                    updateSwitchColor(incognito_switch.isChecked());
                });
            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSettings\", \"method\": \"callbackSetIncognito\", \"error\": \"" + e + "\"}", callbackLog).execute();
            EmptyResponse();
        }
    };

    // Обработка ответа на сохранение статуса инкогнито пользователя
    RequestUtils.Callback callbackGetIncognito = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){

                int inc = jsonObject.getInt("incognito_status");

                // Обновление положения incognito_switch
                requireActivity().runOnUiThread(() -> {
                    isUpdatingSwitch = true;
                    incognito_switch.setChecked(inc != 0);
                    updateSwitchColor(incognito_switch.isChecked());
                    isUpdatingSwitch = false;
                });

            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSettings\", \"method\": \"callbackGetIncognito\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
