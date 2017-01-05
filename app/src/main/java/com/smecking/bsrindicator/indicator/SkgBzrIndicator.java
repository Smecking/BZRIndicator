package com.smecking.bsrindicator.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.smecking.bsrindicator.R;


/**
 * Created by Smecking on 2016/12/26.
 */

public class SkgBzrIndicator extends View implements ViewPager.OnPageChangeListener {

    private static final String DEFAULT_FOCUS_COLOR = "#F7F7F7";

    private Paint mPaint = new Paint();
    private Paint bzrPaint = new Paint();
    private int padding;
    private int radius;
    private int outRadius;
    private int color;
    private int width;
    private int height;
    private int count;
    private int drawPosition;
    private float drawPositionOffset;


    public SkgBzrIndicator(Context context) {
        super(context);
        init(context, null);
    }


    public SkgBzrIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initPaint();
    }


    public SkgBzrIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void initPaint() {
        mPaint.setColor(color);       //设置画笔颜色
        mPaint.setStrokeWidth(1f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        bzrPaint.setColor(color);
        bzrPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

    }


    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkgBzrIndicator);
        padding = (int) typedArray.getDimension(R.styleable.SkgBzrIndicator_skg_padding, 0f);
        radius = (int) typedArray.getDimension(R.styleable.SkgBzrIndicator_skg_radius, dpToPx(5));
        outRadius = (int) typedArray.getDimension(R.styleable.SkgBzrIndicator_skg_out_radius, dpToPx(3));
        color = typedArray.getColor(R.styleable.SkgBzrIndicator_skg_color, Color.parseColor(DEFAULT_FOCUS_COLOR));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasureWidth(widthMeasureSpec);
        height = getMeasureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

    }


    public int getMeasureWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // int desiredWidth = outRadius * 2 * count + padding * (count - 1);
        int desiredWidth = radius * 2 * count + padding * (count - 1);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }
        return width;
    }

    public int getMeasureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int desiredHeight = outRadius;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }
        return height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawCircle(200, 20, radius, mPaint);
//        canvas.drawCircle(300, 20, outRadius, bzrPaint);
        int height = getHeight();

        for (int i = 0; i < count; i++) {
            //canvas.drawCircle(outRadius * i * 2 + outRadius + padding * i, height / 2, outRadius, mPaint);
            canvas.drawCircle(radius * i * 2 + radius + padding * i, height / 2, outRadius, mPaint);
        }
         // canvas.drawCircle(radius, height / 2, radius, bzrPaint);
        canvas.drawCircle((radius*2*drawPosition+radius+padding*drawPosition)+(radius*2+padding)*drawPositionOffset,height/2,radius,bzrPaint);
    }

    public void setViewPager(ViewPager vp) {
        if (vp != null) {
            count = vp.getAdapter().getCount();
            System.out.println(count);
            vp.addOnPageChangeListener(this);
        }
    }

    private static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        drawPosition = position;
        drawPositionOffset = positionOffset;
        System.out.println(drawPosition+"---------"+drawPositionOffset);
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
