package com.example.bettertinder.views;

import com.example.bettertinder.ItemModel;

import java.util.ArrayList;

public interface OnFragmentSwitchedListener {
    void onFragmentSwitched(FragmentType type);

    ArrayList<ItemModel> getLikedItems();

    void setLikedItems(ArrayList<ItemModel> likedItems);

    enum FragmentType {
        HOME,
        RESULT
    }
}
