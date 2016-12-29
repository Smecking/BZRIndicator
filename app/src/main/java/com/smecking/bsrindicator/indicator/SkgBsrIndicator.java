package com.smecking.bsrindicator.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import com.smecking.bsrindicator.R;
import com.smecking.bsrindicator.utils.DensityUtils;



/**
 * Created by Smecking on 2016/12/26.
 */

public class SkgBsrIndicator extends View implements ViewPager.OnPageChangeListener {

    //Slide
    private int frameXCoordinate;
    private int frameColor;
    private int frameColorReverse;

    private int selectedPosition;
    private int selectingPosition;
    private int lastSelectedPosition;

    private int radiusPx = DensityUtils.dpToPx(6);
    private int paddingPx = DensityUtils.dpToPx(8);
    private int unselectedColor = Color.parseColor("#33ffffff");
    private int selectedColor = Color.parseColor("#ffffff");


    private Paint paint = new Paint();
    private RectF rect = new RectF();
    private int count;

    public SkgBsrIndicator(Context context) {
        super(context);
        init(context, null);
    }


    public SkgBsrIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SkgBsrIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        initAttributes(context,attrs);
        initFrameValues();
        initAnimation();

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    private void initAnimation() {

    }

    private void initFrameValues() {
        //color
        frameColor = selectedColor;
        frameColorReverse = unselectedColor;
        //slide
        int xCoordinate = getXCoordinate(selectedPosition);
        frameXCoordinate = xCoordinate;

    }

    private int getXCoordinate(int position) {
        int actualViewWidth = calculateActualViewWidth();
        int x = (getWidth() - actualViewWidth) / 2;

        if (x < 0) {
            x = 0;
        }

        for (int i = 0; i < count; i++) {
            x += radiusPx;
            if (position == i) {
                return x;
            }

            x += radiusPx + paddingPx;
        }

        return x;
    }

    private int calculateActualViewWidth() {
        int width = 0;
        int diameter = radiusPx * 2;

        for (int i = 0; i < count; i++) {
            width += diameter;

            if (i < count - 1) {
                width += paddingPx;
            }
        }

        return width;
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        if (attrs==null){
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkgBsrIndicator);
        count = typedArray.getInt(R.styleable.SkgBsrIndicator_count, 4);
        int position = typedArray.getInt(R.styleable.SkgBsrIndicator_select, 0);

        if (position < 0) {
            position = 0;
        } else if (count > 0 && position > count - 1) {
            position = count - 1;
        }

        selectedPosition = position;
        selectingPosition = position;

        paddingPx = (int) typedArray.getDimension(R.styleable.SkgBsrIndicator_padding, paddingPx);
        radiusPx = (int) typedArray.getDimension(R.styleable.SkgBsrIndicator_radius, radiusPx);

        unselectedColor = typedArray.getColor(R.styleable.SkgBsrIndicator_unselectedColor, unselectedColor);
        selectedColor = typedArray.getColor(R.styleable.SkgBsrIndicator_selectedColor, selectedColor);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int circleDiameterPx = radiusPx * 2;
        int desiredHeight = circleDiameterPx;
        int desiredWidth = 0;

        if (count != 0) {
            desiredWidth = (circleDiameterPx * count) + (paddingPx * (count - 1));
        }

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        if (width < 0) {
            width = 0;
        }

        if (height < 0) {
            height = 0;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int y = getHeight() / 2;

        for (int i = 0; i < count; i++) {
            int x = getXCoordinate(i);
            drawCircle(canvas, i, x, y);
        }
    }

    private void drawCircle(Canvas canvas, int position, int x, int y) {
        boolean selectedItem = position == selectedPosition || position == lastSelectedPosition;
        boolean selectingItem = position == selectingPosition || position == selectedPosition;
        boolean isSelectedItem = selectedItem | selectingItem;

        if (isSelectedItem) {
            drawWithAnimationEffect(canvas, position, x, y);
        } else {
            drawWithNoEffect(canvas, position, x, y);
        }
    }

    private void drawWithNoEffect(Canvas canvas, int position, int x, int y) {
        int radius = radiusPx;
        int color = unselectedColor;

        if (position == selectedPosition) {
            color = selectedColor;
        }

        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
    }

    private void drawWithAnimationEffect(Canvas canvas, int position, int x, int y) {
        paint.setColor(unselectedColor);
        canvas.drawCircle(x, y, radiusPx, paint);

        if (position == selectingPosition || position == selectedPosition) {
            paint.setColor(selectedColor);
            canvas.drawCircle(frameXCoordinate, y, radiusPx, paint);

        } else if (position == selectedPosition || position == lastSelectedPosition) {
            paint.setColor(selectedColor);
            canvas.drawCircle(frameXCoordinate, y, radiusPx, paint);
        }
    }

    public void setViewPager(ViewPager viewpager){
        if (viewpager!=null){
            count=viewpager.getAdapter().getCount();
            viewpager.addOnPageChangeListener(this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
       /* Pair<Integer, Float> progressPair = getProgress(position, positionOffset);
        int selectingPosition = progressPair.first;
        float selectingProgress = progressPair.second;

        if (selectingProgress == 1) {
            lastSelectedPosition = selectedPosition;
            selectedPosition = selectingPosition;
        }

        setProgress(selectingPosition, selectingProgress);
    }

    private void setProgress(int selectingPosition, float progress) {
        if (selectingPosition < 0) {
            selectingPosition = 0;

        } else if (selectingPosition > count - 1) {
            selectingPosition = count - 1;
        }

        if (progress < 0) {
            progress = 0;

        } else if (progress > 1) {
            progress = 1;
        }

        this.selectingPosition = selectingPosition;
        AbsAnimation animator = getSelectedAnimation();

        if (animator != null) {
            animator.progress(progress);
        }*/
    }

    private Pair<Integer,Float> getProgress(int position, float positionOffset) {
        boolean isRightOverScrolled = position > selectedPosition;
        boolean isLeftOverScrolled = position + 1 < selectedPosition;

        if (isRightOverScrolled || isLeftOverScrolled) {
            selectedPosition = position;
        }

        boolean isSlideToRightSide = selectedPosition == position && positionOffset != 0;
        int selectingPosition;
        float selectingProgress;

        if (isSlideToRightSide) {
            selectingPosition = position + 1;
            selectingProgress = positionOffset;

        } else {
            selectingPosition = position;
            selectingProgress = 1 - positionOffset;
        }

        if (selectingProgress > 1) {
            selectingProgress = 1;

        } else if (selectingProgress < 0) {
            selectingProgress = 0;
        }

        return new Pair<>(selectingPosition, selectingProgress);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
