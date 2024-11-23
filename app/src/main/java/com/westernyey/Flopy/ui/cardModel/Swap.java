package com.westernyey.Flopy.ui.cardModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.westernyey.Flopy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Swap extends Fragment {
    private CardAdapter cardAdapter;
    private List<CardModel> data;
    private SwipeFlingAdapterView flingAdapterView;
    private int status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_swap, container, false);

        flingAdapterView = rootView.findViewById(R.id.swipe);

        // Инициализация кнопок
        Button dislike = rootView.findViewById(R.id.dislike);
        Button back = rootView.findViewById(R.id.back);
        Button super_like = rootView.findViewById(R.id.super_like);
        Button like = rootView.findViewById(R.id.like);


        like.setOnClickListener(v -> {
            flingAdapterView.getTopCardListener().selectRight();
        });

        dislike.setOnClickListener(v -> {
            flingAdapterView.getTopCardListener().selectLeft();
        });

        back.setOnClickListener(v -> {

        });

        super_like.setOnClickListener(v -> {

        });


        data = new ArrayList<>();

        new RequestUtils(this, "pars_persons_list", "POST",
                "{\"min_age\": \"" + 18 + "\", " +
                        "\"max_age\": \"" + 60 + "\", " +
                        "\"id_gender\": \"" + 1 + "\"," +
                        "\"id_target\": \"" + 1 + "\"}", callbackSetList).execute();


        List<Integer> boberImages = Arrays.asList(R.drawable.bober, R.drawable.djtape, R.drawable.druc, R.drawable.ezh);
        List<String> boberAdditionalInfo = Arrays.asList("Информация 1", "Информация 2", "Информация 3"); // Пример дополнительной информации
        data.add(new CardModel("Егор, 21", "Екатеринбург", "20 км.", "Дружба", boberImages, boberAdditionalInfo));


        cardAdapter = new CardAdapter(getActivity(), R.layout.card_item_for_swap, data);

        flingAdapterView.setAdapter(cardAdapter);


        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                data.remove(0);
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(getActivity(), "dislike", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(getActivity(), "like", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                // Handle if needed
            }

            @Override
            public void onScroll(float v) {
                // Handle if needed
            }
        });

        return rootView;


    }
    RequestUtils.Callback callbackSetList = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            this.status = jsonObject.getInt("status");

            if (status == 0) {
                List<Integer> allImages = Arrays.asList(R.drawable.bober, R.drawable.djtape, R.drawable.druc, R.drawable.ezh, R.drawable.metin, R.drawable.monkey, R.drawable.neupok, R.drawable.oksana);

                // Получаем список пользователей
                JSONArray persons = jsonObject.getJSONArray("persons");

                for (int i = 0; i < persons.length(); i++) {
                    JSONObject person = persons.getJSONObject(i);
                    List<String> additionalInfo = new ArrayList<>();

                    String name = person.getString("name");
                    int age = person.getInt("age");
                    String nameAndAge;
                    if (name.length() > 10){
                        nameAndAge = name + ",\n"+ age;
                    } else {
                        nameAndAge = name + ", "+ age;
                    }


                    String city = person.getString("city");

                    String target = person.getString("id_target");
                    switch (target) {
                        case "0":
                            target = "Дружба";
                            break;
                        case "1":
                            target = "Свидания";
                            break;
                        case "2":
                            target = "Отношения";
                            break;
                        case "3":
                            target = "Общение";
                            break;
                    }

                    String distance = "20 км.";

                    String height = person.getString("height");
                    if (!height.isEmpty()){
                        additionalInfo.add(height);
                    }

                    String id_zodiac_sign = person.getString("id_zodiac_sign");
                    switch (id_zodiac_sign) {
                        case "0":
                            break;
                        case "1":
                            additionalInfo.add("Овен");
                            break;
                        case "2":
                            additionalInfo.add("Телец");
                            break;
                        case "3":
                            additionalInfo.add("Близнецы");
                            break;
                        case "4":
                            additionalInfo.add("Рак");
                            break;
                        case "5":
                            additionalInfo.add("Лев");
                            break;
                        case "6":
                            additionalInfo.add("Дева");
                            break;
                        case "7":
                            additionalInfo.add("Весы");
                            break;
                        case "8":
                            additionalInfo.add("Скорпион");
                            break;
                        case "9":
                            additionalInfo.add("Стрелец");
                            break;
                        case "10":
                            additionalInfo.add("Козерог");
                            break;
                        case "11":
                            additionalInfo.add("Водолей");
                            break;
                        case "12":
                            additionalInfo.add("Рыбы");
                            break;
                    }

                    String id_education = person.getString("id_education");
                    switch (id_education) {
                        case "0":
                            break;
                        case "1":
                            additionalInfo.add("Образование: Среднее");
                            break;
                        case "2":
                            additionalInfo.add("Образование: Высшее");
                            break;
                        case "3":
                            additionalInfo.add("Образование: Аспирант");
                            break;
                    }

                    String id_children = person.getString("id_children");
                    switch (id_children) {
                        case "0":
                            break;
                        case "1":
                            additionalInfo.add("Дети: Нет и не планирую");
                            break;
                        case "2":
                            additionalInfo.add("Дети: Нет, но хотелось бы");
                            break;
                        case "3":
                            additionalInfo.add("Дети: Уже есть");
                            break;
                    }

                    String id_smoking = person.getString("id_smoking");
                    switch (id_smoking) {
                        case "0":
                            break;
                        case "1":
                            additionalInfo.add("Курение: Резко негативно");
                            break;
                        case "2":
                            additionalInfo.add("Курение: Нейтрально");
                            break;
                        case "3":
                            additionalInfo.add("Курение: Положительно");
                            break;
                    }

                    String id_alcohol = person.getString("id_alcohol");
                    switch (id_alcohol) {
                        case "0":
                            break;
                        case "1":
                            additionalInfo.add("Алкоголь: Резко негативно");
                            break;
                        case "2":
                            additionalInfo.add("Алкоголь: Нейтрально");
                            break;
                        case "3":
                            additionalInfo.add("Алкоголь: Положительно");
                            break;
                    }

                    // Добавляем по 1-2 случайным изображениям из доступных
                    List<Integer> images = new ArrayList<>();
                    images.add(allImages.get((int) (Math.random() * allImages.size())));
                    if (Math.random() > 0.5) {
                        images.add(allImages.get((int) (Math.random() * allImages.size())));
                    }


                    // Информация о себе
                    // Получение "about_me" и преобразование его в массив строк
                    String about_me = person.getString("about_me");
                    additionalInfo.add(about_me);
//                    JSONArray aboutMeArray = jsonObject.getJSONArray("about_me");
//                    String[] about_me = new String[aboutMeArray.length()];
//                    for (int n = 0; n < aboutMeArray.length(); n++) {
//                        // Извлекаем строку "о себе"
//                        about_me[n] = aboutMeArray.getString(n);
//
//                        // Добавляем строку в additionalInfo
//                        additionalInfo.add(about_me[n]);
//                    }


                    // Добавляем карточку пользователя в data
                    data.add(new CardModel(nameAndAge, city, distance, target, images, additionalInfo));
                }

                // Обновляем адаптер
                cardAdapter.notifyDataSetChanged();

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
