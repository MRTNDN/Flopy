package com.westernyey.Flopy.ui.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.westernyey.Flopy.R;

import java.util.ArrayList;

public class Matches extends Fragment {
    private ArrayList<String> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        data = new ArrayList<>();
        data.add("данные");
        data.add("match");

        GridView gridView = view.findViewById(R.id.gridView);
        MyAdapter adapter = new MyAdapter(getActivity(), data);
        gridView.setAdapter(adapter);


        return view;
    }
}
