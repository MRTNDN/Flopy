package com.westernyey.Flopy.ui.settings.dialog;

import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.westernyey.Flopy.R;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationConfirmationDialog extends BottomSheetDialogFragment {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switch1, switch2, switch3;


    private boolean isUpdatingSwitch;
    private int switch_1_status, switch_2_status, switch_3_status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_dialog_notification_confirmation, container, false);

        switch1 = rootView.findViewById(R.id.switch1);
        switch2 = rootView.findViewById(R.id.switch2);
        switch3 = rootView.findViewById(R.id.switch3);


        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isUpdatingSwitch) return;

            if (isChecked) {
                switch_1_status = 1;
            } else {
                switch_1_status = 0;
            }
            try {
                JSONObject jsonRequestBody = new JSONObject();
                jsonRequestBody.put("id_person", getUserId(requireContext()));
                jsonRequestBody.put("like", switch_1_status);
                jsonRequestBody.put("match", switch_2_status);
                jsonRequestBody.put("chat", switch_3_status);

                new RequestUtils(this, "set_notification", "POST", jsonRequestBody.toString() , callbackSetNotification).execute();
            } catch (JSONException e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"NotificationConfirmationDialog\", \"method\": \"set_notification.request.switch1\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });

        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isUpdatingSwitch) return;

            if (isChecked) {
                switch_2_status = 1;
            } else {
                switch_2_status = 0;
            }
            try {
                JSONObject jsonRequestBody = new JSONObject();
                jsonRequestBody.put("id_person", getUserId(requireContext()));
                jsonRequestBody.put("like", switch_1_status);
                jsonRequestBody.put("match", switch_2_status);
                jsonRequestBody.put("chat", switch_3_status);

                new RequestUtils(this, "set_notification", "POST", jsonRequestBody.toString() , callbackSetNotification).execute();
            } catch (JSONException e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"NotificationConfirmationDialog\", \"method\": \"set_notification.request.switch2\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });

        switch3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isUpdatingSwitch) return;

            if (isChecked) {
                switch_3_status = 1;
            } else {
                switch_3_status = 0;
            }
            try {
                JSONObject jsonRequestBody = new JSONObject();
                jsonRequestBody.put("id_person", getUserId(requireContext()));
                jsonRequestBody.put("like", switch_1_status);
                jsonRequestBody.put("match", switch_2_status);
                jsonRequestBody.put("chat", switch_3_status);

                new RequestUtils(this, "set_notification", "POST", jsonRequestBody.toString() , callbackSetNotification).execute();
            } catch (JSONException e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"NotificationConfirmationDialog\", \"method\": \"set_notification.request.switch3\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });



        try {
            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("id_person", getUserId(requireContext()));

            new RequestUtils(this, "get_notification", "POST", jsonRequestBody.toString() , callbackGetNotification).execute();
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"NotificationConfirmationDialog\", \"method\": \"get_notification.request\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }


        return rootView;
    }

    // Метод для изменения цвета переключателя
    private void updateSwitchColor(boolean isChecked, @SuppressLint("UseSwitchCompatOrMaterialCode") Switch load_switch) {
        if (isChecked) {
            // Получаем цвет purple_200 из ресурсов
            load_switch.getThumbDrawable().setTint(ContextCompat.getColor(requireContext(), R.color.purple_200));  // Включен - фиолетовый
        } else {
            load_switch.getThumbDrawable().setTint(ContextCompat.getColor(requireContext(), R.color.dark_grey));  // Выключен - серый
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


    // Обработка ответа на получение данных о рассылки для пользователя
    RequestUtils.Callback callbackGetNotification = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){

                int like = jsonObject.getInt("like");
                int match = jsonObject.getInt("match");
                int chat = jsonObject.getInt("chat");

                // Обновление положения incognito_switch
                requireActivity().runOnUiThread(() -> {
                    isUpdatingSwitch = true;

                    switch1.setChecked(like != 0);
                    switch_1_status = like;
                    updateSwitchColor(switch1.isChecked(), switch1);

                    switch2.setChecked(match != 0);
                    switch_2_status = match;
                    updateSwitchColor(switch2.isChecked(), switch2);

                    switch3.setChecked(chat != 0);
                    switch_3_status = chat;
                    updateSwitchColor(switch3.isChecked(), switch3);

                    isUpdatingSwitch = false;
                });

            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"NotificationConfirmationDialog\", \"method\": \"callbackGetNotification\", \"error\": \"" + e + "\"}", callbackLog).execute();
            EmptyResponse();
        }
    };


    // Обработка ответа на отпраку данных о рассылки для пользователя
    RequestUtils.Callback callbackSetNotification = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){
                requireActivity().runOnUiThread(() -> {

                    updateSwitchColor(switch1.isChecked(), switch1);
                    updateSwitchColor(switch2.isChecked(), switch2);
                    updateSwitchColor(switch3.isChecked(), switch3);
                });
            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"NotificationConfirmationDialog\", \"method\": \"callbackSetNotification\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
