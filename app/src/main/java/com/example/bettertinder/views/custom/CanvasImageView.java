package com.example.bettertinder.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CanvasImageView extends View {

    private Bitmap bitmapToDraw;
    private Matrix mShaderMatrix;

    private Paint paint;
    private RectF rectF;

    public CanvasImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmapToDraw = null;
        mShaderMatrix = new Matrix();

        if (attrs != null) {
            int[] set = {android.R.attr.background};
            TypedArray a = context.obtainStyledAttributes(attrs, set);

            rectF = new RectF();
            paint = new Paint();
            paint.setColor(a.getColor(0, Color.TRANSPARENT));

            a.recycle();
        }
    }

    public void setBitmapToDraw(Bitmap bitmapToDraw) {
        this.bitmapToDraw = bitmapToDraw;

        updateBitmapSize();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updateBitmapSize();
        updateRect();
    }

    private void updateRect() {
        if (rectF == null) return;

        float viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        rectF.set(0, 0, viewWidth, viewHeight);

        invalidate();
        requestLayout();
    }

    //scaling center_crop logic, using matrix
    private void updateBitmapSize() {
        if (bitmapToDraw == null) return;

        float viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        float bitmapWidth = bitmapToDraw.getWidth();
        float bitmapHeight = bitmapToDraw.getHeight();

        float scale;
        float dx = 0, dy = 0;

        if (bitmapWidth * viewHeight > viewWidth * bitmapHeight) {
            scale = viewHeight / bitmapHeight;
            dx = (viewWidth - bitmapWidth * scale) * 0.5f;
        } else {
            scale = viewWidth / bitmapWidth;
            dy = (viewHeight - bitmapHeight * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate(Math.round(dx), Math.round(dy));

        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmapToDraw != null)
            canvas.drawBitmap(bitmapToDraw, mShaderMatrix, null);
        else
            canvas.drawRect(rectF, paint);
    }
}
