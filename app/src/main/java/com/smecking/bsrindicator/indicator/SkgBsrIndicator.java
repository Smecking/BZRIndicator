package com.smecking.bsrindicator.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.smecking.bsrindicator.R;


/**
 * Created by Smecking on 2016/12/26.
 */

public class SkgBsrIndicator extends View implements ViewPager.OnPageChangeListener {


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

    }

    private void initAnimation() {

    }

    private void initFrameValues() {

    }



    private void initAttributes(Context context, AttributeSet attrs) {
        if (attrs==null){
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkgBsrIndicator);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
