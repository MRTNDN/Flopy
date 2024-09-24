package com.westernyey.Flopy.ui.profile;

import static com.cripochec.Flopy.ui.utils.DataUtils.getPhoto1;
import static com.cripochec.Flopy.ui.utils.DataUtils.getPhoto2;
import static com.cripochec.Flopy.ui.utils.DataUtils.getPhoto3;
import static com.cripochec.Flopy.ui.utils.DataUtils.getPhoto4;
import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;
import static com.cripochec.Flopy.ui.utils.DataUtils.saveEntry;
import static com.cripochec.Flopy.ui.utils.DataUtils.saveFullness;
import static com.cripochec.Flopy.ui.utils.DataUtils.savePhoto1;
import static com.cripochec.Flopy.ui.utils.DataUtils.savePhoto2;
import static com.cripochec.Flopy.ui.utils.DataUtils.savePhoto3;
import static com.cripochec.Flopy.ui.utils.DataUtils.savePhoto4;
import static com.cripochec.Flopy.ui.utils.UriUtils.getFileFromUri;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cripochec.Flopy.ui.utils.DataUtils;
import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.RequestUtilsPhoto;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.westernyey.Flopy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentProfileSettings extends Fragment {
    private EditText editAboutMe, editName, editAge, editCity, editHeight;
    private LinearLayout containerAboutMe;
    private int count = 0;
    private int photoNumber = 0;
    private int status;
    private int fullness;
    private String buf;
    Spinner spinnerGender, spinnerTarget, spinnerZodiacSign, spinnerEducation, spinnerChildren, spinnerSmoking, spinnerAlcohol;
    private TextView fullnessTextView;
    private ImageView photo1, photo2, photo3, photo4;
    int previousPositionGender, previousPositionZodiacSign, previousPositionEducation,
            previousPositionChildren, previousPositionSmoking, previousPositionAlcohol = 0;

    @SuppressLint({"SetTextI18n", "DiscouragedApi"})
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
        photo1 = rootView.findViewById(R.id.image1);
        photo2 = rootView.findViewById(R.id.image2);
        photo3 = rootView.findViewById(R.id.image3);
        photo4 = rootView.findViewById(R.id.image4);

        photo1.setOnClickListener(v -> showOptionsDialog(photo1, getPhoto1(requireContext()), 1));
        photo2.setOnClickListener(v -> showOptionsDialog(photo2, getPhoto2(requireContext()), 2));
        photo3.setOnClickListener(v -> showOptionsDialog(photo3, getPhoto3(requireContext()), 3));
        photo4.setOnClickListener(v -> showOptionsDialog(photo4, getPhoto4(requireContext()), 4));

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
                    fullness += 5;
                } else if (previousPositionSmoking != 0 && position == 0) {
                    fullness -= 5;
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
                    fullness += 5;
                } else if (previousPositionAlcohol != 0 && position == 0) {
                    fullness -= 5;
                }
                fullnessTextView.setText("Заполнено на " + fullness + "%");
                previousPositionAlcohol = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


