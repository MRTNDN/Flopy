package com.westernyey.Flopy.ui.cardModel;

import static com.cripochec.Flopy.ui.utils.DataUtils.getFilterGender;
import static com.cripochec.Flopy.ui.utils.DataUtils.getFilterMaxAge;
import static com.cripochec.Flopy.ui.utils.DataUtils.getFilterMinAge;
import static com.cripochec.Flopy.ui.utils.DataUtils.getFilterStatus;
import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;

import android.app.Activity;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Swap extends Fragment {
    private CardAdapter cardAdapter;
    private List<CardModel> data;
    private SwipeFlingAdapterView flingAdapterView;
    private CardModel removedCard = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_swap, container, false);

        flingAdapterView = rootView.findViewById(R.id.swipe);

        Button dislike = rootView.findViewById(R.id.dislike);
        Button back = rootView.findViewById(R.id.back);
        Button super_like = rootView.findViewById(R.id.super_like);
        Button like = rootView.findViewById(R.id.like);

        like.setOnClickListener(v -> flingAdapterView.getTopCardListener().selectRight());

        dislike.setOnClickListener(v -> flingAdapterView.getTopCardListener().selectLeft());

        back.setOnClickListener(v -> {
            if (removedCard != null) {
                data.add(1, removedCard); // Добавляем сохранённую карточку в начало списка
                removedCard = null; // Очищаем переменную

                // Обновляем адаптер
                cardAdapter.notifyDataSetChanged();
                flingAdapterView.requestLayout(); // Запрос на перерисовку списка

                back.setBackgroundResource(R.drawable.swap_but_back);
                back.setEnabled(false);

                // Сбрасываем индекс изображения у первой карточки
                if (!data.isEmpty()) {
                    data.get(0).resetImageIndex();
                }
            }
        });

        super_like.setOnClickListener(v -> {
            //
            showErrorToast("В разработке");
        });

        back.setEnabled(false);

        try {
            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("id_person", getUserId(requireContext()));
            jsonRequestBody.put("min_age", getFilterMinAge(requireContext()));
            jsonRequestBody.put("max_age", getFilterMaxAge(requireContext()));
            jsonRequestBody.put("id_gender", getFilterGender(requireContext()));
            jsonRequestBody.put("id_target", getFilterStatus(requireContext()));

            data = new ArrayList<>();
            cardAdapter = new CardAdapter(getActivity(), R.layout.card_item_for_swap, data);
            flingAdapterView.setAdapter(cardAdapter);

            new RequestUtils(this, "pars_persons_list", "POST", jsonRequestBody.toString(), callbackSetList).execute();
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"Swap\", \"method\": \"onCreateView\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }


        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                if (!data.isEmpty()) {
                    removedCard = data.get(0); // Сохраняем первый объект
                    back.setBackgroundResource(R.drawable.swap_but_back_on);
                    back.setEnabled(true);

                    data.remove(0);
                    if (!data.isEmpty()) {
                        data.get(0).resetImageIndex(); // Сброс фото на первой картинке для новой анкеты
                    }
                    cardAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLeftCardExit(Object o) {
                try {
                    CardModel card = (CardModel) o; // Получаем данные текущей карточки
                    JSONObject jsonRequestBody = new JSONObject();
                    jsonRequestBody.put("id_person", getUserId(requireContext()));
                    jsonRequestBody.put("id_second_person", card.getIdPerson()); // Передаем ID человека на анкете

                    new RequestUtils(Swap.this, "dislike_person", "POST", jsonRequestBody.toString(), callbackDislikePerson).execute();
                } catch (Exception e) {
                    new RequestUtils(Swap.this, "log", "POST", "{\"module\": \"Swap\", \"method\": \"onLeftCardExit\", \"error\": \"" + e + "\"}", callbackLog).execute();
                }
                Toast.makeText(getActivity(), "dislike", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                try {
                    CardModel card = (CardModel) o; // Получаем данные текущей карточки
                    JSONObject jsonRequestBody = new JSONObject();
                    jsonRequestBody.put("id_person", getUserId(requireContext()));
                    jsonRequestBody.put("id_second_person", card.getIdPerson()); // Передаем ID человека на анкете

                    new RequestUtils(Swap.this, "like_person", "POST", jsonRequestBody.toString(), callbackLikePerson).execute();
                } catch (Exception e) {
                    new RequestUtils(Swap.this, "log", "POST", "{\"module\": \"Swap\", \"method\": \"onRightCardExit\", \"error\": \"" + e + "\"}", callbackLog).execute();
                }
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

    // Обнавление cardAdapter
    public void updateCards() {
        try {
            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("id_person", getUserId(requireContext()));
            jsonRequestBody.put("min_age", getFilterMinAge(requireContext()));
            jsonRequestBody.put("max_age", getFilterMaxAge(requireContext()));
            jsonRequestBody.put("id_gender", getFilterGender(requireContext()));
            jsonRequestBody.put("id_target", getFilterStatus(requireContext()));

            // Отправляем новый запрос для обновления списка карт
            new RequestUtils(this, "pars_persons_list", "POST", jsonRequestBody.toString(), callbackSetList).execute();
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"Swap\", \"method\": \"updateCards\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
            e.printStackTrace(); // Логируем ошибку
            showErrorToast("Ошибка логирования на клиенте.");
        }
    };


    RequestUtils.Callback callbackSetList = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0) {
                JSONArray persons = jsonObject.getJSONArray("persons");
                data.clear();

                for (int i = 0; i < persons.length(); i++) {
                    JSONObject person = persons.getJSONObject(i);
                    int id_person = person.getInt("id_person");
                    String name = person.getString("name");
                    int age = person.getInt("age");
                    String city = person.getString("city");
                    String distance = "20 км.";
                    String target = "Дружба";
                    switch (getFilterStatus(requireContext())) {
                        case 1: target = "Свидания"; break;
                        case 2: target = "Отношения"; break;
                        case 3: target = "Общение"; break;
                    }

                    List<String> AdditionalInfo = new ArrayList<>();
                    if (person.has("height") && !person.getString("height").isEmpty()) {
                        AdditionalInfo.add("Рост: "+person.getString("height"));
                    }

                    String zodiac_sign;
                    switch (person.getString("id_zodiac_sign")) {
                        case "1":
                            zodiac_sign = "Овен";
                            break;
                        case "2":
                            zodiac_sign = "Телец";
                            break;
                        case "3":
                            zodiac_sign = "Близнецы";
                            break;
                        case "4":
                            zodiac_sign = "Рак";
                            break;
                        case "5":
                            zodiac_sign = "Лев";
                            break;
                        case "6":
                            zodiac_sign = "Дева";
                            break;
                        case "7":
                            zodiac_sign = "Весы";
                            break;
                        case "8":
                            zodiac_sign = "Скорпион";
                            break;
                        case "9":
                            zodiac_sign = "Стрелец";
                            break;
                        case "10":
                            zodiac_sign = "Козерог";
                            break;
                        case "11":
                            zodiac_sign = "Водолей";
                            break;
                        case "12":
                            zodiac_sign = "Рыбы";
                            break;
                        default:
                            zodiac_sign = "";
                    }

                    if (!zodiac_sign.equals("")){
                        AdditionalInfo.add(zodiac_sign);
                    }

                    String education;
                    switch (person.getString("id_education")) {
                        case "1":
                            education = "Среднее";
                            break;
                        case "2":
                            education = "Высшее";
                            break;
                        case "3":
                            education = "Аспирант";
                            break;
                        default:
                            education = "";
                    }

                    if (!education.equals("")){
                        AdditionalInfo.add("Образование: "+education);
                    }

                    String children;
                    switch (person.getString("id_children")) {
                        case "1":
                            children = "Нет и не планирую";
                            break;
                        case "2":
                            children = "Нет, но хотелось бы";
                            break;
                        case "3":
                            children = "Уже есть";
                            break;
                        default:
                            children = "";
                    }

                    if (!children.equals("")){
                        AdditionalInfo.add("Дети: "+children);
                    }

                    String smoking;
                    switch (person.getString("id_smoking")) {
                        case "1":
                            smoking = "Негативно";
                            break;
                        case "2":
                            smoking = "Нейтрально";
                            break;
                        case "3":
                            smoking = "Положительно";
                            break;
                        default:
                            smoking = "";
                    }

                    if (!smoking.equals("")){
                        AdditionalInfo.add("Курение: "+smoking);
                    }

                    String alcohol;
                    switch (person.getString("id_alcohol")) {
                        case "1":
                            alcohol = "Негативно";
                            break;
                        case "2":
                            alcohol = "Нейтрально";
                            break;
                        case "3":
                            alcohol = "Положительно";
                            break;
                        default:
                            alcohol = "";
                    }

                    if (!alcohol.equals("")){
                        AdditionalInfo.add("Алкаголь: "+alcohol);
                    }


                    // Получаем массив persons
                    JSONArray personsArray = jsonObject.optJSONArray("persons");
                    List<String> photo_url = new ArrayList<>();

                    if (personsArray != null && personsArray.length() > 0) {
                        JSONObject personObject = personsArray.optJSONObject(i); // Берем первый объект

                        if (personObject != null) {

                            photo_url.add(personObject.optString("photo1_url", ""));
                            photo_url.add(personObject.optString("photo2_url", ""));
                            photo_url.add(personObject.optString("photo3_url", ""));
                            photo_url.add(personObject.optString("photo4_url", ""));

                            // Фильтруем "None" и пустые строки
                            photo_url.removeIf(url -> url.equals("None") || url.isEmpty());


                            // о себе
                            JSONArray aboutMeArray = personObject.optJSONArray("about_me");
                            if (aboutMeArray != null) {
                                for (int n = 0; n < aboutMeArray.length(); n++) {
                                    AdditionalInfo.add(aboutMeArray.optString(n, ""));
                                }
                            }
                        }
                    }

                    CardModel card = new CardModel(id_person, name, String.valueOf(age), city, distance, target, photo_url, AdditionalInfo);
                    card.resetImageIndex(); // Сбрасываем индекс изображения на первую для новой анкеты
                    data.add(card); // Добавляем карточку в список
                }

                Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(() -> cardAdapter.notifyDataSetChanged());
                }
            } else {
                showErrorToast("Ошибка на стороне сервера ERROR: " + jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            requireActivity().runOnUiThread(() -> {
                new RequestUtils(this, "log", "POST", "{\"module\": \"Swap\", \"method\": \"callbackSetList\", \"error\": \"" + e + "\"}", callbackLog).execute();
                EmptyResponse();
            });
        }
    };


    RequestUtils.Callback callbackDislikePerson = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0) {

            } else {
                showErrorToast("Ошибка на стороне сервера ERROR: " + jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            requireActivity().runOnUiThread(() -> {
                new RequestUtils(this, "log", "POST", "{\"module\": \"Swap\", \"method\": \"callbackDislikePerson\", \"error\": \"" + e + "\"}", callbackLog).execute();
                EmptyResponse();
            });
        }
    };

    RequestUtils.Callback callbackLikePerson = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0) {

            } else {
                showErrorToast("Ошибка на стороне сервера ERROR: " + jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            requireActivity().runOnUiThread(() -> {
                new RequestUtils(this, "log", "POST", "{\"module\": \"Swap\", \"method\": \"callbackLikePerson\", \"error\": \"" + e + "\"}", callbackLog).execute();
                EmptyResponse();
            });
        }
    };
    private void showErrorToast(String message) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> ToastUtils.showShortToast(requireContext(), message));
        }
    }

    public void EmptyResponse() {
        showErrorToast("Ошибка callback.");
    }
}
