package com.zhaorou.zrapplication.widget.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable divider;
    private int height;
    private Paint paint;

    public CustomItemDecoration(Context context) {
        TypedArray ta = context.obtainStyledAttributes(ATTRS);
        divider = ta.getDrawable(0);
        height = divider != null ? divider.getIntrinsicHeight() : 0;
        ta.recycle();
    }

    public CustomItemDecoration(Drawable divider) {
        this.divider = divider;
        height = divider.getIntrinsicHeight();
    }

    public CustomItemDecoration(int height, int color) {
        this.height = height;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        //注意：GridLayoutManager是继承自LinearLayoutManager，所以这里必须把GridLayoutManager的判断放在前面
        //否则即使LayoutManager是GridLayoutManager，条件判定仍会进入LinearLayoutManager的代码段，将会导致
        //绘制的分割线出现错误
        if (layoutManager instanceof GridLayoutManager) {
            int position = parent.getChildAdapterPosition(view);
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            int orientation = ((GridLayoutManager) layoutManager).getOrientation();
            if ((position + 1) % spanCount == 0 && orientation == GridLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, height);
            } else {
                outRect.set(0, 0, height, height);
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.set(0, 0, height, 0);
            } else {
                outRect.set(0, 0, 0, height);
            }
        }

    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin + height;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + height;
            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
            if (paint != null) {
                c.drawRect(left, top, right, bottom, paint);
            }
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;
            if (layoutManager instanceof GridLayoutManager) {
                bottom = bottom + height;
            }
            int left = child.getRight() + params.rightMargin;
            int right = left + height;
            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
            if (paint != null) {
                c.drawRect(left, top, right, bottom, paint);
            }
        }
    }
}
