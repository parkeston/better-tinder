package com.example.bettertinder.views.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CanvasHomeButton extends View {

    private static final int BACKGROUND_COLOR = Color.rgb(255, 204, 0);
    private static final int HOME_COLOR = Color.rgb(50, 50, 50);

    private static final float HOME_SIZE_PERCENT = 0.5f;
    private static final float DOOR_WIDTH_PERCENT = 0.35f;
    private static final float DOOR_HEIGHT_PERCENT = 0.6f;

    private RectF backDrawBounds;
    private RectF homeBaseDrawBounds;
    private RectF doorDrawBounds;

    private Path roofPath;

    private Paint basePaint;
    private Paint homePaint;

    public CanvasHomeButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        backDrawBounds = new RectF();
        homeBaseDrawBounds = new RectF();
        doorDrawBounds = new RectF();

        roofPath = new Path();

        basePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        basePaint.setColor(BACKGROUND_COLOR);
        basePaint.setStyle(Paint.Style.FILL);

        homePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        homePaint.setColor(HOME_COLOR);
        homePaint.setStyle(Paint.Style.FILL);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        UpdateDrawBounds(HOME_SIZE_PERCENT, DOOR_WIDTH_PERCENT, DOOR_HEIGHT_PERCENT);

        invalidate();
        requestLayout();
    }

    private void UpdateDrawBounds(float homeWidthPercent, float doorWidthPercent, float doorHeightPercent) {
        float contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float contentHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        float left = getPaddingLeft();
        float top = getPaddingTop();

        backDrawBounds.set(left, top, left + contentWidth, top + contentHeight);

        float width = contentHeight * homeWidthPercent;
        float doorWidth = width * doorWidthPercent;
        float doorHeight = width * doorHeightPercent;

        left = left + contentWidth / 2 - width / 2;
        top = top + contentHeight / 2 - width / 2 + width / 4;

        homeBaseDrawBounds.set(left, top, left + width, top + width);

        float topOffset = (top + width) - (getPaddingTop() + contentHeight);
        topOffset = -topOffset;
        PointF a = new PointF(left - width / 4, top + width / 4);
        PointF b = new PointF(left + width / 2, getPaddingTop() + topOffset);
        PointF c = new PointF(left + width + width / 4, top + width / 4);

        roofPath.moveTo(b.x, b.y);
        roofPath.lineTo(c.x, c.y);
        roofPath.lineTo(a.x, a.y);
        roofPath.close();

        left = left + (width / 2 - doorWidth / 2);
        top = top + width - doorHeight;

        doorDrawBounds.set(left, top, left + doorWidth, top + doorHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRoundRect(backDrawBounds, 50, 50, basePaint);
        canvas.drawRect(homeBaseDrawBounds, homePaint);
        canvas.drawPath(roofPath, homePaint);
        canvas.drawRect(doorDrawBounds, basePaint);

        roofPath.reset();
    }
}
