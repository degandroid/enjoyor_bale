package com.enjoyor.healthhouse.custom.CustomRecyclerView;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by dongjunkun on 2015/12/7.
 */
public class MyLinearLayoutManager extends LinearLayoutManager {
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {
                    @Override
                    protected int calculateTimeForScrolling(int dx) {
                        // 此函数计算滚动dx的距离需要多久，当要滚动的距离很大时，比如说52000，
                        // 经测试，系统会多次调用此函数，每10000距离调一次，所以总的滚动时间
                        // 是多次调用此函数返回的时间的和，所以修改每次调用该函数时返回的时间的
                        // 大小就可以影响滚动需要的总时间，可以直接修改些函数的返回值，也可以修改
                        // dx的值，这里暂定使用后者.
                        // (See LinearSmoothScroller.TARGET_SEEK_SCROLL_DISTANCE_PX)
                        if (dx < 1000) {
                            dx = 1000;
                        }
                        return super.calculateTimeForScrolling(dx);
                    }

                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return this.computeScrollVectorForPosition(targetPosition);
                    }
                };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }


}
