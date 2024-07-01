package com.westernyey.Flopy.ui.profile;

import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;
import static com.cripochec.Flopy.ui.utils.DataUtils.saveEntry;
import static com.cripochec.Flopy.ui.utils.DataUtils.saveFullness;

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

import com.cripochec.Flopy.ui.utils.DataUtils;
import com.cripochec.Flopy.ui.utils.FragmentUtils;
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
        fullnessTextView = rootView.findViewById(R.id.name_and_age);

        Button btnAddAboutMe = rootView.findViewById(R.id.btn_add_about_me);
        containerAboutMe = rootView.findViewById(R.id.container_about_me);

        // За каждое фото +8,5
        ImageView photo1 = rootView.findViewById(R.id.image1);
        ImageView photo2 = rootView.findViewById(R.id.image2);
        ImageView photo3 = rootView.findViewById(R.id.image3);
        ImageView photo4 = rootView.findViewById(R.id.image4);


//        String url1 = "https://belkiskastasarim.com.tr/uploads/yazilar/kucuk/yok.png";
//        String url1 = "http://90.156.231.211/root/FlopyPhoto/yok.png";
//        String url2 = "https://belkiskastasarim.com.tr/uploads/yazilar/kucuk/yok.png";
//        String url3 = "https://your-server.com/path/to/image3.jpg";
//        String url4 = "https://your-server.com/path/to/image4.jpg";

