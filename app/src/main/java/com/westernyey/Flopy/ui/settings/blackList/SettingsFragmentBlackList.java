package com.westernyey.Flopy.ui.settings.blackList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cripochec.Flopy.ui.utils.FragmentUtils;
import com.westernyey.Flopy.R;
import com.westernyey.Flopy.ui.settings.FragmentSettings;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragmentBlackList extends Fragment {
    private LinearLayout userContainer;
    private List<BlacklistUser> blacklistUsers = new ArrayList<>();

    // Пример JSON строки (затем вы замените запросом к серверу)
    private String jsonString = "[{\"id\":\"1\",\"name\":\"John\",\"age\":30,\"photoResource\":2131165292}," +
            "{\"id\":\"2\",\"name\":\"Alice\",\"age\":25,\"photoResource\":2131165293}," +
            "{\"id\":\"3\",\"name\":\"Bob\",\"age\":28,\"photoResource\":2131165294}]";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment_black_list, container, false);

        Button btnBac = rootView.findViewById(R.id.btn_bac_settings);
        userContainer = rootView.findViewById(R.id.userContainer);

        btnBac.setOnClickListener(v -> {
            Fragment fragment = new FragmentSettings();
            FragmentUtils.replaceFragment(requireActivity().getSupportFragmentManager(), R.id.fr_activity_main, fragment);
        });


        // Загрузка списка пользователей из "чёрного списка"
        loadBlacklistUsers();

        // Добавление пользователей в LinearLayout
        addUsersToLayout();

        return rootView;
    }

    // Временно загружаем данные из строки json
    private void loadBlacklistUsers() {
        // Заглушка для обработки JSON (замените этот блок, когда будет готов запрос)
        blacklistUsers.add(new BlacklistUser("1", "Bober", 30, R.drawable.bober));
        blacklistUsers.add(new BlacklistUser("2", "DjTape", 25, R.drawable.djtape));
        blacklistUsers.add(new BlacklistUser("3", "Ezh", 28, R.drawable.ezh));
    }

    // Метод для добавления пользователей в layout
    private void addUsersToLayout() {
        for (BlacklistUser user : blacklistUsers) {
            // Создаем горизонтальный LinearLayout для каждого пользователя
            LinearLayout userLayout = new LinearLayout(getContext());
            userLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Добавляем фото
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(user.getPhotoResource());
            // Устанавливаем размер изображения 100x100 пикселей
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(100, 100);
            imageView.setLayoutParams(imageParams);

            userLayout.addView(imageView);

            // Создаем текст с именем и возрастом
            TextView textView = new TextView(getContext());
            textView.setText(user.getName() + ", " + user.getAge());
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            textParams.setMargins(16, 0, 0, 0); // Небольшой отступ
            textView.setLayoutParams(textParams);
            userLayout.addView(textView);

            // Добавляем кнопку "Удалить"
            Button deleteButton = new Button(getContext());
            deleteButton.setText("Удалить");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeUserFromBlacklist(user);
                }
            });

            // Добавляем кнопку к пользовательскому layout'у
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            buttonParams.setMargins(0, 0, 16, 0); // Отступ справа
            userLayout.addView(deleteButton, buttonParams);

            // Добавляем горизонтальный layout в основной вертикальный
            userContainer.addView(userLayout);
        }
    }

    // Метод для удаления пользователя из списка
    private void removeUserFromBlacklist(BlacklistUser user) {
        // Удаляем пользователя из списка
        blacklistUsers.remove(user);

        // Очищаем контейнер и заново добавляем обновленный список пользователей
        userContainer.removeAllViews();
        addUsersToLayout();
    }

}
