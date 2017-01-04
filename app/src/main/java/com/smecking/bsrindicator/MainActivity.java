package com.smecking.bsrindicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.smecking.bsrindicator.adapter.IndicatorAdapter;
import com.smecking.bsrindicator.indicator.FPageIndicator;
import com.smecking.bsrindicator.indicator.SkgBzrIndicator;

import static com.smecking.bsrindicator.R.id.viewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private IndicatorAdapter mAdapter;
    private FPageIndicator mPageIndicator;
    private SkgBzrIndicator mSkgBzrIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager= (ViewPager) findViewById(viewPager);
        mPageIndicator= (FPageIndicator) findViewById(R.id.indicator);
        mSkgBzrIndicator= (SkgBzrIndicator) findViewById(R.id.skg_indicator);
        mAdapter=new IndicatorAdapter();

        mViewPager.setAdapter(mAdapter);
        mPageIndicator.attachToViewPager(mViewPager);
       // mSkgBzrIndicator.setViewpager(mViewPager);

       // isLastPage();
    }

    public void isLastPage() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //判断是不是要跳转下页一个标记位
            private boolean flag;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("position : "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("smecking", "onPageScrollStateChanged: " + state);
                switch(state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        //拖的时候才进入下一页
                        flag = false;
                        Log.d("smecking", "SCROLL_STATE_DRAGGING: " + ViewPager.SCROLL_STATE_DRAGGING);

                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        flag = true;
                        Log.d("smecking", "SCROLL_STATE_SETTLING: " + ViewPager.SCROLL_STATE_SETTLING);
                        break;


                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.d("smecking", "SCROLL_STATE_IDLE: " + ViewPager.SCROLL_STATE_IDLE+"  mViewPager.getCurrentItem() "+mViewPager.getCurrentItem());
                        /**
                         * 判断是不是最后一页，同是是不是拖的状态
                         */
                        if(mViewPager.getCurrentItem() == mAdapter.getCount() - 1 && !flag) {

                            // overridePendingTransition(0, 0);
                           // startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out
                            );
                            finish();
                        }
                        flag = true;

                        break;
                }
            }
        });
    }
}
