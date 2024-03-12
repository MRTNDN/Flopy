package com.westernyey.Flopy.ui.cardModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.westernyey.Flopy.R;

import java.util.ArrayList;
import java.util.List;

public class Swap extends Fragment {
    private ArrayAdapter<String> arrayAdapter;
    private List<String> data;
    private SwipeFlingAdapterView flingAdapterView;

    // Пустой конструктор (необязательно)
    public Swap() {
        // Пустой конструктор
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Надуваем макет фрагмента
        View rootView = inflater.inflate(R.layout.fragment_swap, container, false);

        flingAdapterView = rootView.findViewById(R.id.swipe);
        // Инициализация кнопок "like" и "dislike"
        Button like = rootView.findViewById(R.id.like);
        Button dislike = rootView.findViewById(R.id.dislike);

        // Установка слушателей для кнопок "like" и "dislike"
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingAdapterView.getTopCardListener().selectRight();
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingAdapterView.getTopCardListener().selectLeft();
            }
        });

        data = new ArrayList<>();
        data.add("бобер");
        data.add("еж");
        data.add("мурлок");
        data.add("обезъянка");
        data.add("дрюс");
        data.add("Неупокоева");
        data.add("DJ tape");
        data.add("Оксана");
        data.add("Метин");

        arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.card_item_for_swap, R.id.data, data);

        flingAdapterView.setAdapter(arrayAdapter);

        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                data.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(getActivity(), "dislike", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(getActivity(), "like", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });

        flingAdapterView.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int i, Object o) {
                Toast.makeText(getActivity(), "data is " + data.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public void setLayoutParams(FrameLayout.LayoutParams layoutParams) {
    }
}
