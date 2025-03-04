package com.westernyey.Flopy.ui.profile;

import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;
import static com.cripochec.Flopy.ui.utils.DataUtils.saveEntry;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.westernyey.Flopy.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FragmentProfileSettings extends Fragment {
    private EditText editAboutMe, editName, editAge, editCity, editHeight;
    private LinearLayout containerAboutMe;
    private int count = 0;
    private int photoNumber = 0;
    private int fullness;
    Spinner spinnerGender, spinnerTarget, spinnerZodiacSign, spinnerEducation, spinnerChildren, spinnerSmoking, spinnerAlcohol;
    private TextView fullnessTextView;
    private Button btnBac, btnOk, btnAddAboutMe;
    int previousPositionGender, previousPositionZodiacSign, previousPositionEducation,
            previousPositionChildren, previousPositionSmoking, previousPositionAlcohol = 0;
    private final List<UserPhoto> photos = new ArrayList<>();
    private ImageView[] photoImageViews;


    @SuppressLint({"SetTextI18n", "DiscouragedApi"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        try {
            // Инициализация элементов
            initializeUI(rootView);

            // Установка слушателей для текстовых полей и спиннеров
            setWatchers();

            // Обработчики кнопок
            btnBac.setOnClickListener(v -> {
                try {
                JSONObject jsonRequestBody = new JSONObject();
                jsonRequestBody.put("id_person", getUserId(requireContext()));

                new RequestUtils(this, "pars_persons_info", "POST", jsonRequestBody.toString() , callbackExitFragment).execute();
                } catch (Exception e) {
                    new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"btnBac.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
                }
                });

            btnOk.setOnClickListener(v -> getData());

            btnAddAboutMe.setOnClickListener(v -> {
                if (count < 3) {
                    String text = editAboutMe.getText().toString();
                    if (!text.isEmpty()) {
                        addTextView(text);
                        editAboutMe.setText("");
                        count++;
                        fullness+=5;
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

            // Запрос данных о пользователе
            if (!DataUtils.getEntry(requireContext())){

                try {
                    JSONObject jsonRequestBody = new JSONObject();
                    jsonRequestBody.put("id_person", getUserId(requireContext()));

                    new RequestUtils(this, "pars_persons_info", "POST", jsonRequestBody.toString() , callbackSetData).execute();
                } catch (Exception e) {
                    new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"onCreateView.request\", \"error\": \"" + e + "\"}", callbackLog).execute();
                }
            }

        } catch (Exception e){
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"onCreateView\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }
        return rootView;
    }

    // Метод инициализации всех объектов UI
    private void initializeUI(View rootView) {
        try {
            // Спинеры
            spinnerGender = rootView.findViewById(R.id.spinner_gender);
            spinnerTarget = rootView.findViewById(R.id.spinner_target);
            spinnerZodiacSign = rootView.findViewById(R.id.spinner_zadiac_sign);
            spinnerEducation = rootView.findViewById(R.id.spinner_education);
            spinnerChildren = rootView.findViewById(R.id.spinner_children);
            spinnerSmoking = rootView.findViewById(R.id.spinner_smoking);
            spinnerAlcohol = rootView.findViewById(R.id.spinner_alcohol);

            // Текстовые поля
            editAboutMe = rootView.findViewById(R.id.edit_about_me);
            editName = rootView.findViewById(R.id.edit_name);
            editAge = rootView.findViewById(R.id.edit_age);
            editCity = rootView.findViewById(R.id.edit_city);
            editHeight = rootView.findViewById(R.id.edit_height);
            fullnessTextView = rootView.findViewById(R.id.name_and_age);

            // Фотографии
            photoImageViews = new ImageView[]{
                    rootView.findViewById(R.id.image1),
                    rootView.findViewById(R.id.image2),
                    rootView.findViewById(R.id.image3),
                    rootView.findViewById(R.id.image4)
            };

            // Создаём 4 объекта класса UserPhoto
            for (int i = 0; i < 4; i++) {
                photos.add(new UserPhoto());
            }


            // Кнопки
            btnBac = rootView.findViewById(R.id.btn_bac);
            btnOk = rootView.findViewById(R.id.btn_ok);
            btnAddAboutMe = rootView.findViewById(R.id.btn_add_about_me);
            containerAboutMe = rootView.findViewById(R.id.container_about_me);

            // Опции фото
            photoImageViews[0].setOnClickListener(v -> showOptionsDialog(photoImageViews[0], 0));
            photoImageViews[1].setOnClickListener(v -> showOptionsDialog(photoImageViews[1], 1));
            photoImageViews[2].setOnClickListener(v -> showOptionsDialog(photoImageViews[2], 2));
            photoImageViews[3].setOnClickListener(v -> showOptionsDialog(photoImageViews[3], 3));

            // Опции спинеров
            initializeSpinner(spinnerGender, R.array.gender_array);
            initializeSpinner(spinnerTarget, R.array.target_array);
            initializeSpinner(spinnerZodiacSign, R.array.zodiac_sign_array);
            initializeSpinner(spinnerEducation, R.array.education_array);
            initializeSpinner(spinnerChildren, R.array.children_array);
            initializeSpinner(spinnerSmoking, R.array.smoking_array);
            initializeSpinner(spinnerAlcohol, R.array.alcohol_array);
        } catch (Exception e){
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"initializeUI\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }

    }


    // Метод для установки слушателей объектов
    private void setWatchers() {
        try {
            // Слушатели для текстовых полей
            editName.addTextChangedListener(createTextWatcher(10));
            editAge.addTextChangedListener(createTextWatcher(10));
            editCity.addTextChangedListener(createTextWatcher(5));
            editHeight.addTextChangedListener(createTextWatcher(5));

            // Слушатели для спинеров
            spinnerGender.setOnItemSelectedListener(createSpinnerListener(10, new int[]{previousPositionGender}));
            spinnerZodiacSign.setOnItemSelectedListener(createSpinnerListener(5, new int[]{previousPositionZodiacSign}));
            spinnerEducation.setOnItemSelectedListener(createSpinnerListener(5, new int[]{previousPositionEducation}));
            spinnerChildren.setOnItemSelectedListener(createSpinnerListener(5, new int[]{previousPositionChildren}));
            spinnerSmoking.setOnItemSelectedListener(createSpinnerListener(5, new int[]{previousPositionSmoking}));
            spinnerAlcohol.setOnItemSelectedListener(createSpinnerListener(5, new int[]{previousPositionAlcohol}));
        } catch (Exception e){
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"setWatchers\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }

    }


    // Общиe методы для setWatchers
    private TextWatcher createTextWatcher(int value) {
        return new TextWatcher() {
            String buf = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buf = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (buf.isEmpty() && !s.toString().isEmpty()) {
                    fullness += value;
                } else if (!buf.isEmpty() && s.toString().isEmpty()) {
                    fullness -= value;
                }
                fullnessTextView.setText("Заполнено на " + fullness + "%");
            }
        };
    }

    private AdapterView.OnItemSelectedListener createSpinnerListener(int value, int[] previousPosition) {
        return new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousPosition[0] == 0 && position != 0) {
                    fullness += value;
                } else if (previousPosition[0] != 0 && position == 0) {
                    fullness -= value;
                }
                fullnessTextView.setText("Заполнено на " + fullness + "%");
                previousPosition[0] = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
    }


    // Метод для инициализации спиннеров
    private void initializeSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), arrayResId, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    // Метод сбора данных и отправка на сохранение
    private void getData() {
        try {
            // Сбор данных из EditText
            String name = editName.getText().toString();
            String age = editAge.getText().toString();
            String city = editCity.getText().toString();
            String height = editHeight.getText().toString();

            // Сбор данных из Spinner
            String gender = spinnerGender.getSelectedItem().toString();
            int id_gender = 0;
            if (gender.equals("Мужской")){id_gender = 1;}
            if (gender.equals("Женский")){id_gender = 2;}

            String target = spinnerTarget.getSelectedItem().toString();
            int id_target = 0;
            if (target.equals("Свидания\nХодить на свидания и хорошо\nпроводить время")){id_target = 1;}
            if (target.equals("Отношения\nНайти вторую половинку")){id_target = 2;}
            if (target.equals("Общение без конкретики\nОбщяться, делиться мыслями")){id_target = 3;}

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


            if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || photos.isEmpty()) {
                ToastUtils.showShortToast(getContext(), "Должны быть заполнены все основные данные и хотя бы одно фото");
                return;
            }

            // Формируем JSON для сохранения основной информации
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
            jsonRequestBody.put("fullness", fullness);

            // Отправка основной информации
            new RequestUtils(this, "save_persons_info", "POST", jsonRequestBody.toString(), callbackGetData).execute();


            // Отправка фотографий
            List<File> photoFiles = photos.stream()
                    .map(UserPhoto::getFile)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (photoFiles.isEmpty()) {
                new RequestUtils(this, "update_persons_photos", "POST", getDataPhotos(photos), callbackGetData).execute();
            } else {
                new RequestUtils(this, "save_persons_photos", "POST", getDataPhotos(photos), photoFiles, callbackGetData).execute();
            }


        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"getData\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }

    }

    // Функция получения данных о AboutMe
    private String getDataAboutMe() {
        List<String> list = new ArrayList<>();
        try {
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
        } catch (Exception e){
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"getDataAboutMe\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }

        // Преобразуем список в JSON строку
        JSONArray jsonArray = new JSONArray(list);
        return jsonArray.toString();
    }

    // Функция получения данных о фото
    public String getDataPhotos(List<UserPhoto> photos) {
        try {
            JSONArray photosArray = new JSONArray();

            // Обходим список объектов UserPhoto
            for (UserPhoto photo : photos) {
                JSONObject photoJson = new JSONObject();
                if (photo.getUrl() != null || photo.getFile() != null){
                    photoJson.put("url", photo.getUrl());
                    photoJson.put("file_path", photo.getFile());
                    photoJson.put("dominating", photo.getDominating());
                    photosArray.put(photoJson);
                }
            }

            JSONObject resultJson = new JSONObject();
            resultJson.put("id_person", getUserId(requireContext()));
            resultJson.put("photos", photosArray);

            return resultJson.toString();
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"getDataPhotos\", \"error\": \"" + e + "\"}", callbackLog).execute();
            return null;
        }
    }

    // Функция добавления текста в AboutMe
    @SuppressLint("SetTextI18n")
    private void addTextView(String text) {
        try {
            requireActivity().runOnUiThread(() -> {
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
                    fullness-=5;
                    fullnessTextView.setText("Заполнено на "+fullness+"%");
                });

                // Добавляем элементы в горизонтальный LinearLayout
                linearLayout.addView(textView);
                linearLayout.addView(deleteButton);

                containerAboutMe.addView(linearLayout);
            });

        } catch (Exception e){
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"addTextView\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }
    }

    // Диологовое окно фото
    @SuppressLint("SetTextI18n")
    private void showOptionsDialog(ImageView imageView, int photoNumber) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            this.photoNumber = photoNumber;

            // Проверяем изображение на заполниность
            Drawable drawable = imageView.getDrawable(); // Текущее изображение в ImageView
            String[] options;

            // Формируем список опций
            if (drawable == null) {
                options = new String[]{"Сделать фото", "Выбрать из галереи"};
            } else if (imageView.getId() == R.id.image1) {
                options = new String[]{"Удалить фото"};
            } else {
                options = new String[]{"Сделать главным", "Удалить фото"};
            }

            builder.setItems(options, (dialog, which) -> {
                switch (options[which]) {
                    case "Сделать фото":
                        ImagePicker.with(this)
                                .cameraOnly()        // Пользователь может сделать фото только с камеры
                                .crop(9f, 16f)       // Обрезка в пропорции 16:9
                                .compress(1024)      // Финальный размер изображения до 1 МБ
                                .start();
                        break;

                    case "Выбрать из галереи":
                        ImagePicker.with(this)
                                .galleryOnly()       // Пользователь выбирает только из галереи
                                .crop(9f, 16f)       // Обрезка в пропорции 16:9
                                .compress(1024)      // Финальный размер изображения до 1 МБ
                                .galleryMimeTypes(new String[]{"image/png", "image/jpg", "image/jpeg"})
                                .start();
                        break;

                    case "Удалить фото":
                        deletePhoto(photoNumber);
                        break;

                    case "Сделать главным":
                        setMainPhoto(photoNumber);
                        break;
                }
            });

            builder.show();
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"showOptionsDialog\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }
    }

    // Удаление фото
    @SuppressLint("SetTextI18n")
    private void deletePhoto(int photoNumber) {
        try {
            // Сбрасываем UserPhoto объект
            UserPhoto userPhoto = photos.get(photoNumber);
            userPhoto.setFile(null);
            userPhoto.setUrl(null);
            userPhoto.setDominating(-1);

            // Убираем изображение из ImageView
            photoImageViews[photoNumber].setImageDrawable(null);

            sortAndSetPhotosToImageViews();

            // Уменьшаем заполненность
            fullness -= 5;
            if (fullness < 0) fullness = 0;
            requireActivity().runOnUiThread(() -> fullnessTextView.setText("Заполнено на " + fullness + "%"));

        } catch (Exception e) {
            e.printStackTrace();
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"deletePhoto\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }
    }

    // Установка главного фото
    private void setMainPhoto(int photoNumber) {
        try {
            // Меняем местами главное фото и выбранное
            UserPhoto mainPhoto = photos.get(0);
            UserPhoto selectedPhoto = photos.get(photoNumber);

            mainPhoto.setDominating(photoNumber);
            selectedPhoto.setDominating(0);

            // Обновляем изображения в ImageView
            sortAndSetPhotosToImageViews();

        } catch (Exception e) {
            e.printStackTrace();
            new RequestUtils(this, "log", "POST",
                    "{\"module\": \"FragmentProfileSettings\", \"method\": \"setMainPhoto\", \"error\": \"" + e + "\"}",
                    callbackLog).execute();
        }
    }


    // Создание уникального файла для фото
    private File createUniqueFile(Context context) {
        try {
            String uniqueFileName = "temp_image_" + System.currentTimeMillis() + ".jpg";
            File storageDir = new File(context.getCacheDir(), "images");
            if (!storageDir.exists()) {
                storageDir.mkdirs(); // Создаём директорию, если её ещё нет
            }
            return new File(storageDir, uniqueFileName);
        } catch (Exception e) {
            e.printStackTrace();
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"createUniqueFile\", \"error\": \"" + e + "\"}", callbackLog).execute();
            return null;
        }
    }

    // Конвентор Uri в File
    private void copyUriToFile(Context context, Uri uri, File destinationFile) {
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while (true) {
                assert inputStream != null;
                if (!((length = inputStream.read(buffer)) > 0)) break;
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
        e.printStackTrace();
        new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"copyUriToFile\", \"error\": \"" + e + "\"}", callbackLog).execute();
    }
    }

    // Обработка ответа от выбора фото
    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (data != null) {
                Uri uri = data.getData();

                if (uri == null) throw new IllegalArgumentException("URI is null");

                // Создаём уникальный файл для сохранения изображения
                File uniqueFile = createUniqueFile(requireContext());

                // Копируем содержимое URI в уникальный файл
                copyUriToFile(requireContext(), uri, uniqueFile);

                // Получаем соответствующий UserPhoto объект
                UserPhoto userPhoto = photos.get(photoNumber); // Предполагается, что requestCode соответствует photoNumber

                // Обновляем данные UserPhoto
                userPhoto.setFile(uniqueFile); // Устанавливаем локальный файл
                userPhoto.setUrl(null);        // Если это локальное фото, URL не используется
                userPhoto.setDominating(photoNumber); // Привязываем номер фотографии

                // Обновляем соответствующий ImageView
                sortAndSetPhotosToImageViews();

                // Обновляем текстовую информацию о заполненности
                fullness += 5;
                requireActivity().runOnUiThread(() ->
                        fullnessTextView.setText("Заполнено на " + fullness + "%")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"onActivityResult\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }
    }

    private void sortAndSetPhotosToImageViews() {
        // 1. Фильтруем только фото с допустимым dominating (от 0 до 3)
        List<UserPhoto> sortedPhotos = new ArrayList<>();
        for (UserPhoto photo : photos) {
            if (photo.getDominating() >= 0 && photo.getDominating() < photoImageViews.length) {
                sortedPhotos.add(photo);
            }
        }

        // 2. Сортируем по dominating
        sortedPhotos.sort(Comparator.comparingInt(UserPhoto::getDominating));

        // 3. Корректируем dominating (если пропущены значения)
        for (int i = 0; i < sortedPhotos.size(); i++) {
            sortedPhotos.get(i).setDominating(i); // Присваиваем новый индекс
        }

        // 4. Очищаем ImageView перед установкой новых фото
        for (ImageView imageView : photoImageViews) {
            imageView.setImageDrawable(null);
        }

        // 5. Устанавливаем фото в ImageView
        for (UserPhoto photo : sortedPhotos) {
            int index = photo.getDominating();
            ImageView imageView = photoImageViews[index];

            if (photo.getFile() != null) {
                Glide.with(imageView.getContext())
                        .load(photo.getFile())
                        .into(imageView);
            } else if (photo.getUrl() != null) {
                Glide.with(imageView.getContext())
                        .load(photo.getUrl())
                        .into(imageView);
            }
        }
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

    // Обработка данных профиля
    RequestUtils.Callback callbackGetData = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){
                saveEntry(requireContext(), false);

                fragment = new FragmentProfile();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"callbackGetData\", \"error\": \"" + e + "\"}", callbackLog).execute();
            EmptyResponse();
        }
    };


    // Установка данных профиля
    @SuppressLint("SetTextI18n")
    RequestUtils.Callback callbackSetData = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){
                // Преобразование и отображение массива "about_me"
                JSONArray aboutMeArray = jsonObject.getJSONArray("about_me");
                String[] about_me_list = new String[aboutMeArray.length()];
                for (int i = 0; i < aboutMeArray.length(); i++) {
                    about_me_list[i] = aboutMeArray.getString(i);
                }
                for (String s : about_me_list) {
                    addTextView(s);
                    count++;
                }

                // Обновление UI в основном потоке
                requireActivity().runOnUiThread(() ->{
                    try {
                        spinnerGender.setSelection(jsonObject.getInt("id_gender"));
                        spinnerTarget.setSelection(jsonObject.getInt("id_target"));
                        fullnessTextView.setText(jsonObject.getString("fullness"));
                        editCity.setText(jsonObject.getString("city"));
                        editHeight.setText(jsonObject.getString("height"));
                        editName.setText(jsonObject.getString("name"));
                        editAge.setText(jsonObject.getString("age"));
                        spinnerZodiacSign.setSelection(jsonObject.getInt("id_zodiac_sign"));
                        spinnerEducation.setSelection(jsonObject.getInt("id_education"));
                        spinnerChildren.setSelection(jsonObject.getInt("id_children"));
                        spinnerSmoking.setSelection(jsonObject.getInt("id_smoking"));
                        spinnerAlcohol.setSelection(jsonObject.getInt("id_alcohol"));
                    } catch (Exception e){
                        new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"callbackSetData, requireActivity\", \"error\": \"" + e + "\"}", callbackLog).execute();
                    }
                });

                // Установка фотографий
                List<String> photo_url = new ArrayList<>();
                photo_url.add(jsonObject.getString("photo1_url"));
                photo_url.add(jsonObject.getString("photo2_url"));
                photo_url.add(jsonObject.getString("photo3_url"));
                photo_url.add(jsonObject.getString("photo4_url"));

                for (int i = 0; i<4; i++){
                    if (!Objects.equals(photo_url.get(i), "None")){
                        int finalI = i;
                        requireActivity().runOnUiThread(() ->
                                Glide.with(this).load(photo_url.get(finalI)).into(photoImageViews[finalI]));
                        UserPhoto userPhoto = photos.get(i);
                        userPhoto.setUrl(photo_url.get(i));
                        userPhoto.setDominating(i);
                    }
                }

            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"callbackSetData\", \"error\": \"" + e + "\"}", callbackLog).execute();
            EmptyResponse();
        }
    };

    @SuppressLint("SetTextI18n")
    RequestUtils.Callback callbackExitFragment = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){
                fragment = new FragmentProfile();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            } else {
                showErrorToast("Все поля со * должны быть заполнены");
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfileSettings\", \"method\": \"callbackBacFragment\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
