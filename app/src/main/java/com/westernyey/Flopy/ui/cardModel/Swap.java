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


        dislike.setOnClickListener(v -> {
            flingAdapterView.getTopCardListener().selectLeft();
        });

        back.setOnClickListener(v -> {

        });

        super_like.setOnClickListener(v -> {

        });

        like.setOnClickListener(v -> {
            flingAdapterView.getTopCardListener().selectRight();
        });


        data = new ArrayList<>();

        data.add(new CardModel("бобер", "Екатеринбург", "20 км." , "Дружба", R.drawable.bober));
        data.add(new CardModel("dj tape", "Екатеринбург", "20 км." , "Дружба", R.drawable.djtape));
        data.add(new CardModel("druc", "Екатеринбург", "20 км." , "Дружба", R.drawable.druc));
        data.add(new CardModel("ezh", "Екатеринбург", "20 км." , "Дружба", R.drawable.ezh));
        data.add(new CardModel("metin", "Екатеринбург", "20 км." , "Дружба", R.drawable.metin));
        data.add(new CardModel("макака", "Екатеринбург", "20 км." , "Дружба", R.drawable.monkey));
        data.add(new CardModel("Неупокоева", "Екатеринбург", "20 км." , "Дружба", R.drawable.neupok));
        data.add(new CardModel("Оксана", "Екатеринбург", "20 км." , "Дружба", R.drawable.oksana));

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
//                Toast.makeText(getActivity(), "Empty", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScroll(float v) {
//                Toast.makeText(getActivity(), "Scroll", Toast.LENGTH_SHORT).show();
            }
        });

//        flingAdapterView.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClicked(int i, Object o) {
////                Toast.makeText(getActivity(), "data is " + data.get(i).getText(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return rootView;
    }
}
