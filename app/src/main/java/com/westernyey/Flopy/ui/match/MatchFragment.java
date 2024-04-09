// MatchFragment.java
package com.westernyey.Flopy.ui.match;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.westernyey.Flopy.R;

import java.util.ArrayList;

public class MatchFragment extends Fragment {
    private ArrayList<String> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        data = new ArrayList<>();
        data.add("данные");
        data.add("match");

        GridView gridView = view.findViewById(R.id.gridView);
        TextView noDataTextView = view.findViewById(R.id.noDataTextView);

        if (data.isEmpty()) {
            gridView.setVisibility(View.GONE);
            noDataTextView.setVisibility(View.VISIBLE);
        } else {
            gridView.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.GONE);

            MyAdapter<String> adapter = new MyAdapter<>(getActivity(), data);
            gridView.setAdapter(adapter);
        }

        return view;
    }
}
