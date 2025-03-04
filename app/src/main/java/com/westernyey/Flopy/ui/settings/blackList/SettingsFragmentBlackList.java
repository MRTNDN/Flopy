package com.westernyey.Flopy.ui.settings.blackList;

import static com.cripochec.Flopy.ui.utils.DataUtils.getUserId;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SettingsFragmentBlackList extends Fragment {
    private final List<BlacklistUser> blacklistUsers = new ArrayList<>();
    private ItemBlacklistAdapter adapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment_black_list, container, false);

        Button btnBac = rootView.findViewById(R.id.btn_bac_settings);
        btnBac.setOnClickListener(v -> {
            Fragment fragment = new FragmentSettings();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ItemBlacklistAdapter(getContext(), blacklistUsers, this::removeUserFromBlacklist);
        recyclerView.setAdapter(adapter);

        try {
            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("id_person", getUserId(requireContext()));

            new RequestUtils(this, "get_blocked_users", "POST", jsonRequestBody.toString() , callbackGetBlockedUsers).execute();
        } catch (JSONException e) {
            e.printStackTrace();
            new RequestUtils(this, "log", "POST", "{\"module\": \"SettingsFragmentBlackList\", \"method\": \"get_blocked_users.request\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }
        return rootView;
    }

    private void loadBlacklistUsers(JSONArray blockList) {
        try {
            blacklistUsers.clear(); // Очищаем список перед загрузкой новых данных

            for (int i = 0; i < blockList.length(); i++) {
                JSONObject userJson = blockList.getJSONObject(i);
                String id = userJson.getString("id");
                String name = userJson.getString("name");
                int age = userJson.getInt("age");
                String photoUrl = userJson.getString("photo_url");

                // Создание объекта пользователя
                blacklistUsers.add(new BlacklistUser(id, name, age, photoUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            new RequestUtils(this, "log", "POST", "{\"module\": \"SettingsFragmentBlackList\", \"method\": \"loadBlacklistUsers\", \"error\": \"" + e + "\"}", callbackLog).execute();
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private void removeUserFromBlacklist(BlacklistUser user) {
        // Отправляем запрос на сервер
        try {
            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("id_person", getUserId(requireContext()));
            jsonRequestBody.put("id_block", user.getId());

            // Колбэк для обработки ответа
            RequestUtils.Callback callbackRemoveBlackList = (fragment, result) -> {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") == 0) {
                        requireActivity().runOnUiThread(() -> {
                            blacklistUsers.remove(user);  // Удаляем локально
                            adapter.notifyDataSetChanged();  // Обновляем UI
                        });
                    } else {
                        showErrorToast("Ошибка на сервере, status: " + jsonObject.getInt("status"));
                    }
                } catch (JSONException e) {
                    new RequestUtils(this, "log", "POST", "{\"module\": \"SettingsFragmentBlackList\", \"method\": \"callbackRemoveBlackList\", \"error\": \"" + e + "\"}", callbackLog).execute();
                    EmptyResponse();
                }
            };

            // Отправляем запрос на удаление
            new RequestUtils(this, "remove_black_list", "POST", jsonRequestBody.toString(), callbackRemoveBlackList).execute();

        } catch (JSONException e) {
            e.printStackTrace();
            new RequestUtils(this, "log", "POST", "{\"module\": \"SettingsFragmentBlackList\", \"method\": \"remove_black_list.request\", \"error\": \"" + e + "\"}", callbackLog).execute();
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


    @SuppressLint("NotifyDataSetChanged")
    RequestUtils.Callback callbackGetBlockedUsers = (fragment, result) -> {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getInt("status") == 0) {
                JSONArray blockList = jsonObject.getJSONArray("block_list");
                requireActivity().runOnUiThread(() -> {
                    loadBlacklistUsers(blockList);
                    adapter.notifyDataSetChanged();

                    // Проверка на пустоту списка
                    if (blacklistUsers.isEmpty()) {
                        showErrorToast("Список пуст");
                    }
                });
            } else {
                showErrorToast("Ошибка на сервере, status: " + jsonObject.getInt("status"));
            }
        } catch (JSONException e) {
            new RequestUtils(this, "log", "POST", "{\"module\": \"SettingsFragmentBlackList\", \"method\": \"callbackGetBlockedUsers\", \"error\": \"" + e + "\"}", callbackLog).execute();
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
