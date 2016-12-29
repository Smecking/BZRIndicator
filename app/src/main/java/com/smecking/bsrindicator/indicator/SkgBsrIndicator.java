package com.smecking.bsrindicator.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.smecking.bsrindicator.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Smecking on 2016/12/26.
 */

public class SkgBsrIndicator extends View implements PageIndicator {

    private Context context;
    private ViewPager vp;
    private List<GradientDrawable> unselectDrawables = new ArrayList<>();
    private List<Rect> unselectRects = new ArrayList<>();
    private GradientDrawable selectDrawable = new GradientDrawable();
    private Rect selectRect = new Rect();
    private int count;
    private int currentItem;
    private float positionOffset;

    private int bsr_radius;
    private int bsr_color;
    private int indicatorGap;

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
        this.context = context;
        indicatorGap = dp2px(12);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkgBsrIndicator);
        bsr_radius = typedArray.getDimensionPixelSize(R.styleable.SkgBsrIndicator_bsr_radius, dp2px(6));
        bsr_color = typedArray.getColor(R.styleable.SkgBsrIndicator_bsr_color, Color.parseColor("#ffffff"));
    }


    @Override
    public void setViewPager(ViewPager vp) {
        if (isValid(vp)) {
            this.vp = vp;
            this.count = vp.getAdapter().getCount();
            vp.removeOnPageChangeListener(this);
            vp.addOnPageChangeListener(this);
            unselectDrawables.clear();
            unselectRects.clear();
            for (int i = 0; i <= count; i++) {
                unselectDrawables.add(new GradientDrawable());
                unselectRects.add(new Rect());
            }
            invalidate();
        }
    }

    @Override
    public void setViewPager(ViewPager vp, int realCount) {

    }

    @Override
    public void setCurrentItem(int item) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        /**
         * position:当前View的位置
         * positionOffset:当前View的偏移量比例.[0,1)
         */
        this.currentItem = position;
        this.positionOffset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private boolean isValid(ViewPager vp) {
        if (vp == null) {
            throw new IllegalStateException("ViewPager can not be NULL!");
        }

        if (vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager adapter can not be NULL!");
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {//大小确定,直接使用
            result = specSize;
        } else {
            int padding = getPaddingTop() + getPaddingBottom();
            result = padding + bsr_radius * 2;
            //如果父视图的测量要求为AT_MOST,即限定了一个最大值,则再从系统建议值和自己计算值中去一个较小值
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY || count == 0) {//大小确定,直接使用
            result = specSize;
        } else {
            int padding = getPaddingLeft() + getPaddingRight();
            result = padding + bsr_radius * 2 * count + indicatorGap * (count - 1);
        }
        //如果父视图的测量要求为AT_MOST,即限定了一个最大值,则再从系统建议值和自己计算值中去一个较小值
        if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (count <= 0)
            return;

        int verticalOffset = getPaddingTop() + (getHeight() - getPaddingTop() - getPaddingBottom()) / 2 - bsr_radius;
        int indicatorLayoutWidth = bsr_radius * 2 * count + indicatorGap * (count - 1);
        int horizontalOffset = getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 - indicatorLayoutWidth / 2;

        drawUnselect(canvas, count, verticalOffset, horizontalOffset);
        drawSelect(canvas, verticalOffset, horizontalOffset);
    }

    private void drawSelect(Canvas canvas, int verticalOffset, int horizontalOffset) {
        int delta = (int) ((indicatorGap + bsr_radius * 2) * positionOffset);

        selectRect.left = horizontalOffset + (bsr_radius * 2 + indicatorGap) * currentItem + delta;
        selectRect.top = verticalOffset;
        selectRect.right = selectRect.left + bsr_radius * 2;
        selectRect.bottom = selectRect.top + bsr_radius * 2;

        selectDrawable.setColor(bsr_color);
        selectDrawable.setStroke(dp2px(5), bsr_color);
        selectDrawable.setBounds(selectRect);
        selectDrawable.draw(canvas);
    }

    private void drawUnselect(Canvas canvas, int count, int verticalOffset, int horizontalOffset) {
        for (int i = 0; i < count; i++) {
            Rect rect = unselectRects.get(i);
            rect.left = horizontalOffset + (bsr_radius * 2 + indicatorGap) * i;
            rect.top = verticalOffset;
            rect.right = rect.left + bsr_radius * 2;
            rect.bottom = rect.top + bsr_radius * 2;

            GradientDrawable unselectDrawable = unselectDrawables.get(i);
            unselectDrawable.setCornerRadius(dp2px(8));
            unselectDrawable.setColor(bsr_color);
            unselectDrawable.setStroke(dp2px(1), bsr_color);
            unselectDrawable.setBounds(rect);
            unselectDrawable.draw(canvas);
        }
    }

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
