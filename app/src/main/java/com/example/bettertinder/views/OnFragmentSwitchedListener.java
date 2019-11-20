package com.example.bettertinder.views;

import android.graphics.Bitmap;

import java.util.ArrayList;

public interface OnFragmentSwitchedListener {
    void onFragmentSwitched(FragmentType type);

    ArrayList<Bitmap> getLikedItems();

    void setLikedItems(ArrayList<Bitmap> likedItems);

    enum FragmentType {
        HOME,
        RESULT
    }
}
