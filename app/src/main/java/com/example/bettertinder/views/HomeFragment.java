package com.example.bettertinder.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.bettertinder.R;
import com.example.bettertinder.cardstack.CardStackAdapter;
import com.example.bettertinder.cardstack.CardStackLayoutManager;
import com.example.bettertinder.cardstack.input.CardStackTouchHelperCallback;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private OnFragmentSwitchedListener fragmentSwitchedListener;
    private CardStackAdapter adapter;

    private int glideLoadCounter;

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

        CardStackLayoutManager manager = new CardStackLayoutManager();
        manager.setMaxShowCount(3);
        manager.setTransYGap(21);
        manager.setScaleGap(0.05f);
        manager.setAngle(20);
        manager.setAnimationDuratuion(450);
        recyclerView.setLayoutManager(manager);

        generateAdapterData(recyclerView, 10);
    }


    private void generateAdapterData(final RecyclerView recyclerView, final int dataSize) {
        final Bitmap[] data = new Bitmap[dataSize];
        String dataUrl = "https://source.unsplash.com/collection/3336145/600x800";
        glideLoadCounter = 0;

        for (int i = 0; i < dataSize; i++) {
            final int k = i;

            Glide.with(getContext()).asBitmap().load(dataUrl + "?sig=" + k).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    data[k] = resource;
                    glideLoadCounter++;

                    if (glideLoadCounter == dataSize) {
                        adapter = new CardStackAdapter(new ArrayList<Bitmap>(Arrays.asList(data)), fragmentSwitchedListener);
                        recyclerView.setAdapter(adapter);
                        ItemTouchHelper touchHelper = new ItemTouchHelper(new CardStackTouchHelperCallback(adapter));
                        touchHelper.attachToRecyclerView(recyclerView);
                    }
                }


                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
        }
    }

}
