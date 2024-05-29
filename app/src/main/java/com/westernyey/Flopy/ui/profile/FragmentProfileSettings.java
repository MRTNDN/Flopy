package com.westernyey.Flopy.ui.profile;

import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;
import static com.cripochec.Flopy.ui.utils.DataUtils.saveEntry;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentProfileSettings extends Fragment {
    private EditText editAboutMe, editName, editAge, editCity, editHeight;
    private LinearLayout containerAboutMe;
    private int count = 0;
    private int status;
    private int fullness;
    private String buf;
    Spinner spinnerGender, spinnerTarget, spinnerZodiacSign, spinnerEducation, spinnerChildren, spinnerSmoking, spinnerAlcohol;
    private TextView fullnessTextView;
    int previousPositionGender, previousPositionZodiacSign, previousPositionEducation,
            previousPositionChildren, previousPositionSmoking, previousPositionAlcohol = 0;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        // Инициализация элементов
        Button btnBac = rootView.findViewById(R.id.btn_bac);
        Button btnOk = rootView.findViewById(R.id.btn_ok);
        spinnerGender = rootView.findViewById(R.id.spinner_gender);
        spinnerTarget = rootView.findViewById(R.id.spinner_target);
        spinnerZodiacSign = rootView.findViewById(R.id.spinner_zadiac_sign);
        spinnerEducation = rootView.findViewById(R.id.spinner_education);
        spinnerChildren = rootView.findViewById(R.id.spinner_children);
        spinnerSmoking = rootView.findViewById(R.id.spinner_smoking);
        spinnerAlcohol = rootView.findViewById(R.id.spinner_alcohol);
        editAboutMe = rootView.findViewById(R.id.edit_about_me);
        editName = rootView.findViewById(R.id.edit_name);
        editAge = rootView.findViewById(R.id.edit_age);
        editCity = rootView.findViewById(R.id.edit_city);
        editHeight = rootView.findViewById(R.id.edit_height);
        fullnessTextView = rootView.findViewById(R.id.percent_of_full);

        Button btnAddAboutMe = rootView.findViewById(R.id.btn_add_about_me);
        containerAboutMe = rootView.findViewById(R.id.container_about_me);

        ImageView photo1 = rootView.findViewById(R.id.image1);
        ImageView photo2 = rootView.findViewById(R.id.image2);
        ImageView photo3 = rootView.findViewById(R.id.image3);
        ImageView photo4 = rootView.findViewById(R.id.image4);




        saveEntry(requireContext(), true);
        String imageUrl = "https://sun9-76.userapi.com/impg/rY4GWtRZHZsFs-PimjJJ9BRKDIZgFN8k_ZkEAg/IbDakXTl3MU.jpg?size=900x900&quality=96&sign=94c2de887e911cc8f089d75647b9cf64&c_uniq_tag=vxwB27avAu-ESkB25Sdgg24aKaRz0aJ1qr1816Zj_Oc&type=album";

        Glide.with(this)
                .load(imageUrl)
                .into(photo1);
//        photo1.setImageResource(R.color.castom_red);
        photo2.setImageResource(R.color.light_blue_600);
        photo3.setImageResource(R.color.purple_200);
        photo4.setImageResource(R.color.yelloww);

// Спинеры слушатели
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buf = String.valueOf(s);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (buf.isEmpty() && !String.valueOf(s).isEmpty()){
                    fullness+=10;
                } else if (!buf.isEmpty() && String.valueOf(s).isEmpty()){
                    fullness-=10;
                }
                fullnessTextView.setText("Заполнено на "+fullness+"%");
            }
        });
        editAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buf = String.valueOf(s);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (buf.isEmpty() && !String.valueOf(s).isEmpty()){
                    fullness+=10;
                } else if (!buf.isEmpty() && String.valueOf(s).isEmpty()){
                    fullness-=10;
                }
                fullnessTextView.setText("Заполнено на "+fullness+"%");
            }
        });
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousPositionGender == 0 && position != 0) {
                    fullness += 10;
                } else if (previousPositionGender != 0 && position == 0) {
                    fullness -= 10;
                }
                fullnessTextView.setText("Заполнено на " + fullness + "%");
                previousPositionGender = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        editCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buf = String.valueOf(s);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (buf.isEmpty() && !String.valueOf(s).isEmpty()){
                    fullness+=4;
                } else if (!buf.isEmpty() && String.valueOf(s).isEmpty()){
                    fullness-=4;
                }
                fullnessTextView.setText("Заполнено на "+fullness+"%");
            }
        });
        editHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buf = String.valueOf(s);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (buf.isEmpty() && !String.valueOf(s).isEmpty()){
                    fullness+=4;
                } else if (!buf.isEmpty() && String.valueOf(s).isEmpty()){
                    fullness-=4;
                }
                fullnessTextView.setText("Заполнено на "+fullness+"%");
            }
        });
        spinnerZodiacSign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousPositionZodiacSign == 0 && position != 0) {
                    fullness += 10;
                } else if (previousPositionZodiacSign != 0 && position == 0) {
                    fullness -= 10;
                }
                fullnessTextView.setText("Заполнено на " + fullness + "%");
                previousPositionZodiacSign = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousPositionEducation == 0 && position != 0) {
                    fullness += 10;
                } else if (previousPositionEducation != 0 && position == 0) {
                    fullness -= 10;
                }
                fullnessTextView.setText("Заполнено на " + fullness + "%");
                previousPositionEducation = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerChildren.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousPositionChildren == 0 && position != 0) {
                    fullness += 10;
                } else if (previousPositionChildren != 0 && position == 0) {
                    fullness -= 10;
                }
                fullnessTextView.setText("Заполнено на " + fullness + "%");
                previousPositionChildren = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerSmoking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousPositionSmoking == 0 && position != 0) {
                    fullness += 10;
                } else if (previousPositionSmoking != 0 && position == 0) {
                    fullness -= 10;
                }
                fullnessTextView.setText("Заполнено на " + fullness + "%");
                previousPositionSmoking = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerAlcohol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousPositionAlcohol == 0 && position != 0) {
                    fullness += 10;
                } else if (previousPositionAlcohol != 0 && position == 0) {
                    fullness -= 10;
                }
                fullnessTextView.setText("Заполнено на " + fullness + "%");
                previousPositionAlcohol = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


