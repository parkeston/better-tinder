package com.example.bettertinder.cardstack;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.bettertinder.ItemModel;
import com.example.bettertinder.R;
import com.example.bettertinder.cardstack.input.OnItemSwipedListener;
import com.example.bettertinder.cardstack.input.OnItemSwipedPrecentageListener;
import com.example.bettertinder.views.OnFragmentSwitchedListener;
import com.example.bettertinder.views.custom.CanvasImageView;

import java.util.ArrayList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ItemView> implements OnItemSwipedListener {
    private ArrayList<ItemModel> data;
    private ArrayList<ItemModel> likedItems;
    private OnFragmentSwitchedListener fragmentSwitchedListener;

    public CardStackAdapter(ArrayList<ItemModel> data, OnFragmentSwitchedListener fragmentSwitchedListener) {
        this.data = data;
        this.fragmentSwitchedListener = fragmentSwitchedListener;

        likedItems = new ArrayList<>();
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
        data.add(data.get(0));
        data.remove(0);
        notifyItemRemoved(0);
        notifyItemInserted(data.size() - 1);

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

    public static class ItemView extends RecyclerView.ViewHolder implements OnItemSwipedPrecentageListener {
        private CanvasImageView image;
        private TextView title, city;

        private View leftOverlay, rightOverlay;

        public ItemView(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.item_name);
            city = itemView.findViewById(R.id.item_city);

            leftOverlay = itemView.findViewById(R.id.left_overlay);
            rightOverlay = itemView.findViewById(R.id.right_overlay);
        }

        public void SetData(ItemModel model) {
            title.setText(model.getTitle());
            city.setText(model.getCity());


            Glide.with(image).asBitmap().load(model.getImageUrl()).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    image.setBitmapToDraw(resource);

                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
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
