package com.westernyey.Flopy.ui.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.westernyey.Flopy.R;

public class FiltersMenu extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filters_menu, container, false);
        setCancelable(true); // Разрешить закрытие при нажатии вне области фрагмента
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Задаем высоту BottomSheetDialogFragment (в данном случае 400dp)
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 400);
        // Задать состояние скрытия при свайпе вниз
        getDialog().getWindow().setDimAmount(0.5f); // Затемнение заднего фона
        ((View) getView().getParent()).setBackgroundColor(Color.TRANSPARENT); // Прозрачный фон
        BottomSheetBehavior.from((View) getView().getParent()).setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}