// Спинеры инициализация
        // Спинер полов
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.gender_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerGender.setAdapter(adapter);

        // Спинер цели
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.target_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerTarget.setAdapter(adapter);

        // Спинер знак зодиака
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.zodiac_sign_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerZodiacSign.setAdapter(adapter);

        // Спинер образование
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.education_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerEducation.setAdapter(adapter);

        // Спинер дети
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.children_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerChildren.setAdapter(adapter);

        // Спинер курение
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.smoking_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerSmoking.setAdapter(adapter);

        // Спинер алкоголь
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.alcohol_array, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Применяем кастомный макет
        spinnerAlcohol.setAdapter(adapter);


        btnBac.setOnClickListener(v -> {
        });

        btnOk.setOnClickListener(v -> {
            getData();
//            new android.os.Handler().postDelayed(
//                    () -> {
//                        if (status == 0){
//                            saveEntry(requireContext(), false);
//                            Fragment fragment = new FragmentSlider();
//                            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
//                        } else {
//                        }
//                    },
//                    5000
//            );

        });

        btnAddAboutMe.setOnClickListener(v -> {
            if (count < 3) {
                String text = editAboutMe.getText().toString();
                if (!text.isEmpty()) {
                    addTextView(text);
                    editAboutMe.setText("");
                    count++;
                    fullness+=8;
                    fullnessTextView.setText("Заполнено на "+fullness+"%");
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


    private void getData() {
        // Сбор данных из EditText и Spinner
        try {
            String name = editName.getText().toString();
            String age = editAge.getText().toString();
            String gender = spinnerGender.getSelectedItem().toString();
            String target = spinnerTarget.getSelectedItem().toString();
            String city = editCity.getText().toString();
            String height = editHeight.getText().toString();
            String zodiacSign = spinnerZodiacSign.getSelectedItem().toString();
            String education = spinnerEducation.getSelectedItem().toString();
            String children = spinnerChildren.getSelectedItem().toString();
            String smoking = spinnerSmoking.getSelectedItem().toString();
            String alcohol = spinnerAlcohol.getSelectedItem().toString();

            if (name.isEmpty() || age.isEmpty() || gender.isEmpty()){
                ToastUtils.showShortToast(getContext(), "все основные поля должны быть заполнены");
                return;
            }

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("id_person", getUserId(requireContext()));
            jsonRequestBody.put("name", name);
            jsonRequestBody.put("age", age);
            jsonRequestBody.put("gender", gender);
            jsonRequestBody.put("target", target);
            jsonRequestBody.put("about_me", getDataAboutMe());
            jsonRequestBody.put("city", city);
            jsonRequestBody.put("height", height);
            jsonRequestBody.put("zodiac_sign", zodiacSign);
            jsonRequestBody.put("education", education);
            jsonRequestBody.put("children", children);
            jsonRequestBody.put("smoking", smoking);
            jsonRequestBody.put("alcohol", alcohol);

            new RequestUtils(this, "save_persons_info", "POST", jsonRequestBody.toString(), callbackGetData).execute();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShortToast(getContext(), "Данные не сохранены!");
        }

    }

    private String getDataAboutMe() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < containerAboutMe.getChildCount(); i++) {
            View childView = containerAboutMe.getChildAt(i);
            if (childView instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) childView;
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    View grandChildView = linearLayout.getChildAt(j);
                    if (grandChildView instanceof TextView) {
                        TextView textView = (TextView) grandChildView;
                        list.add(textView.getText().toString());
                    }
                }
            }
        }
        list.remove(list.size() - 1);
        // Преобразуем список в JSON строку
        JSONArray jsonArray = new JSONArray(list);
        return jsonArray.toString();
    }


    @SuppressLint("SetTextI18n")
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
            containerAboutMe.removeView(linearLayout);
            count--;
            fullness-=8;
            fullnessTextView.setText("Заполнено на "+fullness+"%");
        });

        // Добавляем элементы в горизонтальный LinearLayout
        linearLayout.addView(textView);
        linearLayout.addView(deleteButton);

        containerAboutMe.addView(linearLayout);
    }

    RequestUtils.Callback callbackGetData = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };
}

