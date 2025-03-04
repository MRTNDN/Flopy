package com.westernyey.Flopy.ui.likedYou;

import static com.cripochec.Flopy.ui.utils.DataUtils.saveLastFragmentPosition;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.settings.FragmentSettings;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class FragmentLiked extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liked_you, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view2);
        ImageView sadImage = rootView.findViewById(R.id.imageSadEmoji);
        TextView sadText = rootView.findViewById(R.id.textView9);

        // Инициализация кнопок и бокового меню
        Button btnOpenMenu = rootView.findViewById(R.id.btn_open_menu);
        Button btnOpenSet = rootView.findViewById(R.id.btn_open_settings);

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
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentLiked\", \"method\": \"btnOpenMenu.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }

        });

        btnOpenSet.setOnClickListener(v -> {
            try {
                saveLastFragmentPosition(requireContext(), "like");
                Fragment fragment = new FragmentSettings();
                FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
            } catch (Exception e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"FragmentLiked\", \"method\": \"btnOpenSet.setOnClickListener\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }
        });

        // Тестовые данные
        List<String> names = Arrays.asList("Мирослав, 19", "Василий, 22", "Добрыня, 20", "Влад, 23", "Гена, 19");
        List<Integer> images = Arrays.asList(R.drawable.bober, R.drawable.djtape, R.drawable.druc, R.drawable.ezh, R.drawable.monkey);

//        List<String> names = Arrays.asList();
//        List<Integer> images = Arrays.asList();

        // Настройка RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 колонки
        PersonAdapter adapter = new PersonAdapter(names, images);
        recyclerView.setAdapter(adapter);

        // Первоначальная проверка данных
        if (adapter.getItemCount() > 0) {
            sadImage.setVisibility(View.GONE);
            sadText.setVisibility(View.GONE);
        } else {
            sadImage.setVisibility(View.VISIBLE);
            sadText.setVisibility(View.VISIBLE);
        }

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