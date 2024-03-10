package com.westernyey.Flopy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.westernyey.Flopy.R;

public class Swap extends Fragment {

    // Пустой конструктор (необязательно)
    public Swap() {
        // Пустой конструктор
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Надуваем макет фрагмента
        View rootView = inflater.inflate(R.layout.fragment_swap, container, false);

        // Здесь вы можете добавить дополнительные настройки для вашего фрагмента, если это необходимо

        return rootView;
    }

    public void setLayoutParams(FrameLayout.LayoutParams layoutParams) {
    }
}
