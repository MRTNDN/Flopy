package com.westernyey.Flopy.ui.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;

public class FragmentProfileSettings extends Fragment {
    private EditText editAboutMe;
    private Button btnAddAboutMe;
    private LinearLayout containerAboutMe;
    private int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        // Инициализация кнопок и бокового меню
        Button btnBac = rootView.findViewById(R.id.btn_bac);
        Button btnOk = rootView.findViewById(R.id.btn_ok);
        Spinner spinnerGender = rootView.findViewById(R.id.spinner_gender);
        Spinner spinnerTarget = rootView.findViewById(R.id.spinner_target);
        Spinner spinnerZadiacSign = rootView.findViewById(R.id.spinner_zadiac_sign);
        Spinner spinnerEducation = rootView.findViewById(R.id.spinner_education);
        Spinner spinnerChildren = rootView.findViewById(R.id.spinner_children);
        Spinner spinnerSmoking = rootView.findViewById(R.id.spinner_smoking);
        Spinner spinnerAlcohol = rootView.findViewById(R.id.spinner_alcohol);
        editAboutMe = rootView.findViewById(R.id.edit_about_me);
        btnAddAboutMe = rootView.findViewById(R.id.btn_add_about_me);
        containerAboutMe = rootView.findViewById(R.id.container_about_me);

        // Спинер полов
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.gender_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerGender.setAdapter(adapter);
        spinnerGender.setSelection(2); // "Не выбрано"

        // Спинер цели
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.target_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerTarget.setAdapter(adapter);
        spinnerTarget.setSelection(0); // "Дружба"

        // Спинер знак зодиака
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.zodiac_sign_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerZadiacSign.setAdapter(adapter);
        spinnerZadiacSign.setSelection(12); // "Не выбрано"

        // Спинер образование
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.education_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerEducation.setAdapter(adapter);
        spinnerEducation.setSelection(3); // "Не выбрано"

        // Спинер дети
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.children_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerChildren.setAdapter(adapter);
        spinnerChildren.setSelection(3); // "Не выбрано"

        // Спинер курение
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.smoking_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerSmoking.setAdapter(adapter);
        spinnerSmoking.setSelection(3); // "Не выбрано"

        // Спинер алкоголь
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.alcohol_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerAlcohol.setAdapter(adapter);
        spinnerAlcohol.setSelection(3); // "Не выбрано"


        btnBac.setOnClickListener(v -> {
            Fragment fragment = new FragmentProfile();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        btnOk.setOnClickListener(v -> {
//            StringBuilder message = new StringBuilder();
//            for (int i = 0; i < containerAboutMe.getChildCount(); i++) {
//                View childView = containerAboutMe.getChildAt(i);
//                if (childView instanceof LinearLayout) {
//                    LinearLayout linearLayout = (LinearLayout) childView;
//                    for (int j = 0; j < linearLayout.getChildCount(); j++) {
//                        View grandChildView = linearLayout.getChildAt(j);
//                        if (grandChildView instanceof TextView) {
//                            TextView textView = (TextView) grandChildView;
//                            message.append(textView.getText().toString()).append("\n");
//                        }
//                    }
//                }
//            }
//            ToastUtils.showShortToast(getContext(), message.toString());
        });

        btnAddAboutMe.setOnClickListener(v -> {
            if (count < 3) {
                String text = editAboutMe.getText().toString();
                if (!text.isEmpty()) {
                    addTextView(text);
                    editAboutMe.setText("");
                    count++;
                } else {
                    ToastUtils.showShortToast(getContext(), "Нельзя добавить пустую запись");
                    editAboutMe.setText("");
                }
            } else {
                ToastUtils.showShortToast(getContext(), "Можно добавить только 3 записи о себе");
                editAboutMe.setText("");
            }
        });
        return rootView;
    }

    private void addTextView(String text) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // Создаем горизонтальный LinearLayout
        LinearLayout linearLayout = new LinearLayout(requireContext());
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(0, 8, 0, 8); // Отступ между записями в 5dp

        // Создаем текстовое поле
        TextView textView = new TextView(requireContext());
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1; // Устанавливаем вес, чтобы текстовое поле занимало все доступное пространство
        textView.setLayoutParams(textParams);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.record_background)); // Устанавливаем фон
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Устанавливаем размер текста
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START); // Располагаем текст по левому краю и по вертикали по центру

        // Устанавливаем внутренние отступы
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
        textView.setPadding(padding, padding, padding, padding);

        // Создаем кнопку
        Button deleteButton = new Button(requireContext());
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()), // Устанавливаем размер кнопки
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics())); // Устанавливаем размер кнопки
        deleteButton.setLayoutParams(buttonParams);
        deleteButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.basic_check_mark_del)); // Устанавливаем фон
        deleteButton.setOnClickListener(v -> {
            containerAboutMe.removeView(linearLayout); // Удаляем всю строку, которая содержит текст и кнопку
            count--;
        });

        // Добавляем элементы в горизонтальный LinearLayout
        linearLayout.addView(textView);
        linearLayout.addView(deleteButton);

        containerAboutMe.addView(linearLayout);
    }


}

