package com.westernyey.Flopy.ui.likedYou;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.westernyey.Flopy.R;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<String> names;
    private List<Integer> images;
    private OnDataChangeListener onDataChangeListener;

    public PersonAdapter(List<String> names, List<Integer> images) {
        this.names = names;
        this.images = images;
    }

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.onDataChangeListener = listener;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liked_person_item, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.imageView.setImageResource(images.get(position));
        holder.nameText.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    // Метод для добавления нового элемента
    public void addPerson(int imageRes, String name) {
        names.add(name);
        images.add(imageRes);
        notifyItemInserted(names.size() - 1);
        if (onDataChangeListener != null) {
            onDataChangeListener.onDataChanged(getItemCount());
        }
    }

    // Метод для удаления элемента
    public void removePerson(int position) {
        if (position >= 0 && position < names.size()) {
            names.remove(position);
            images.remove(position);
            notifyItemRemoved(position);
            if (onDataChangeListener != null) {
                onDataChangeListener.onDataChanged(getItemCount());
            }
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameText;
        Button btnLike, btnDislike;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.person_image);
            nameText = itemView.findViewById(R.id.person_name);
            btnLike = itemView.findViewById(R.id.btn_like);
            btnDislike = itemView.findViewById(R.id.btn_dislike);
        }
    }

    public interface OnDataChangeListener {
        void onDataChanged(int itemCount);
    }
}