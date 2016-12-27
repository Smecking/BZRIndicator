package com.smecking.bsrindicator.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Smecking on 2016/12/26.
 */

public class SkgBsrIndicator extends View implements PageIndicator {
    public SkgBsrIndicator(Context context) {
        super(context);
    }

    public SkgBsrIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SkgBsrIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setViewPager(ViewPager vp) {

    }

    @Override
    public void setViewPager(ViewPager vp, int realCount) {

    }

    @Override
    public void setCurrentItem(int item) {

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
