package com.smecking.bsrindicator.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.smecking.bsrindicator.R;


/**
 * Created by Smecking on 2016/12/26.
 */

public class SkgBzrIndicator extends View {

    private static final String DEFAULT_FOCUS_COLOR = "#F7F7F7";

    private Paint mPaint = new Paint();
    private Paint bzrPaint = new Paint();
    private int padding;
    private float radius;
    private float outRadius;
    private int color;
    private int width;
    private int height;
    private int count;


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
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStrokeWidth(2f);
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
        radius = typedArray.getDimension(R.styleable.SkgBzrIndicator_skg_radius, 15f);
        outRadius = typedArray.getDimension(R.styleable.SkgBzrIndicator_skg_out_radius, 20f);
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
        int desiredWidth;

        if (count == 1) {
            desiredWidth = (int) outRadius;
        } else {
            desiredWidth = (int) (outRadius * count + padding * (count - 1));

        }
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
        int desiredHeight = (int) outRadius;
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
        canvas.drawCircle(200, 20, radius, mPaint);
        canvas.drawCircle(300, 20, outRadius, bzrPaint);
    }
}
