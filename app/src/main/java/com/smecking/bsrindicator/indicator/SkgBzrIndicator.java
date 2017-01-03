package com.smecking.bsrindicator.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.smecking.bsrindicator.R;
import com.smecking.bsrindicator.utils.DensityUtils;

import static com.smecking.bsrindicator.utils.DensityUtils.dpToPx;


/**
 * Created by Smecking on 2016/12/26.
 */

public class SkgBzrIndicator extends View {

    private int count;
    //圆环半径
    private float outRadius;
    private float innerRadius_1;
    private float innerRadius_2;

    private int focusColor;

    private float padding;

    private int selectedPos;

    private int drawPosition;
    private float drawPositionOffset;
    private int innerRadius = DensityUtils.dpToPx(DEFAULT_INNER_RADIUS_SIZE);

    private static final int DEFAULT_OUT_RADIUS_SIZE = 5;
    private static final int DEFAULT_INNER_RADIUS_SIZE = 7;

    private static final String DEFAULT_FOCUS_COLOR = "#eeeeee";

    private Paint bzrPaint = new Paint();
    private Paint outPaint = new Paint();


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            System.out.println("dddddddddddddddd");
            drawPosition = position;
            drawPositionOffset = positionOffset;
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            setSelectedPos(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public SkgBzrIndicator(Context context) {
        super(context);
        init(context, null);
    }


    public SkgBzrIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SkgBzrIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkgBzrIndicator);
        outRadius = typedArray.getDimension(R.styleable.SkgBzrIndicator_skg_out_radius,
                dpToPx(DEFAULT_OUT_RADIUS_SIZE));
        innerRadius_1 = typedArray.getDimension(R.styleable.SkgBzrIndicator_skg_radius1,
                DensityUtils.dpToPx(DEFAULT_INNER_RADIUS_SIZE));
        innerRadius_2 = typedArray.getDimension(R.styleable.SkgBzrIndicator_skg_radius2,
                DensityUtils.dpToPx(DEFAULT_INNER_RADIUS_SIZE));
        focusColor = typedArray.getColor(R.styleable.SkgBzrIndicator_skg_focus_color,
                Color.parseColor(DEFAULT_FOCUS_COLOR));
        padding = typedArray.getDimension(R.styleable.SkgBzrIndicator_skg_padding, 0F);
        typedArray.recycle();

        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setColor(focusColor);
        outPaint.setStrokeWidth(2F);

        bzrPaint.setStyle(Paint.Style.FILL);
        bzrPaint.setColor(focusColor);
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int width;
//        int height;
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        int desiredWidth;
//        desiredWidth = (int) (innerRadius_2 * 2 * count + padding * (count - 1));
//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = widthSize;
//        } else if (widthMode == MeasureSpec.AT_MOST) {
//            width = Math.min(desiredWidth, widthSize);
//        } else {
//            width = desiredWidth;
//        }
//
//        int desiredHeight = (int) outRadius;
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize;
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            height = Math.min(desiredHeight, heightSize);
//        } else {
//            height = desiredHeight;
//        }
//
//        setMeasuredDimension(width, height);
//
//
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("draw....");
        int height = getHeight();
        for (int i = 0; i < count; i++) {
            canvas.drawCircle(100f, 50f, innerRadius, outPaint);
            System.out.println(count);
        }

    }


    public void setSelectedPos(int pos) {
        if (this.selectedPos != pos) {
            this.selectedPos = pos;
            invalidate();
        }
    }

    public void setViewpager(ViewPager vp) {
        if (vp != null) {
            count = vp.getAdapter().getCount();
            vp.addOnPageChangeListener(pageChangeListener);
        }
    }

}
