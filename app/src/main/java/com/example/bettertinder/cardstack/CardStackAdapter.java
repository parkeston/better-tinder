package com.example.bettertinder.cardstack;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bettertinder.R;
import com.example.bettertinder.cardstack.input.OnItemSwipedListener;
import com.example.bettertinder.cardstack.input.OnItemSwipedPrecentageListener;
import com.example.bettertinder.views.OnFragmentSwitchedListener;
import com.example.bettertinder.views.custom.CanvasImageView;

import java.util.ArrayList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ItemView> implements OnItemSwipedListener {
    private ArrayList<Bitmap> data;
    private ArrayList<Bitmap> likedItems;
    private OnFragmentSwitchedListener fragmentSwitchedListener;


    public CardStackAdapter(ArrayList<Bitmap> data, OnFragmentSwitchedListener fragmentSwitchedListener) {
        this.data = data;
        likedItems = new ArrayList<>();
        this.fragmentSwitchedListener = fragmentSwitchedListener;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spot, parent, false);
        return new ItemView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, int position) {
        holder.SetData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onItemSwiped() {
        data.remove(0);
        notifyItemRemoved(0);

        if (data.size() == 0) {
            fragmentSwitchedListener.setLikedItems(likedItems);
            fragmentSwitchedListener.onFragmentSwitched(OnFragmentSwitchedListener.FragmentType.RESULT);
        }
    }

    @Override
    public void onItemSwipedLeft() {
        Log.i("swipe", "left");
    }

    @Override
    public void onItemSwipedRight() {
        likedItems.add(data.get(0));

    }

    public class ItemView extends RecyclerView.ViewHolder implements OnItemSwipedPrecentageListener {
        private CanvasImageView image;

        private View leftOverlay, rightOverlay;

        public ItemView(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.item_image);

            leftOverlay = itemView.findViewById(R.id.left_overlay);
            rightOverlay = itemView.findViewById(R.id.right_overlay);
        }

        public void SetData(Bitmap bitmap) {
            image.setBitmapToDraw(bitmap);
        }

        @Override
        public void onItemSwipePercentage(float percentage) {
            if (percentage < 0)
                leftOverlay.setAlpha(Math.abs(percentage));
            else if (percentage == 0) {
                rightOverlay.setAlpha(0);
                leftOverlay.setAlpha(0);
            } else
                rightOverlay.setAlpha(percentage);
        }
    }
}
