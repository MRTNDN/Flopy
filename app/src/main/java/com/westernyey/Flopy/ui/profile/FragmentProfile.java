package com.westernyey.Flopy.ui.profile;

import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;
import static com.cripochec.Flopy.ui.utils.DataUtils.saveLastFragmentPosition;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.settings.FragmentSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentProfile extends Fragment {

    private ProgressBar progressBar;
    private TextView tvFillPercentage, name_and_age;
    private LinearLayout containerAboutMe;
    private ImageView[] photoImageViews;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        try {
        // Инициализация кнопок и бокового меню
        Button btnOpenMenu = rootView.findViewById(R.id.btn_open_menu);
        Button btnOpenSet = rootView.findViewById(R.id.btn_open_settings);
        Button btnOpenProfSett = rootView.findViewById(R.id.btn_open_profile_settings);
        containerAboutMe = rootView.findViewById(R.id.about_me_container);

        progressBar = rootView.findViewById(R.id.progress_circular);
        tvFillPercentage = rootView.findViewById(R.id.tv_progress_percentage);
        name_and_age = rootView.findViewById(R.id.name_and_age);

        // Фотографии
        photoImageViews = new ImageView[]{
                rootView.findViewById(R.id.profile_image_view_1),
                rootView.findViewById(R.id.profile_image_view_2),
                rootView.findViewById(R.id.profile_image_view_3),
                rootView.findViewById(R.id.profile_image_view_4)
        };


        btnOpenMenu.setOnClickListener(v -> {
            DrawerLayout drawerLayout = requireActivity().findViewById(R.id.drawer_layout);
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        btnOpenSet.setOnClickListener(v -> {
            saveLastFragmentPosition(requireContext(), "profile");
            Fragment fragment = new FragmentSettings();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        btnOpenProfSett.setOnClickListener(v -> {
            Fragment fragment = new FragmentProfileSettings();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        // Отправка запроса на сервер для регистрации
        JSONObject loginData = new JSONObject();
            loginData.put("id_person", getUserId(requireContext()));


        new RequestUtils(this, "pars_persons_info", "POST", loginData.toString(), callbackSetData).execute();

        } catch (JSONException e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentProfile\", \"method\": \"onCreateView\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void addTextView(String text) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
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

        // Добавляем элементы в горизонтальный LinearLayout
        linearLayout.addView(textView);

        containerAboutMe.addView(linearLayout);
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

    @SuppressLint("SetTextI18n")
    RequestUtils.Callback callbackSetData = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int status = jsonObject.optInt("status", -1);

            if (status == 0) {
                // Загрузка фотографий
                List<String> photo_url = new ArrayList<>();
                photo_url.add(jsonObject.optString("photo1_url", ""));
                photo_url.add(jsonObject.optString("photo2_url", ""));
                photo_url.add(jsonObject.optString("photo3_url", ""));
                photo_url.add(jsonObject.optString("photo4_url", ""));

                requireActivity().runOnUiThread(() -> {
                    for (int i = 0; i < 4; i++) {
                        if (!"None".equals(photo_url.get(i))) {
                            Glide.with(this)
                                    .load(photo_url.get(i))
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.error_image)
                                    .into(photoImageViews[i]);
                        } else {
                            photoImageViews[i].setVisibility(View.GONE);
                        }
                    }
                });


                // Обработка "about_me"
                JSONArray aboutMeArray = jsonObject.optJSONArray("about_me");
                if (aboutMeArray != null) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            for (int i = 0; i < aboutMeArray.length(); i++) {
                                addTextView(aboutMeArray.optString(i, ""));
                            }
                        } catch (Exception e) {
                            ToastUtils.showShortToast(requireContext(), "Error: " + e.getMessage());
                        }
                    });
                }

                // Установка имени и возраста
                String name = jsonObject.optString("name", "Без имени");
                String age = jsonObject.optString("age", "??");
                requireActivity().runOnUiThread(() -> name_and_age.setText(name + ", " + age));

                // Установка прогресса профиля
                String fullnessStr = jsonObject.optString("fullness", "0");
                int fullness = 0;
                try {
                    fullness = Integer.parseInt(fullnessStr);
                } catch (NumberFormatException ignored) {}

                setProfileProgress(fullness);

            } else {
                showErrorToast("Ошибка на стороне сервера ERROR: " + status);
            }
        } catch (JSONException e) {
            requireActivity().runOnUiThread(() -> {
                new RequestUtils(this, "log", "POST",
                        "{\"module\": \"FragmentProfile\", \"method\": \"callbackSetData\", \"error\": \"" + e + "\"}",
                        callbackLog).execute();
                EmptyResponse();
            });
        }
    };


    @SuppressLint("SetTextI18n")
    private void setProfileProgress(int progress) {
        requireActivity().runOnUiThread(() -> {
            progressBar.setProgress(progress);
            tvFillPercentage.setText(progress + "%");
        });
    }


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
