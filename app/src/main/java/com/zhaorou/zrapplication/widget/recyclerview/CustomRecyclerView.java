package com.zhaorou.zhuanquan.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CustomRecyclerView extends RecyclerView implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    private boolean isLongPress;

    public CustomRecyclerView(Context context) {
        this(context, null);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGestureDetector(context);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && gestureDetector.onTouchEvent(e) && !isLongPress && itemClickListener != null) {
            int childAdapterPosition = rv.getChildAdapterPosition(childView);
            this.itemClickListener.OnItemClick(rv, childView, childAdapterPosition);
            return true;
        }
        if (childView != null && isLongPress && itemLongClickListener != null) {
            int childAdapterPosition = rv.getChildAdapterPosition(childView);
            this.itemLongClickListener.OnItemLongClick(rv, childView, childAdapterPosition);
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private void initGestureDetector(Context context) {
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                isLongPress = false;
                return super.onDown(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                isLongPress = false;
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                isLongPress = true;
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
        addOnItemTouchListener(this);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
        addOnItemTouchListener(this);
    }

    public interface OnItemClickListener{
        void OnItemClick(ViewGroup parent, View view, int position);
    }

    public interface OnItemLongClickListener{
        void OnItemLongClick(ViewGroup parent, View view, int position);
    }
}
