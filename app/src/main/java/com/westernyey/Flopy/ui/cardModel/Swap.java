package com.westernyey.Flopy.ui.cardModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_swap, container, false);

        flingAdapterView = rootView.findViewById(R.id.swipe);

        // Инициализация кнопок
        Button dislike = rootView.findViewById(R.id.dislike);
        Button back = rootView.findViewById(R.id.back);
        Button super_like = rootView.findViewById(R.id.super_like);
        Button like = rootView.findViewById(R.id.like);


        like.setOnClickListener(v -> {
            flingAdapterView.getTopCardListener().selectRight();
        });

        dislike.setOnClickListener(v -> {
            flingAdapterView.getTopCardListener().selectLeft();
        });

        back.setOnClickListener(v -> {

        });

        super_like.setOnClickListener(v -> {

        });


        data = new ArrayList<>();

//        List<Integer> boberImages = Arrays.asList(R.drawable.bober, R.drawable.djtape, R.drawable.druc, R.drawable.ezh);
//        List<String> boberAdditionalInfo = Arrays.asList("Информация 1", "Информация 2", "Информация 3"); // Пример дополнительной информации
//        data.add(new CardModel("бобер", "Екатеринбург", "20 км.", "Дружба", boberImages, boberAdditionalInfo));
//
//        List<Integer> metinImages = Arrays.asList(R.drawable.metin, R.drawable.monkey, R.drawable.neupok, R.drawable.oksana);
//        List<String> metinAdditionalInfo = Arrays.asList("Информация 1", "Информация 2"); // Пример дополнительной информации
//        data.add(new CardModel("metin", "Екатеринбург", "20 км.", "Дружба", metinImages, metinAdditionalInfo));


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
                Toast.makeText(getActivity(), "dislike", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(getActivity(), "like", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                // Handle if needed
            }

            @Override
            public void onScroll(float v) {
                // Handle if needed
            }
        });

        return rootView;
    }
}