//        Glide.with(this).load(url1).into(photo1);
//        Glide.with(this).load(url2).into(photo2);
//        Glide.with(this).load(url3).into(photo3);
//        Glide.with(this).load(url4).into(photo4);



        photo1.setImageResource(R.color.castom_red);
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
                    fullness+=8;
                } else if (!buf.isEmpty() && String.valueOf(s).isEmpty()){
                    fullness-=8;
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
                    fullness+=8;
                } else if (!buf.isEmpty() && String.valueOf(s).isEmpty()){
                    fullness-=8;
                }
                fullnessTextView.setText("Заполнено на "+fullness+"%");
            }
        });
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousPositionGender == 0 && position != 0) {
                    fullness += 8;
                } else if (previousPositionGender != 0 && position == 0) {
                    fullness -= 8;
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
                    fullness += 4;
                } else if (previousPositionZodiacSign != 0 && position == 0) {
                    fullness -= 4;
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
                    fullness += 4;
                } else if (previousPositionEducation != 0 && position == 0) {
                    fullness -= 4;
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
                    fullness += 4;
                } else if (previousPositionChildren != 0 && position == 0) {
                    fullness -= 4;
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
                    fullness += 4;
                } else if (previousPositionSmoking != 0 && position == 0) {
                    fullness -= 4;
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
                    fullness += 4;
                } else if (previousPositionAlcohol != 0 && position == 0) {
                    fullness -= 4;
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


        btnBac.setOnClickListener(v -> new RequestUtils(this, "pars_persons_info", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\"}", callbackBacFragment).execute());


        btnOk.setOnClickListener(v -> getData());


        btnAddAboutMe.setOnClickListener(v -> {
            if (count < 3) {
                String text = editAboutMe.getText().toString();
                if (!text.isEmpty()) {
                    addTextView(text);
                    editAboutMe.setText("");
                    count++;
                    fullness+=4;
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


        if (!DataUtils.getEntry(requireContext())){
            new RequestUtils(this, "pars_persons_info", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\"}", callbackSetData).execute();
        }

        return rootView;
    }




    private void getData() {
        // Сбор данных из EditText и Spinner
        try {
            String name = editName.getText().toString();
            String age = editAge.getText().toString();

            String gender = spinnerGender.getSelectedItem().toString();
            int id_gender = 0;
            if (gender.equals("Мужской")){id_gender = 1;}
            if (gender.equals("Женский")){id_gender = 2;}

            String target = spinnerTarget.getSelectedItem().toString();
            int id_target = 0;
            if (target.equals("Свидания\nХодить на свидания и хорошо\nпроводить время")){id_target = 1;}
            if (target.equals("Отношения\nНайти вторую половинку")){id_target = 2;}
            if (target.equals("Общение без конкретики\nОбщяться, делиться мыслями")){id_target = 3;}

            String city = editCity.getText().toString();
            String height = editHeight.getText().toString();

            String zodiacSign = spinnerZodiacSign.getSelectedItem().toString();
            int id_zodiacSign = 0;
            if (zodiacSign.equals("Овен")){id_zodiacSign = 1;}
            if (zodiacSign.equals("Телец")){id_zodiacSign = 2;}
            if (zodiacSign.equals("Близнецы")){id_zodiacSign = 3;}
            if (zodiacSign.equals("Рак")){id_zodiacSign = 4;}
            if (zodiacSign.equals("Лев")){id_zodiacSign = 5;}
            if (zodiacSign.equals("Дева")){id_zodiacSign = 6;}
            if (zodiacSign.equals("Весы")){id_zodiacSign = 7;}
            if (zodiacSign.equals("Скорпион")){id_zodiacSign = 8;}
            if (zodiacSign.equals("Стрелец")){id_zodiacSign = 9;}
            if (zodiacSign.equals("Козерог")){id_zodiacSign = 10;}
            if (zodiacSign.equals("Водолей")){id_zodiacSign = 11;}
            if (zodiacSign.equals("Рыбы")){id_zodiacSign = 12;}

            String education = spinnerEducation.getSelectedItem().toString();
            int id_education = 0;
            if (education.equals("Среднее")){id_education = 1;}
            if (education.equals("Высшее")){id_education = 2;}
            if (education.equals("Аспирант/\nКандидат наук")){id_education = 3;}

            String children = spinnerChildren.getSelectedItem().toString();
            int id_children = 0;
            if (children.equals("Нет и не планирую")){id_children = 1;}
            if (children.equals("Нет, но хотелось бы")){id_children = 2;}
            if (children.equals("Уже есть")){id_children = 3;}

            String smoking = spinnerSmoking.getSelectedItem().toString();
            int id_smoking = 0;
            if (smoking.equals("Резко негативно")){id_smoking = 1;}
            if (smoking.equals("Нейтрально")){id_smoking = 2;}
            if (smoking.equals("Положительно")){id_smoking = 3;}

            String alcohol = spinnerAlcohol.getSelectedItem().toString();
            int id_alcohol = 0;
            if (alcohol.equals("Резко негативно")){id_alcohol = 1;}
            if (alcohol.equals("Нейтрально")){id_alcohol = 2;}
            if (alcohol.equals("Положительно")){id_alcohol = 3;}

            if (name.isEmpty() || age.isEmpty() || gender.isEmpty()){
                ToastUtils.showShortToast(getContext(), "Все основные и хотя бы 1 поле о себе должны быть заполнены");
                return;
            }

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("id_person", getUserId(requireContext()));
            jsonRequestBody.put("name", name);
            jsonRequestBody.put("age", age);
            jsonRequestBody.put("id_gender", id_gender);
            jsonRequestBody.put("id_target", id_target);
            jsonRequestBody.put("about_me", getDataAboutMe());
            jsonRequestBody.put("city", city);
            jsonRequestBody.put("height", height);
            jsonRequestBody.put("id_zodiac_sign", id_zodiacSign);
            jsonRequestBody.put("id_education", id_education);
            jsonRequestBody.put("id_children", id_children);
            jsonRequestBody.put("id_smoking", id_smoking);
            jsonRequestBody.put("id_alcohol", id_alcohol);

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
        if (list.isEmpty()){
            list.add("");
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
            fullness-=4;
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
            if (status == 0){
                saveEntry(requireContext(), false);
                saveFullness(requireContext(), fullness);
                fragment = new FragmentProfile();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            } else {
                handleEmptyResponse();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };

    @SuppressLint("SetTextI18n")
    RequestUtils.Callback callbackSetData = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");
            if (status == 0){
                spinnerGender.setSelection(jsonObject.getInt("id_gender"));
                spinnerTarget.setSelection(jsonObject.getInt("id_target"));

                // Получение "about_me" и преобразование его в массив строк
                JSONArray aboutMeArray = jsonObject.getJSONArray("about_me");
                String[] about_me_list = new String[aboutMeArray.length()];
                for (int i = 0; i < aboutMeArray.length(); i++) {
                    about_me_list[i] = aboutMeArray.getString(i);

                }

                requireActivity().runOnUiThread(() ->{
                    try {
                        for (String s : about_me_list) {
                            addTextView(s);
                            count++;
                            fullness += 4;
                        }

                        fullnessTextView.setText("Заполнено на " + fullness + "%");
                        editCity.setText(jsonObject.getString("city"));
                        editHeight.setText(jsonObject.getString("height"));
                        editName.setText(jsonObject.getString("name"));
                        editAge.setText(jsonObject.getString("age"));
                    } catch (Exception e){
                        ToastUtils.showShortToast(requireContext(), "Error: "+e);
                    }
                });

                spinnerZodiacSign.setSelection(jsonObject.getInt("id_zodiac_sign"));
                spinnerEducation.setSelection(jsonObject.getInt("id_education"));
                spinnerChildren.setSelection(jsonObject.getInt("id_children"));
                spinnerSmoking.setSelection(jsonObject.getInt("id_smoking"));
                spinnerAlcohol.setSelection(jsonObject.getInt("id_alcohol"));
            } else {
                handleEmptyResponse();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };

    @SuppressLint("SetTextI18n")
    RequestUtils.Callback callbackBacFragment = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");
            if (status == 0){
                fragment = new FragmentProfile();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            } else {
                requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(),
                        "Все основные и хотя бы 1 поле о себе должны быть заполнены"));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };

    // Обработка пустого ответа от сервера
    public void handleEmptyResponse() {
        requireActivity().runOnUiThread(() -> ToastUtils.showShortToast(requireContext(),
                "Ошибка сервера, попробуйте заново."+ "\nКод: "+status)); // Показываем сообщение об ошибке
    }
}

