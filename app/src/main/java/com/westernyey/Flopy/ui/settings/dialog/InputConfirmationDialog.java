package com.westernyey.Flopy.ui.settings.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.westernyey.Flopy.R;

public class InputConfirmationDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_dialog_input_confirmation, container, false);

        ConstraintLayout layout1 = rootView.findViewById(R.id.Layout1);
        ConstraintLayout layout2 = rootView.findViewById(R.id.Layout2);
        ConstraintLayout layout3 = rootView.findViewById(R.id.Layout3);

        layout1.setOnClickListener(v -> {
            ToastUtils.showShortToast(requireContext(), "Уже подключенно");
        });

        layout2.setOnClickListener(v -> {
            ToastUtils.showShortToast(requireContext(), "В разработке");
        });

        layout3.setOnClickListener(v -> {
            ToastUtils.showShortToast(requireContext(), "В разработке");
        });
        return rootView;
    }
}
