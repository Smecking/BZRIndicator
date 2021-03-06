package com.smecking.bsrindicator.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    private Paint bzrPaint2 = new Paint();
    private Path mPath = new Path();
    private int padding;
    private int radius;
    private int outRadius;
    private int color;
    private int width;
    private int height;
    private int count;
    private int drawPosition;
    private int currentPosition;
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
        bzrPaint.setAntiAlias(true);

        bzrPaint2.setColor(Color.BLUE);
        bzrPaint2.setStyle(Paint.Style.FILL);
        bzrPaint2.setAntiAlias(true);
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
        //圆1X坐标点
        int circle1X=radius * 2 * drawPosition + radius +padding * drawPosition;
        //向左滑圆1X坐标
        int circle1Xz=radius*2*currentPosition+radius+padding*currentPosition;
        System.out.println("circle1X---"+circle1X);
        //圆2X坐标点
        int circle2X= (int) ((radius * 2 * drawPosition + radius + padding * drawPosition) + (radius * 2 + padding) * drawPositionOffset);
        int height = getHeight();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        Paint paint1=new Paint();
        paint1.setColor(Color.RED);
        for (int i = 0; i < count; i++) {
            canvas.drawCircle(radius * i * 2 + radius + padding * i, height / 2, outRadius, mPaint);
        }
       // mPath.moveTo((radius * 2 + padding) * drawPosition + radius, (height - radius * 2 * (1 - drawPositionOffset)) / 2);

        if (currentPosition>drawPosition&drawPositionOffset!=0){
            //绘制圆1点一
            canvas.drawCircle((radius * 2 + padding) * currentPosition + radius, (height - radius * 2 * drawPositionOffset) / 2, 3, paint);
            //绘制圆1点二
            canvas.drawCircle((radius * 2 + padding) * currentPosition + radius, (height - radius * 2 * drawPositionOffset) / 2 + radius * 2 *  drawPositionOffset, 3, paint);
        }else {
            //绘制圆1点一
            canvas.drawCircle((radius * 2 + padding) * drawPosition + radius, (height - radius * 2 * (1 - drawPositionOffset)) / 2, 3, paint);
            //绘制圆1点二
            canvas.drawCircle((radius * 2 + padding) * drawPosition + radius, (height - radius * 2 * (1 - drawPositionOffset)) / 2 + radius * 2 * (1 - drawPositionOffset), 3, paint);
        }

        if(currentPosition>drawPosition&drawPositionOffset!=0){
            //绘制圆2点1
            canvas.drawCircle((radius * 2 * drawPosition + radius + padding * drawPosition) + (radius * 2 + padding) * drawPositionOffset, (height - radius * 2 *(1-drawPositionOffset)) / 2, 3, paint1);
            //绘制圆2点2
            canvas.drawCircle((radius * 2 * drawPosition + radius + padding * drawPosition) + (radius * 2 + padding) * drawPositionOffset, radius * 2 *(1- drawPositionOffset) + (height - radius * 2 *(1-drawPositionOffset)) / 2, 3, paint1);
        }else {
            //绘制圆2点1            canvas.drawCircle((radius * 2 * drawPosition + radius + padding * drawPosition) + (radius * 2 + padding) * drawPositionOffset, (height - radius * 2 *drawPositionOffset) / 2, 3, paint1);

            canvas.drawCircle((radius * 2 * drawPosition + radius + padding * drawPosition) + (radius * 2 + padding) * drawPositionOffset, (height - radius * 2 *drawPositionOffset) / 2, 3, paint1);
            //绘制圆2点2
            canvas.drawCircle((radius * 2 * drawPosition + radius + padding * drawPosition) + (radius * 2 + padding) * drawPositionOffset, radius * 2 * drawPositionOffset + (height - radius * 2 * drawPositionOffset) / 2, 3, paint1);
        }
        if (currentPosition>drawPosition&drawPositionOffset!=0){
            //绘制控制点
            canvas.drawCircle((radius * 2 + padding) * currentPosition+(radius *drawPositionOffset), height / 2, 3, paint);
        }else {
            //绘制控制点
            canvas.drawCircle((radius + radius * drawPositionOffset) + (radius * 2 + padding) * drawPosition, height / 2, 3, paint);
        }
        if (currentPosition>drawPosition&drawPositionOffset!=0){
            canvas.drawCircle(circle1Xz, height / 2, radius * (drawPositionOffset), bzrPaint2);
            canvas.drawCircle(circle2X, height / 2, radius * (1-drawPositionOffset), bzrPaint);
        }else {
            canvas.drawCircle(circle1X, height / 2, radius * (1 - drawPositionOffset), bzrPaint2);
            canvas.drawCircle(circle2X, height / 2, radius * drawPositionOffset, bzrPaint);
        }

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
        System.out.println("onpagescroll-------"+drawPosition+"    currentPo----"+currentPosition+"  offset---"+positionOffset);
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition=position;
        System.out.println("onpsgeselect-------"+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
