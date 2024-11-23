package com.westernyey.Flopy.ui.slider;

import static com.cripochec.Flopy.ui.utils.DataUtils.saveIncognito;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.cardModel.Swap;
import com.westernyey.Flopy.ui.settings.FragmentSettings;

import org.json.JSONObject;

public class FragmentSlider extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_slider, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Добавляем SwapFragment в контейнер
        fragmentTransaction.add(R.id.swap_container, new Swap());
        fragmentTransaction.commit();

        // Инициализация кнопок и бокового меню
        Button btnOpenMenu = rootView.findViewById(R.id.btn_open_menu);
        Button btnOpenSet = rootView.findViewById(R.id.btn_open_settings);
        Button btnOpenFil = rootView.findViewById(R.id.btn_open_filters);

        btnOpenMenu.setOnClickListener(v -> {
            DrawerLayout drawerLayout = requireActivity().findViewById(R.id.drawer_layout);
            if (drawerLayout != null) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        btnOpenFil.setOnClickListener(v -> {
            Fragment fragment = new FragmentSettings();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        btnOpenSet.setOnClickListener(v -> {
            Fragment fragment = new FragmentSettings();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

//        new RequestUtils(this, "get_incognito", "POST", "{\"id_person\": \"" + getUserId(requireContext()) + "\"}", callbackGetIncognito).execute();

        return rootView;
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

    // Обработка ответа на сохранение статуса инкогнито пользователя
    RequestUtils.Callback callbackGetIncognito = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){
                saveIncognito(requireContext(), jsonObject.getInt("incognito_status"));
            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSlider\", \"method\": \"callbackGetIncognito\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
