package com.example.bettertinder.cardstack;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class CardStackLayoutManager extends RecyclerView.LayoutManager {
    private int maxShowCount = 3;
    private float scaleGap = 0.1f;
    private int transYGap = 0;
    private int angle = 10;
    private long animationDuratuion = 500;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
    }


    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        final int parentTop = getPaddingTop();
        final int parentLeft = getPaddingLeft();
        final int parentRight = getWidth() - getPaddingLeft();
        final int parentBottom = getHeight() - getPaddingBottom();

        int itemCount = getItemCount();
        if (itemCount < 1) {
            return;
        }

        int startPosition = Math.min(maxShowCount, itemCount) - 1;
        startPosition = startPosition > 0 ? startPosition : 0;

        for (int position = startPosition; position >= 0; position--) {
            View view = recycler.getViewForPosition(position);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            layoutDecoratedWithMargins(view, parentLeft, parentTop, parentRight, parentBottom);


            if (position > 0) {
                view.setScaleX(1 - scaleGap * position);
                if (position < maxShowCount - 1) {
                    view.setTranslationY((transYGap * position));
                } else {
                    view.setTranslationY((transYGap * (position - 1)));
                }
            }
        }
    }


    public int getMaxShowCount() {
        return maxShowCount;
    }

    public void setMaxShowCount(int maxShowCount) {
        this.maxShowCount = maxShowCount + 1;
    }

    public float getScaleGap() {
        return scaleGap;
    }

    public void setScaleGap(float scaleGap) {
        this.scaleGap = scaleGap;
    }

    public int getTransYGap() {
        return transYGap;
    }

    public void setTransYGap(int transYGap) {
        this.transYGap = transYGap;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public long getAnimationDuratuion() {
        return animationDuratuion;
    }

    public void setAnimationDuratuion(long animationDuratuion) {
        this.animationDuratuion = animationDuratuion;
    }
}
