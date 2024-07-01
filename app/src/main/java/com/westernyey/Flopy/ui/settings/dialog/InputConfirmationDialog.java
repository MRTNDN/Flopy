package com.westernyey.Flopy.ui.settings.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.westernyey.Flopy.R;

public class InputConfirmationDialog extends BottomSheetDialogFragment {

    private final OnInputConfirmedListener listener;

    public interface OnInputConfirmedListener {
        void onExitConfirmed();
    }

    public InputConfirmationDialog(OnInputConfirmedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_dialog_input_confirmation, container, false);
    }
}
