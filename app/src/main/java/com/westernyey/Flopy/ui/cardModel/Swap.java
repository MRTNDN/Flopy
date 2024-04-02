package com.westernyey.Flopy.ui.cardModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private CardAdapter cardAdapter;
    private List<CardModel> data;
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

        // Применяем фон с закругленными углами
        rootView.setBackgroundResource(R.drawable.rounded_edges);

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

        data.add(new CardModel("бобер", R.drawable.bober));
        data.add(new CardModel("dj tape", R.drawable.djtape));
        data.add(new CardModel("druc", R.drawable.druc));
        data.add(new CardModel("ezh", R.drawable.ezh));
        data.add(new CardModel("metin", R.drawable.metin));
        data.add(new CardModel("макака", R.drawable.monkey));
        data.add(new CardModel("Неупокоева", R.drawable.neupok));
        data.add(new CardModel("Оксана", R.drawable.oksana));

        cardAdapter = new CardAdapter(getActivity(), R.layout.card_item_for_swap, data);

        flingAdapterView.setAdapter(cardAdapter);

        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                data.remove(0);
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
              //  Toast.makeText(getActivity(), "dislike", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
               // Toast.makeText(getActivity(), "like", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(getActivity(), "data is " + data.get(i).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public void setLayoutParams(FrameLayout.LayoutParams layoutParams) {
    }
}
