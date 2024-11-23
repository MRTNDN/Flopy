package com.westernyey.Flopy.ui.settings.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.westernyey.Flopy.R;

public class DellConfirmationDialog extends BottomSheetDialogFragment {

    private final OnExitConfirmedListener listener;

    public interface OnExitConfirmedListener {
        void onExitConfirmed();
    }

    public DellConfirmationDialog(OnExitConfirmedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_dialog_dell_confirmation, container, false);
        view.findViewById(R.id.btn_yes).setOnClickListener(v -> {
            if (listener != null) {
                listener.onExitConfirmed();
            }
            dismiss();
        });
        view.findViewById(R.id.btn_no).setOnClickListener(v -> dismiss());
        return view;
    }
}
