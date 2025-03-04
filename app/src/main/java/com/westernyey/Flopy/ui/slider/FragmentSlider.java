package com.westernyey.Flopy.ui.slider;

import android.app.Activity;
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
import com.westernyey.Flopy.ui.filter.FilterConfirmationDialog;
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
        fragmentTransaction.commitAllowingStateLoss(); // Избегаем проблем с состоянием

        // Инициализация кнопок и бокового меню
        Button btnOpenMenu = rootView.findViewById(R.id.btn_open_menu);
        Button btnOpenSet = rootView.findViewById(R.id.btn_open_settings);
        Button btnOpenFil = rootView.findViewById(R.id.btn_open_filters);


        btnOpenMenu.setOnClickListener(v -> {
            try {
                Activity activity = getActivity();
                if (activity != null) {
                    DrawerLayout drawerLayout = activity.findViewById(R.id.drawer_layout);
                    if (drawerLayout != null) {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                }
            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSlider\", \"method\": \"btnOpenMenu.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });


        btnOpenFil.setOnClickListener(v -> {
            try {
                FilterConfirmationDialog bottomSheet = new FilterConfirmationDialog();
                bottomSheet.setFilterDialogListener(() -> {
                    Swap swapFragment = (Swap) getChildFragmentManager().findFragmentById(R.id.swap_container);
                    if (swapFragment != null) {
                        swapFragment.updateCards(); // Вызываем обновление карт
                    }
                });
                bottomSheet.show(getParentFragmentManager(), "FilterConfirmationDialog");
            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSlider\", \"method\": \"btnOpenFil.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }
        });

//        btnOpenFil.setOnClickListener(v -> {
//            try {
//                FilterConfirmationDialog bottomSheet = new FilterConfirmationDialog();
//                bottomSheet.show(getParentFragmentManager(), "FilterConfirmationDialog");
//            } catch (Exception e) {
//                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSlider\", \"method\": \"btnOpenFil.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
//            }
//
//        });

        btnOpenSet.setOnClickListener(v -> {
            try {
                Fragment fragment = new FragmentSettings();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentSlider\", \"method\": \"btnOpenSet.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }
        });

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
            e.printStackTrace(); // Логируем ошибку
            showErrorToast("Ошибка логирования на клиенте.");
        }
    };

    // Обработка ошибки запроса
    public void EmptyResponse() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> ToastUtils.showShortToast(requireContext(), "Ошибка callback."));
        }
    }

    // Метод для показа сообщения об ошибке
    private void showErrorToast(String message) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> ToastUtils.showShortToast(requireContext(), message));
        }
    }
}
