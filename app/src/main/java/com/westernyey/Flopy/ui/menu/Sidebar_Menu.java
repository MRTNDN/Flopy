package com.westernyey.Flopy.ui.menu;

import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.cripochec.Flopy.ui.utils.RequestUtils;
import com.cripochec.Flopy.ui.utils.ToastUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.likedYou.FragmentLiked;
import com.westernyey.Flopy.ui.profile.FragmentProfile;
import com.westernyey.Flopy.ui.slider.FragmentSlider;

import org.json.JSONException;
import org.json.JSONObject;

public class Sidebar_Menu extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sidebar_menu, container, false);

        Button btn_profile = rootView.findViewById(R.id.b1);
        Button btn_like = rootView.findViewById(R.id.b2);
        Button btn_massage = rootView.findViewById(R.id.b3);
        Button btn_main = rootView.findViewById(R.id.b4);

        btn_profile.setOnClickListener(v -> {
            // Обработка нажатия на кнопку btn_profile
            Fragment fragment = new FragmentProfile();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);

            // Закрываем боковое меню
            DrawerLayout drawer = requireActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        });

        btn_like.setOnClickListener(v -> {
            // Обработка нажатия на кнопку btn_like
            Fragment fragment = new FragmentLiked();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);

            // Закрываем боковое меню
            DrawerLayout drawer = requireActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        });

        btn_massage.setOnClickListener(v -> {
            // Обработка нажатия на кнопку btn_massage
            //            ToastUtils.showShortToast(requireContext(), "В разработке");


//ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ
            try {
                JSONObject jsonRequestBody = new JSONObject();
                jsonRequestBody.put("id_person", getUserId(requireContext()));
                jsonRequestBody.put("id_block", 1);

                new RequestUtils(this, "add_black_list", "POST", jsonRequestBody.toString() , callbackAddBlackList).execute();
            } catch (JSONException e) {
                new RequestUtils(this, "log", "POST", "{\"module\": \"Sidebar_Menu\", \"method\": \"add_black_list.request\", \"error\": \"" + e + "\"}", callbackLog).execute();
            }
//ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ_ТЕСТ
        });

        btn_main.setOnClickListener(v -> {
            // Обработка нажатия на кнопку btn_main
            Fragment fragment = new FragmentSlider();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);

            // Закрываем боковое меню
            DrawerLayout drawer = requireActivity().findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
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
            showErrorToast("Ошибка логирования на клиенте.");
        }
    };


    RequestUtils.Callback callbackAddBlackList = (fragment, result) -> {
        // Обработка ответа
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0){
                showErrorToast("Удачно");
            } else {
                showErrorToast("Ошибка на сервере, status: "+jsonObject.getInt("status"));
            }
        } catch (Exception e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"Sidebar_Menu\", \"method\": \"callbackAddBlackList\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
