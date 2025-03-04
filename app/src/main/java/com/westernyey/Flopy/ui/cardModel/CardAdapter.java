package com.westernyey.Flopy.ui.cardModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.westernyey.Flopy.R;

import java.util.List;

public class CardAdapter extends ArrayAdapter<CardModel> {
    public CardAdapter(Context context, int resourceId, List<CardModel> items) {
        super(context, resourceId, items);
    }

    @SuppressLint("SetTextI18n")
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
        LinearLayout linearLayoutPhoto = convertView.findViewById(R.id.linear_layout_photo);

        name_and_age.setText(cardModel.getName() + ", " + cardModel.getAge());
        city.setText(cardModel.getCity());
        distance.setText(cardModel.getDistance());
        target.setText(cardModel.getTarget());

        // Очищаем ImageView перед загрузкой новой фотографии
        Glide.with(getContext()).clear(imageView);
        Glide.with(getContext()).load(cardModel.getCurrentImageUrl()).into(imageView);

        // Добавляем объекты в LinearLayout за каждый URL, который не равен null
        addUrlsToLinearLayout(linearLayoutPhoto, cardModel.getPhotoUrls(), cardModel.getCurrentImageIndex());

        btnNextPhoto.setOnClickListener(v -> {
            cardModel.nextImage();
            Glide.with(getContext()).clear(imageView); // Очищаем ImageView перед загрузкой новой фотографии
            Glide.with(getContext()).load(cardModel.getCurrentImageUrl()).into(imageView);
            updateUrlsInLinearLayout(linearLayoutPhoto, cardModel.getPhotoUrls(), cardModel.getCurrentImageIndex());
        });

        btnPastPhoto.setOnClickListener(v -> {
            cardModel.previousImage();
            Glide.with(getContext()).clear(imageView); // Очищаем ImageView перед загрузкой новой фотографии
            Glide.with(getContext()).load(cardModel.getCurrentImageUrl()).into(imageView);
            updateUrlsInLinearLayout(linearLayoutPhoto, cardModel.getPhotoUrls(), cardModel.getCurrentImageIndex());
        });

        FlexboxLayout flexboxLayout = convertView.findViewById(R.id.flexbox_container);
        addAdditionalInfo(flexboxLayout, cardModel.getAdditionalInfo());

        return convertView;
    }

    private void updateUrlsInLinearLayout(LinearLayout linearLayout, List<String> photoUrls, int currentIndex) {
        // Обновляем только цвет фона для текущего элемента
        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if (i == currentIndex) {
                imageView.setImageResource(R.drawable.slider_small_on_horizontal); // Фиолетовый цвет для текущего изображения
            } else {
                imageView.setImageResource(R.drawable.slider_small_horizontal); // Сбрасываем цвет
            }
        }
    }

    private void addUrlsToLinearLayout(LinearLayout linearLayout, List<String> photoUrls, int currentIndex) {
        // Если это первый раз, очищаем LinearLayout, чтобы добавить элементы
        if (linearLayout.getChildCount() == 0) {
            for (int i = 0; i < photoUrls.size(); i++) {
                String url = photoUrls.get(i);
                if (url != null) {  // Проверяем, что URL не null
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageResource(R.drawable.slider_small_horizontal);  // Устанавливаем нужный drawable

                    // Если это текущий индекс, делаем фиолетовым
                    if (i == currentIndex) {
                        imageView.setImageResource(R.drawable.slider_small_on_horizontal);  // Устанавливаем нужный drawable
                    }

                    // Применяем параметры для ImageView
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 0, 10, 0);  // Можно настроить отступы
                    imageView.setLayoutParams(params);

                    linearLayout.addView(imageView);  // Добавляем ImageView в LinearLayout
                }
            }
        }
    }

    private void addAdditionalInfo(FlexboxLayout flexboxLayout, List<String> infoList) {
        flexboxLayout.removeAllViews();
        for (String info : infoList) {
            TextView textView = new TextView(getContext());
            textView.setText(info);
            textView.setBackgroundResource(R.drawable.item_background_gray);
            textView.setPadding(20, 10, 20, 10);
            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 20, 20);
            textView.setLayoutParams(params);
            flexboxLayout.addView(textView);
        }
    }
}
