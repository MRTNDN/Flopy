package com.westernyey.Flopy.ui.cardModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.flexbox.FlexboxLayout;
import com.westernyey.Flopy.R;

import java.util.List;

public class CardAdapter extends ArrayAdapter<CardModel> {
    public CardAdapter(Context context, int resourceId, List<CardModel> items) {
        super(context, resourceId, items);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_item_for_swap, parent, false);
        }

        CardModel cardModel = getItem(position);
        assert cardModel != null;

        TextView name_and_age = convertView.findViewById(R.id.data);
        TextView city = convertView.findViewById(R.id.city);
        TextView distance = convertView.findViewById(R.id.distance);
        TextView target = convertView.findViewById(R.id.target);
        ImageView imageView = convertView.findViewById(R.id.image);
        Button btnNextPhoto = convertView.findViewById(R.id.btn_next_photo);
        Button btnPastPhoto = convertView.findViewById(R.id.btn_past_photo);


        name_and_age.setText(cardModel.getNameAndAge());
        city.setText(cardModel.getCity());
        distance.setText(cardModel.getDistance());
        target.setText(cardModel.getTarget());
        imageView.setImageResource(cardModel.getCurrentImageResourceId());

        btnNextPhoto.setOnClickListener(v -> {
            cardModel.nextImage();
            imageView.setImageResource(cardModel.getCurrentImageResourceId());
        });

        btnPastPhoto.setOnClickListener(v -> {
            cardModel.previousImage();
            imageView.setImageResource(cardModel.getCurrentImageResourceId());
        });

        FlexboxLayout flexboxLayout = convertView.findViewById(R.id.flexbox_container);
        addAdditionalInfo(flexboxLayout, cardModel.getAdditionalInfo()); // Предполагается, что в CardModel есть этот метод

        return convertView;
    }

    private void addAdditionalInfo(FlexboxLayout flexboxLayout, List<String> infoList) {
        flexboxLayout.removeAllViews(); // Очищаем предыдущие элементы
        for (String info : infoList) {
            TextView textView = new TextView(getContext());
            textView.setText(info);
            textView.setBackgroundResource(R.drawable.item_background_gray);
            textView.setPadding(20, 10, 20, 10); // отступы
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 20, 20); // отступы между элементами
            textView.setLayoutParams(params);
            flexboxLayout.addView(textView);
        }
    }





}
