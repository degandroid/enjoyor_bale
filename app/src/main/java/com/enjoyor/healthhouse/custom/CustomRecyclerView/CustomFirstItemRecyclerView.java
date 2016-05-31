package com.enjoyor.healthhouse.custom.CustomRecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.Scroller;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.ServiceAdapter;

/**
 * Created by jinshaoling on 15/12/11.
 */
public class CustomFirstItemRecyclerView extends RecyclerView {

    private int item_normal_height;
    private int item_max_height;
    private float item_normal_alpha;
    private float item_max_alpha;
    private float alpha_d;
    private float second_item_normal_font_size;
    private float second_item_max_font_size;

    private float item_normal_font_size;
    private float item_max_font_size;

    private float font_size_d;
    private float second_font_size_d;

    private Context context;
    private Scroller mScroller;
    private boolean isScroller = false;

    public CustomFirstItemRecyclerView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomFirstItemRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void init() {
        mScroller = new Scroller(context);

        item_max_height = (int) getResources().getDimension(R.dimen.item_max_height);
        item_normal_height = (int) getResources().getDimension(R.dimen.item_normal_height);
        item_normal_font_size = getResources().getDimension(R.dimen.item_normal_font_size);
        item_max_font_size = getResources().getDimension(R.dimen.item_max_font_size);
        second_item_normal_font_size = getResources().getDimension(R.dimen.second_item_normal_font_size);
        second_item_max_font_size = getResources().getDimension(R.dimen.second_item_max_font_size);
        item_normal_alpha = getResources().getFraction(R.fraction.item_normal_mask_alpha, 1, 1);
        item_max_alpha = getResources().getFraction(R.fraction.item_max_mask_alpha, 1, 1);
        font_size_d = item_max_font_size - item_normal_font_size;
        second_font_size_d = second_item_max_font_size-second_item_normal_font_size;
        alpha_d = item_max_alpha - item_normal_alpha;


    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if(state == SCROLL_STATE_IDLE && this.getChildAt(1)!=null && this.getChildAt(1).getY()!=item_max_height && this.getChildAt(1).getY()>0)
        {

//            RecyclerView.ViewHolder firstViewHolder = this
//                    .findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition());
//            LogUtil.d(Constant.TAG,"getY():"+this.getChildAt(0).getY());
//            LogUtil.d(Constant.TAG,"item_max_height/2:"+item_max_height/2);
//            LogUtil.d(Constant.TAG,"getTranslationY():"+this.getChildAt(0).getTranslationY());
//            LogUtil.d(Constant.TAG,"getScrollY():"+this.getChildAt(0).getScrollY());

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.getLayoutManager();
            int firstPositon = linearLayoutManager.findFirstVisibleItemPosition();
            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

            if(isScroller)
                return;
            if(this.getChildAt(1).getY()>= item_max_height / 2)
            {
                smoothScrollToPosition(firstPositon);
            }
            else
            {
//                LogUtil.d(Constant.TAG, "id:" + this.getChildAt(0).getId());
                if(this.getChildCount()==lastPosition)
                {
//                    LogUtil.d(Constant.TAG, "最后一个");
                    ServiceAdapter.ViewHolder viewHolder = (ServiceAdapter.ViewHolder)this
                            .findViewHolderForLayoutPosition(lastPosition-1) ;
                    if(viewHolder!= null && viewHolder instanceof ServiceAdapter.ViewHolder) {
                        viewHolder.itemView.getLayoutParams().height = item_max_height;
                        viewHolder.mark.setAlpha(1 * item_max_alpha);
                        viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_max_font_size);
                        viewHolder.second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, second_item_max_font_size);
                    }
//                    smoothScrollBy(0,-item_max_height);
//                    smoothScrollBy(0,item_max_height);
                }
                else {
                    smoothScrollBy(0, item_max_height / 2 + (int) this.getChildAt(0).getY());
//                    LogUtil.d(Constant.TAG, "scrollby:" + (item_max_height / 2 + (int) this.getChildAt(0).getY()));

                }
                isScroller = true;
//                LogUtil.d(Constant.TAG, "超过一半");

            }

//            this.scrollToPosition();
//            mScroller.startScroll(getScrollX(), getScrollY(), 0, -100,500);
//            invalidate();
        }
        else
        {
            isScroller = false;
        }
    }

