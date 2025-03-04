package com.westernyey.Flopy.ui.filter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cripochec.Flopy.ui.utils.DataUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.westernyey.Flopy.R;

import java.util.Arrays;
import java.util.List;

public class FilterConfirmationDialog extends BottomSheetDialogFragment {

    private RadioButton btnAny, btnGirls, btnBoys, btn1, btn2, btn3, btn4;

    private FilterDialogListener listener;

    public void setFilterDialogListener(FilterDialogListener listener) {
        this.listener = listener;
    }

    public interface FilterDialogListener {
        void onFilterDialogDismissed();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filter_confirmation_dialog, container, false);

        // Инициализация элементов
        RadioGroup radioGroup = rootView.findViewById(R.id.radioGroup);
        btnAny = rootView.findViewById(R.id.btn_any);
        btnGirls = rootView.findViewById(R.id.btn_girls);
        btnBoys = rootView.findViewById(R.id.btn_boys);

        RadioGroup radioGroup2 = rootView.findViewById(R.id.radioGroupTarget);
        btn1 = rootView.findViewById(R.id.btn_1);
        btn2 = rootView.findViewById(R.id.btn_2);
        btn3 = rootView.findViewById(R.id.btn_3);
        btn4 = rootView.findViewById(R.id.btn_4);

        RangeSeekBar seekBar = rootView.findViewById(R.id.rangeSeekBar);
        TextView ageText = rootView.findViewById(R.id.textView7);

        // Логика обработки RadioGroup (пол)
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            resetButtonBackgrounds(Arrays.asList(btnAny, btnGirls, btnBoys));

            RadioButton selectedButton = rootView.findViewById(checkedId);
            selectedButton.setBackgroundResource(R.drawable.toggle_selected);
            selectedButton.setTextColor(getResources().getColor(R.color.white));

            if (checkedId == R.id.btn_any) DataUtils.saveFilterGender(requireContext(), 0);
            else if (checkedId == R.id.btn_girls) DataUtils.saveFilterGender(requireContext(), 1);
            else if (checkedId == R.id.btn_boys) DataUtils.saveFilterGender(requireContext(), 2);
        });

        // Логика обработки RadioGroup (цель)
        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            resetButtonBackgrounds(Arrays.asList(btn1, btn2, btn3, btn4));

            RadioButton selectedButton = rootView.findViewById(checkedId);
            selectedButton.setBackgroundResource(R.drawable.toggle_selected);
            selectedButton.setTextColor(getResources().getColor(R.color.white));

            if (checkedId == R.id.btn_1) DataUtils.saveFilterStatus(requireContext(), 0);
            else if (checkedId == R.id.btn_2) DataUtils.saveFilterStatus(requireContext(), 1);
            else if (checkedId == R.id.btn_3) DataUtils.saveFilterStatus(requireContext(), 2);
            else if (checkedId == R.id.btn_4) DataUtils.saveFilterStatus(requireContext(), 3);
        });

        // Логика для RangeSeekBar
        seekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                DataUtils.saveFilterMinAge(requireContext(), (int) leftValue);
                DataUtils.saveFilterMaxAge(requireContext(), (int) rightValue);
                ageText.setText("Возраст: " + (int) leftValue + " - " + (int) rightValue + " лет");
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {}

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {}
        });

        // Установка начального состояния кнопок (пол)
        int gender = DataUtils.getFilterGender(requireContext());
        if (gender == 0) btnAny.performClick();
        else if (gender == 1) btnGirls.performClick();
        else if (gender == 2) btnBoys.performClick();

        // Установка начального состояния кнопок (цель)
        int status = DataUtils.getFilterStatus(requireContext());
        if (status == 0) btn1.performClick();
        else if (status == 1) btn2.performClick();
        else if (status == 2) btn3.performClick();
        else if (status == 3) btn4.performClick();

        // Установка значений ползунка
        seekBar.setValue(DataUtils.getFilterMinAge(requireContext()), DataUtils.getFilterMaxAge(requireContext()));
        ageText.setText("Возраст: " + DataUtils.getFilterMinAge(requireContext()) + " - " + DataUtils.getFilterMaxAge(requireContext()) + " лет");

        return rootView;
    }

    private void resetButtonBackgrounds(List<RadioButton> buttons) {
        for (RadioButton button : buttons) {
            button.setBackgroundResource(R.drawable.toggle_unselected);
            button.setTextColor(getResources().getColor(R.color.dark_grey));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener != null) {
            listener.onFilterDialogDismissed();
        }
    }
}
