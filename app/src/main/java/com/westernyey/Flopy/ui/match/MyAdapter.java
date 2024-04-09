// MyAdapter.java
package com.westernyey.Flopy.ui.match;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.westernyey.Flopy.R;

import java.util.ArrayList;

public class MyAdapter<T> extends ArrayAdapter<T> {
    private Context context;

    public MyAdapter(Context context, ArrayList<T> data) {
        super(context, 0, data);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_for_matches, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        T item = getItem(position);
        viewHolder.textView.setText(String.valueOf(item));

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }
}
