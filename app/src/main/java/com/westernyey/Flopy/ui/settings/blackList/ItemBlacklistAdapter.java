package com.westernyey.Flopy.ui.settings.blackList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.westernyey.Flopy.R;

import java.util.List;

public class ItemBlacklistAdapter extends RecyclerView.Adapter<ItemBlacklistAdapter.BlacklistViewHolder> {

    private final List<BlacklistUser> blacklistUsers;
    private final Context context;
    private final OnUserRemovedListener listener;

    // Интерфейс для обработки удаления пользователя
    public interface OnUserRemovedListener {
        void onUserRemoved(BlacklistUser user);
    }

    public ItemBlacklistAdapter(Context context, List<BlacklistUser> users, OnUserRemovedListener listener) {
        this.context = context;
        this.blacklistUsers = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BlacklistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.settings_fragment_black_list_item, parent, false);
        return new BlacklistViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BlacklistViewHolder holder, int position) {
        BlacklistUser user = blacklistUsers.get(position);
        holder.userName.setText(user.getName());
        holder.userAge.setText(String.valueOf(user.getAge()));


        Glide.with(context)
                .load(user.getPhotoUrl()) // URL фото
//                .placeholder(R.drawable.placeholder) // Плейсхолдер во время загрузки
//                .error(R.drawable.error_placeholder) // Плейсхолдер в случае ошибки
                .into(holder.userPhoto);

        // Обработка нажатия на кнопку удаления
        holder.btnDeleteUser.setOnClickListener(v -> {
            listener.onUserRemoved(user);  // Передаём управление фрагменту
        });
    }

    @Override
    public int getItemCount() {
        return blacklistUsers.size();
    }

    public static class BlacklistViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView userAge;
        ImageView userPhoto;
        Button btnDeleteUser;

        public BlacklistViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userAge = itemView.findViewById(R.id.userAge);
            userPhoto = itemView.findViewById(R.id.userPhoto);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
        }
    }



}
