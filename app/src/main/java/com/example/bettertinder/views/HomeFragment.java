package com.example.bettertinder.views;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bettertinder.ItemModel;
import com.example.bettertinder.R;
import com.example.bettertinder.cardstack.CardStackAdapter;
import com.example.bettertinder.cardstack.CardStackLayoutManager;
import com.example.bettertinder.cardstack.input.CardStackTouchHelperCallback;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private OnFragmentSwitchedListener fragmentSwitchedListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        fragmentSwitchedListener = ((OnFragmentSwitchedListener) context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        CardStackAdapter adapter = new CardStackAdapter(createItems(), fragmentSwitchedListener);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new CardStackTouchHelperCallback(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

        CardStackLayoutManager manager = new CardStackLayoutManager();
        manager.setMaxShowCount(3);
        manager.setTransYGap(21);
        manager.setScaleGap(0.05f);
        manager.setAngle(15);
        manager.setAnimationDuratuion(450);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);
    }

    private ArrayList<ItemModel> createItems() {
        ArrayList<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel("0.Yasaka Shrine", "Kyoto", "https://source.unsplash.com/Xq1ntWruZQI/600x800"));
        items.add(new ItemModel("1.Fushimi Inari Shrine", "Kyoto", "https://source.unsplash.com/NYyCqdBOKwc/600x800"));
        items.add(new ItemModel("2.Bamboo Forest", "Kyoto", "https://source.unsplash.com/buF62ewDLcQ/600x800"));
        items.add(new ItemModel("3.Brooklyn Bridge", "New York", "https://source.unsplash.com/THozNzxEP3g/600x800"));
        items.add(new ItemModel("4.Empire State Building", "New York", "https://source.unsplash.com/USrZRcRS2Lw/600x800"));
        items.add(new ItemModel("5.The statue of Liberty", "New York", "https://source.unsplash.com/PeFk7fzxTdk/600x800"));
        items.add(new ItemModel("6.Louvre Museum", "Paris", "https://source.unsplash.com/LrMWHKqilUw/600x800"));
        items.add(new ItemModel("7.Eiffel Tower", "Paris", "https://source.unsplash.com/HN-5Z6AmxrM/600x800"));
        items.add(new ItemModel("8.Big Ben", "London", "https://source.unsplash.com/CdVAUADdqEc/600x800"));
        items.add(new ItemModel("9.Great Wall of China", "China", "https://source.unsplash.com/AWh9C-QjhE4/600x800"));

        return items;
    }

}
