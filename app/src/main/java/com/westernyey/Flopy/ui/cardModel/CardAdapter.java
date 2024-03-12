package com.westernyey.Flopy.ui.cardModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.westernyey.Flopy.R;

import java.util.List;

public class CardAdapter extends ArrayAdapter<CardModel> {
    public CardAdapter(Context context, int resourceId, List<CardModel> items) {
        super(context, resourceId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardModel cardModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_item_for_swap, parent, false);
        }

        TextView dataTextView = convertView.findViewById(R.id.data);
        ImageView imageView = convertView.findViewById(R.id.image);

        dataTextView.setText(cardModel.getText());
        imageView.setImageResource(cardModel.getImageResourceId());

        return convertView;
    }
}
