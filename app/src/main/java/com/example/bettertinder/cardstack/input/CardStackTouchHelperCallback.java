package com.example.bettertinder.cardstack.input;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bettertinder.cardstack.CardStackLayoutManager;

public class CardStackTouchHelperCallback extends ItemTouchHelper.Callback {
    private final OnItemSwipedListener onItemSwiped;

    public CardStackTouchHelperCallback(OnItemSwipedListener onItemSwiped) {
        super();
        this.onItemSwiped = onItemSwiped;
    }

    @Override
    public final int getMovementFlags(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0,
                viewHolder.getAdapterPosition() != 0 ? 0 : ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    public final float getThreshold(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.itemView.getWidth() * 0.7f;
    }

    @Override
    public final boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public final void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        if (direction == ItemTouchHelper.LEFT) {
            onItemSwiped.onItemSwipedLeft();
            onItemSwiped.onItemSwiped();
        } else if (direction == ItemTouchHelper.RIGHT) {
            onItemSwiped.onItemSwipedRight();
            onItemSwiped.onItemSwiped();
        }

        viewHolder.itemView.invalidate();
    }

    @Override
    public final float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.5f;
    }

    @Override
    public final long getAnimationDuration(RecyclerView recyclerView, int animationType,
                                           float animateDx, float animateDy) {
        return ((CardStackLayoutManager) recyclerView.getLayoutManager()).getAnimationDuratuion();
    }

    @Override
    public final void onChildDraw(Canvas c, RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                  boolean isCurrentlyActive) {
        float fraction = Math.abs(dX) / getThreshold(viewHolder);
        fraction = Math.min(1, fraction);


        if (viewHolder instanceof OnItemSwipedPrecentageListener) {
            ((OnItemSwipedPrecentageListener) viewHolder).onItemSwipePercentage(Math.signum(dX) * fraction);
        }

        CardStackLayoutManager cardStackLayoutManager =
                (CardStackLayoutManager) recyclerView.getLayoutManager();

        int childCount = recyclerView.getChildCount();

        if (viewHolder.getAdapterPosition() == 0) {
            viewHolder.itemView.setRotation(cardStackLayoutManager.getAngle() * (dX / recyclerView.getMeasuredWidth())); //use fraction?
            viewHolder.itemView.setTranslationX(dX);
        }

        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            int level = childCount - i - 1;

            if (level > 0) {
                float scale = Math.max(0, Math.min(1,
                        1 - cardStackLayoutManager.getScaleGap() * level
                                + fraction * cardStackLayoutManager.getScaleGap()));
                child.setScaleX(scale);

                if (level < cardStackLayoutManager.getMaxShowCount() - 1) {
                    child.setTranslationY(Math.max(0, (cardStackLayoutManager.getTransYGap() * level
                            - fraction * cardStackLayoutManager.getTransYGap())));
                }
            }
        }
    }

    @Override
    public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0);
        viewHolder.itemView.setScaleX(1);
        viewHolder.itemView.setScaleY(1);

        if (viewHolder instanceof OnItemSwipedPrecentageListener) {
            ((OnItemSwipedPrecentageListener) viewHolder).onItemSwipePercentage(0);
        }
    }
}
