package com.zhaorou.zrapplication.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


/**
 * @author kang
 */
public class ScrollTextView extends android.support.v7.widget.AppCompatTextView {
    public ScrollTextView(Context context) {
        super(context);
        initView();
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        initView();
    }

    private void initView(){
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();

        setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private int mTouchSlop;
    private int mClickTimeout;
    private float mStartX, mStartY, mLastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float deltaX = event.getX() - mStartX;
        float deltaY = event.getY() - mStartY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mStartX = event.getX();
                mStartY = event.getY();
                mLastX = mStartX;
                setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
                // 不想要父视图拦截触摸事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
                setPressed(false);
                getParent().requestDisallowInterceptTouchEvent(false);


                float time = event.getEventTime() - event.getDownTime();

                if (deltaX < mTouchSlop && deltaY < mTouchSlop && time < mClickTimeout) {
                 //   Log.d("mytest","----performClick------");
                    performClick();
                }
     /*           Log.d("mytest","----------");
                Log.d("mytest","deltaX:"+deltaX+"--"+mTouchSlop+"--"+(deltaX < mTouchSlop));
                Log.d("mytest","deltaY:"+deltaY+"--"+mTouchSlop+"--"+(deltaY < mTouchSlop ));
                Log.d("mytest","time:"+time+"--"+mClickTimeout+"--"+(time < mClickTimeout));*/
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    //setMovementMethod


}
