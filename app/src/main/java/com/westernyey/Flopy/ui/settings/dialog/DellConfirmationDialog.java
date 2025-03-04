package com.westernyey.Flopy.ui.settings.dialog;

import static com.cripochec.Flopy.ui.utils.DataUtils.clearAllData;
import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.ActivityStart;

import org.json.JSONObject;

public class DellConfirmationDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_dialog_dell_confirmation, container, false);

        Button btnYes = view.findViewById(R.id.btn_yes);
        Button btnNo = view.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(v -> {
            try {
                JSONObject jsonRequestBody = new JSONObject();
                jsonRequestBody.put("id_person", getUserId(requireContext()));

                new RequestUtils(this, "delete_account", "POST", jsonRequestBody.toString() , callbackDeleteAccount).execute();

            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"DellConfirmationDialog\", \"method\": \"btnYes.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }
        });

        btnNo.setOnClickListener(v -> {
            try {
                // Закрыть модальное окно
                dismiss();
            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"DellConfirmationDialog\", \"method\": \"btnNo.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }
        });

        return view;
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


    // Обработка ответа на удаление пользователя
    RequestUtils.Callback callbackDeleteAccount = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0) {
                clearAllData(requireContext());

                Intent intent = new Intent(requireActivity(), ActivityStart.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                dismiss();
            } else {
                showErrorToast("Ошибка на сервере, status: " + jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"DeleteConfirmationDialog\", \"method\": \"callbackDeleteAccount\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