//    @Override
//    public void computeScroll() {
//        super.computeScroll();
//        if(mScroller.computeScrollOffset())
//        {
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            postInvalidate();
//        }
//    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.getLayoutManager();
        ViewHolder firstViewHolder = this
                .findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition());
        ViewHolder secondViewHolder = this
                .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition());
        ViewHolder threeViewHolder = this
                .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);
        ViewHolder lastViewHolder = this
                .findViewHolderForLayoutPosition(linearLayoutManager.findLastVisibleItemPosition());

//        LogUtil.d(Constant.TAG,"firstViewHolder.itemView.getY():"+firstViewHolder.itemView.getY());

        if(firstViewHolder.itemView.getY()==0)
        {
            ServiceAdapter.ViewHolder viewHolder = (ServiceAdapter.ViewHolder) firstViewHolder;
            viewHolder.itemView.getLayoutParams().height = item_max_height;
            viewHolder.mark.setAlpha(1*item_max_alpha);
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,item_max_font_size);
            viewHolder.second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, second_item_max_font_size);

            ViewHolder twoViewHolder = this.findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition()+1);

            if(twoViewHolder != null && twoViewHolder instanceof  ServiceAdapter.ViewHolder) {
                ServiceAdapter.ViewHolder viewHolder_second = (ServiceAdapter.ViewHolder) this.findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition() + 1);
                viewHolder_second.itemView.getLayoutParams().height = item_normal_height;
                viewHolder_second.mark.setAlpha(1 * item_normal_alpha);
                viewHolder_second.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                viewHolder_second.second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, second_item_normal_font_size);
            }

            return;
        }
        if (firstViewHolder != null && firstViewHolder instanceof ServiceAdapter.ViewHolder) {
            ServiceAdapter.ViewHolder viewHolder = (ServiceAdapter.ViewHolder) firstViewHolder;
            if (viewHolder.itemView.getLayoutParams().height - dy <= item_max_height
                    && viewHolder.itemView.getLayoutParams().height - dy >= item_normal_height) {
                viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height - dy;
                viewHolder.mark.setAlpha(viewHolder.mark.getAlpha() - dy * alpha_d / item_normal_height);
                viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        viewHolder.text.getTextSize() - dy * font_size_d / item_normal_height);
                viewHolder.second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        viewHolder.second_text.getTextSize() - dy * second_font_size_d / item_normal_height);
                viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
            }
        }


        if (secondViewHolder != null && secondViewHolder instanceof ServiceAdapter.ViewHolder) {
            ServiceAdapter.ViewHolder viewHolder = (ServiceAdapter.ViewHolder) secondViewHolder;
            if (viewHolder.itemView.getLayoutParams().height + dy <= item_max_height
                    && viewHolder.itemView.getLayoutParams().height + dy >= item_normal_height) {
                viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height + dy;
                viewHolder.mark.setAlpha(viewHolder.mark.getAlpha() + dy * alpha_d / item_normal_height);
                viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        viewHolder.text.getTextSize() + dy * font_size_d / item_normal_height);
                viewHolder.second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        viewHolder.second_text.getTextSize() + dy * second_font_size_d / item_normal_height);
                viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
            }
        }

        if (threeViewHolder != null && threeViewHolder instanceof ServiceAdapter.ViewHolder) {
            ServiceAdapter.ViewHolder viewHolder = (ServiceAdapter.ViewHolder) threeViewHolder;
            viewHolder.mark.setAlpha(item_normal_alpha);
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
            viewHolder.second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, second_item_normal_font_size);
            viewHolder.itemView.getLayoutParams().height = item_normal_height;
            viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
        }
        if (lastViewHolder != null && lastViewHolder instanceof ServiceAdapter.ViewHolder) {
            ServiceAdapter.ViewHolder viewHolder = (ServiceAdapter.ViewHolder) lastViewHolder;
            viewHolder.mark.setAlpha(item_normal_alpha);
            viewHolder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
            viewHolder.second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, second_item_normal_font_size);
            viewHolder.itemView.getLayoutParams().height = item_normal_height;
            viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
        }


//    LogUtil.d(Constant.TAG, "scroll state" + this.getScrollState());

    }
}
