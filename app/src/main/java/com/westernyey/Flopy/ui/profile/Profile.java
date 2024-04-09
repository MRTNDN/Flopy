package com.westernyey.Flopy.ui.profile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.westernyey.Flopy.R;

public class Profile extends Fragment {

    private EditText editName;
    private EditText editAge;
    private EditText editGender;
    private EditText editHeight;
    private EditText editWeight;
    private EditText editInterests;
    private EditText editDescription;
    private Spinner spinnerZodiac;
    private Spinner spinnerChildren;
    private Spinner spinnerAlcohol;
    private Spinner spinnerSmoking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);

        // Инициализация полей ввода
        editName = rootView.findViewById(R.id.editName);
        editAge = rootView.findViewById(R.id.editAge);
        editGender = rootView.findViewById(R.id.editGender);
        editHeight = rootView.findViewById(R.id.editHeight);
        editWeight = rootView.findViewById(R.id.editWeight);
        editInterests = rootView.findViewById(R.id.editInterests);
        editDescription = rootView.findViewById(R.id.editDescription);

        // Инициализация выпадающих списков
        spinnerZodiac = rootView.findViewById(R.id.spinnerZodiac);
        spinnerChildren = rootView.findViewById(R.id.spinnerChildren);
        spinnerAlcohol = rootView.findViewById(R.id.spinnerAlcohol);
        spinnerSmoking = rootView.findViewById(R.id.spinnerSmoking);

        // Заполнение выпадающих списков данными
        ArrayAdapter<CharSequence> zodiacAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.zodiac_signs, android.R.layout.simple_spinner_item);
        zodiacAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZodiac.setAdapter(zodiacAdapter);

        ArrayAdapter<CharSequence> yesNoAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.yes_no_options, android.R.layout.simple_spinner_item);
        yesNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChildren.setAdapter(yesNoAdapter);
        spinnerAlcohol.setAdapter(yesNoAdapter);
        spinnerSmoking.setAdapter(yesNoAdapter);

        // Добавление обработчика для поля editName
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateName(s.toString());
            }
        });
// Добавление обработчика для поля editAge
        editAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateAge(s.toString());
            }
        });

        // Добавление обработчика для поля editGender
        editGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateGender(s.toString());
            }
        });
// Добавление обработчика для поля editHeight
        editHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateHeight(s.toString());
            }
        });

// Добавление обработчика для поля editWeight
        editWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateWeight(s.toString());
            }
        });

// Добавление обработчика для поля editInterests
        editInterests.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateInterests(s.toString());
            }
        });

// Добавление обработчика для поля editDescription
        editDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateDescription(s.toString());
            }
        });

        return rootView;
    }

    private void validateName(String name) {
        if (name.length() < 2 || name.length() > 25 || !Character.isUpperCase(name.charAt(0))) {
            editName.setError("Имя должно быть с большой буквы и содержать от 2 до 25 символов");
        } else {
            editName.setError(null);
        }
    }

    private void validateAge(String age) {
        try {
            int ageValue = Integer.parseInt(age);
            if (ageValue < 18 || ageValue > 99) {
                editAge.setError("Возраст должен быть от 18 до 99 лет");
            } else {
                editAge.setError(null);
            }
        } catch (NumberFormatException e) {
            editAge.setError("Введите корректный возраст");
        }
    }

    private void validateGender(String gender) {
        // Проверяем, чтобы значение было не пустым
        if (gender.trim().isEmpty()) {
            editGender.setError("Введите пол");
        } else {
            editGender.setError(null);
        }
    }

    private void validateHeight(String height) {
        // Проверяем, чтобы значение было корректным числом
        try {
            double heightValue = Double.parseDouble(height);
            if (heightValue <= 0) {
                editHeight.setError("Введите корректный рост");
            } else {
                editHeight.setError(null);
            }
        } catch (NumberFormatException e) {
            editHeight.setError("Введите корректный рост");
        }
    }

    private void validateWeight(String weight) {
        // Проверяем, чтобы значение было корректным числом
        try {
            double weightValue = Double.parseDouble(weight);
            if (weightValue <= 0) {
                editWeight.setError("Введите корректный вес");
            } else {
                editWeight.setError(null);
            }
        } catch (NumberFormatException e) {
            editWeight.setError("Введите корректный вес");
        }
    }

    private void validateInterests(String interests) {
        // Проверяем, чтобы значение было не пустым
        if (interests.trim().isEmpty()) {
            editInterests.setError("Введите ваши интересы");
        } else {
            editInterests.setError(null);
        }
    }

    private void validateDescription(String description) {
        // Проверяем, чтобы значение было не пустым
        if (description.trim().isEmpty()) {
            editDescription.setError("Введите информацию о себе");
        } else {
            editDescription.setError(null);
        }
    }

}
