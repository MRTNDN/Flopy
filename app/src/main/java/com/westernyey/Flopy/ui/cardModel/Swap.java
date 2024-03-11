package com.westernyey.Flopy.ui.cardModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.westernyey.Flopy.R;

import java.util.ArrayList;

public class Swap extends Fragment {
    private ArrayList<CardModel> mData;
    private View view;

    // Пустой конструктор (необязательно)
    public Swap() {
        // Пустой конструктор
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Надуваем макет фрагмента
        View rootView = inflater.inflate(R.layout.fragment_swap, container, false);

        // Инициализация данных
        mData = new ArrayList<>();

        // Настройка RecyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CardAdapter adapter = new CardAdapter(getActivity(), mData); // Используем getActivity() для получения контекста
        recyclerView.setAdapter(adapter);


        return rootView;
    }

    public void setLayoutParams(FrameLayout.LayoutParams layoutParams) {
    }
}
