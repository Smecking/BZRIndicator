package com.smecking.bsrindicator.adapter;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smecking.bsrindicator.R;

import java.util.Random;

/**
 * Created by Smecking on 2016/12/26.
 */

public class IndicatorAdapter extends PagerAdapter {

    private final Random random = new Random();
    private int size;

    public IndicatorAdapter() {
        super();
        size = 4;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView textView = new TextView(container.getContext());
        //textView.setText(String.valueOf(position + 1));
        switch (position) {
            case 0:
                textView.setText(R.string.hello);
                break;
            case 1:
                textView.setText(R.string.i);
                break;
            case 2:
                textView.setText(R.string.am);
                break;
            case 3:
                textView.setText(R.string.smecking);
                break;
        }
        textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(48);
        container.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return textView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