// Спинеры инициализация
        initializeSpinner(spinnerGender, R.array.gender_array);
        initializeSpinner(spinnerTarget, R.array.target_array);
        initializeSpinner(spinnerZodiacSign, R.array.zodiac_sign_array);
        initializeSpinner(spinnerEducation, R.array.education_array);
        initializeSpinner(spinnerChildren, R.array.children_array);
        initializeSpinner(spinnerSmoking, R.array.smoking_array);
        initializeSpinner(spinnerAlcohol, R.array.alcohol_array);

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
        } else {
            new RequestUtils(this, "pars_persons_photo", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\"}", callbackSetPhoto).execute();
        }
        return rootView;
    }

    // Метод для инициализации спиннера
    private void initializeSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(), arrayResId, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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

    @SuppressLint("SetTextI18n")
    private void showOptionsDialog(ImageView imageView, String imageUrl, int photoNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        // Проверяем, установлено ли изображение R.drawable.add_photo
        boolean isAddPhoto = imageView.getDrawable() != null && imageUrl.equals("add_photo");
        String[] options;

        if (isAddPhoto) {
            options = new String[]{"Сделать фото", "Выбрать из галереи"};
        } else if (imageView.getId() == R.id.image1) {
            options = new String[]{"Удалить фото"};
        } else {
            options = new String[]{"Сделать главным", "Удалить фото"};
        }

        builder.setItems(options, (dialog, which) -> {
            String selectedOption = options[which];
            switch (selectedOption) {
                case "Сделать фото":
                    ImagePicker.with(this)
                            .cameraOnly()	        //User can only capture image using Camera
                            .crop(9f, 16f)	//Crop image with 16:9 aspect ratio
                            .compress(1024)	//Final image size will be less than 1 MB
                            .start();
                    this.photoNumber = photoNumber;
                    break;
                case "Выбрать из галереи":
                    ImagePicker.with(this)
                            .galleryOnly()          //User can only select image from Gallery
                            .crop(9f, 16f)	//Crop image with 16:9 aspect ratio
                            .compress(1024)	//Final image size will be less than 1 MB
                            .galleryMimeTypes(new String[]{"image/png", "image/jpg", "image/jpeg"})
                            .start();
                    this.photoNumber = photoNumber;
                    break;
                case "Удалить фото":
                    photo1.setImageResource(R.drawable.add_photo);
                    photo2.setImageResource(R.drawable.add_photo);
                    photo3.setImageResource(R.drawable.add_photo);
                    photo4.setImageResource(R.drawable.add_photo);

                    fullness -= 8;
                    requireActivity().runOnUiThread(() -> fullnessTextView.setText("Заполнено на " + fullness + "%"));
                    saveFullness(requireContext(), fullness);

                    new RequestUtils(this, "delete_photo", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\", \"photo_url\": \""+ imageUrl +"\"}", callbackDeletePhoto).execute();
                    break;
                case "Сделать главным":
                    new RequestUtils(this, "make_main_photo", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\", \"photo_url\": \""+ imageUrl +"\"}", callbackMakeMainPhoto).execute();
                    break;
            }
        });

        builder.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Uri uri = data.getData();

        try {
            // Получение файла из URI через кэш
            File file = getFileFromUri(requireContext(), uri);  // Преобразуем URI в файл через кэш

            if (getPhoto1(requireContext()).equals("add_photo")){
                photoNumber = 1;
            } else if (getPhoto2(requireContext()).equals("add_photo")){
                photoNumber = 2;
            } else if (getPhoto3(requireContext()).equals("add_photo")){
                photoNumber = 3;
            } else if (getPhoto4(requireContext()).equals("add_photo")){
                photoNumber = 4;
            }

            fullness += 8;
            requireActivity().runOnUiThread(() -> fullnessTextView.setText("Заполнено на " + fullness + "%"));
            saveFullness(requireContext(), fullness);

            // Отправляем запрос на сервер с файлом
            new RequestUtilsPhoto(this, "save_persons_photo", "POST", file, getUserId(requireContext()), photoNumber, callbackSavePhoto).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        photoNumber = 0;
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

                savePhoto1(requireContext(), "add_photo");
                savePhoto2(requireContext(), "add_photo");
                savePhoto3(requireContext(), "add_photo");
                savePhoto4(requireContext(), "add_photo");

                String photo1_url = jsonObject.getString("photo1_url");
                if (!photo1_url.equals("None")){
                    savePhoto1(requireContext(), photo1_url);
                    requireActivity().runOnUiThread(() -> Glide.with(this).load(photo1_url).into(photo1));
                } else {
                    photo1.setImageResource(R.drawable.add_photo);
                }

                String photo2_url = jsonObject.getString("photo2_url");
                if (!photo2_url.equals("None")){
                    savePhoto2(requireContext(), photo2_url);
                    requireActivity().runOnUiThread(() -> Glide.with(this).load(photo2_url).into(photo2));
                } else {
                    photo2.setImageResource(R.drawable.add_photo);
                }

                String photo3_url = jsonObject.getString("photo3_url");
                if (!photo3_url.equals("None")){
                    savePhoto3(requireContext(), photo3_url);
                    requireActivity().runOnUiThread(() -> Glide.with(this).load(photo3_url).into(photo3));
                } else {
                    photo3.setImageResource(R.drawable.add_photo);
                }

                String photo4_url = jsonObject.getString("photo4_url");
                if (!photo4_url.equals("None")){
                    savePhoto4(requireContext(), photo4_url);
                    requireActivity().runOnUiThread(() -> Glide.with(this).load(photo4_url).into(photo4));
                } else {
                    photo4.setImageResource(R.drawable.add_photo);
                }
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

    RequestUtils.Callback callbackSetPhoto = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");
            if (status == 0){

                savePhoto1(requireContext(), "add_photo");
                savePhoto2(requireContext(), "add_photo");
                savePhoto3(requireContext(), "add_photo");
                savePhoto4(requireContext(), "add_photo");

                String photo1_url = jsonObject.getString("photo1_url");
                if (!photo1_url.equals("None")){
                    savePhoto1(requireContext(), photo1_url);
                    requireActivity().runOnUiThread(() -> Glide.with(this).load(photo1_url).into(photo1));
                } else {
                    photo1.setImageResource(R.drawable.add_photo);
                }

                String photo2_url = jsonObject.getString("photo2_url");
                if (!photo2_url.equals("None")){
                    savePhoto2(requireContext(), photo2_url);
                    requireActivity().runOnUiThread(() -> Glide.with(this).load(photo2_url).into(photo2));
                } else {
                    photo2.setImageResource(R.drawable.add_photo);
                }

                String photo3_url = jsonObject.getString("photo3_url");
                if (!photo3_url.equals("None")){
                    savePhoto3(requireContext(), photo3_url);
                    requireActivity().runOnUiThread(() -> Glide.with(this).load(photo3_url).into(photo3));
                } else {
                    photo3.setImageResource(R.drawable.add_photo);
                }

                String photo4_url = jsonObject.getString("photo4_url");
                if (!photo4_url.equals("None")){
                    savePhoto4(requireContext(), photo4_url);
                    requireActivity().runOnUiThread(() -> Glide.with(this).load(photo4_url).into(photo4));
                } else {
                    photo4.setImageResource(R.drawable.add_photo);
                }
            } else {
                handleEmptyResponse();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };

    RequestUtils.Callback callbackMakeMainPhoto = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");
            if (status == 0){
                new RequestUtils(this, "pars_persons_photo", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\"}", callbackSetPhoto).execute();
            } else {
                handleEmptyResponse();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };

    RequestUtils.Callback callbackDeletePhoto = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");
            if (status == 0){
                new RequestUtils(this, "pars_persons_photo", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\"}", callbackSetPhoto).execute();
            } else {
                handleEmptyResponse();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };

    RequestUtilsPhoto.Callback callbackSavePhoto = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");
            if (status == 0){
                new RequestUtils(this, "pars_persons_photo", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\"}", callbackSetPhoto).execute();
            } else {
                handleEmptyResponse();
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

