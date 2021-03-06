package com.zhang.demo.ytx.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zhang.demo.ytx.R;
import com.zhang.demo.ytx.common.utils.DemoUtils;
import com.zhang.demo.ytx.common.utils.LogUtil;

/**
 * 自定义ListView，处理焦点问题
 *
 * Created by Administrator on 2016/7/7.
 */
public class SuperListView extends ListView {
    private static final String LOG_TAG = SuperListView.class.getSimpleName();
    private static final int MSG_WAHT = 0x64;
    private boolean mDrawingCache;
    private boolean mChildrenDrawingCache;
    private Handler mHandler;
    private boolean mMeasureByItems;
    private OnSuperListViewScrollListener mScrollListener;

    private final AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && mMeasureByItems) {
                return;
            }
            mHandler.removeMessages(MSG_WAHT);
            Message msg = mHandler.obtainMessage(MSG_WAHT);
            mHandler.sendMessageDelayed(msg, 50L);
            if (mScrollListener == null) {
                return;
            }
            mScrollListener.onSuperScrollStateChanged(view, scrollState);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mScrollListener == null) {
                return;
            }
            mScrollListener.onSuperScroll(view, firstVisibleItem,visibleItemCount, totalItemCount);
        }
    };



    public SuperListView(Context context) {
        this(context, null);
//        initSuperListView();
    }

    public SuperListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawingCache = false;
        mChildrenDrawingCache = false;
        mScrollListener = null;
        mMeasureByItems = false;
        mHandler = new SuperListViewHandler(Looper.getMainLooper());
        initSuperListView();
    }

    private void initSuperListView() {
        setSelector(R.color.transparent);
        setOnScrollListener(mOnScrollListener);
        setOnTouchListener(null);

        if (DemoUtils.getSdkint() < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }

        //去掉模糊边缘
        setOverScrollMode(AbsListView.OVER_SCROLL_NEVER);
    }

    private int measureWidth(int measureSpec) {
        return measureSpec;
    }

    private int measureHeight(int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            return View.MeasureSpec.makeMeasureSpec(measureSpec, mode);
        } else if (mode == MeasureSpec.EXACTLY) {
            ListAdapter adapter = getAdapter();
            if (adapter == null) {
                measureSpec = 0;
            } else {
                int result = 0;
                for (int i = 0; i < adapter.getCount(); i++) {
                    View contentView = adapter.getView(i, null, null);
                    contentView.measure(0, 0);
                    result += contentView.getMeasuredHeight();
                }
                measureSpec = result + getDividerHeight() * (adapter.getCount() - 1);
            }
        }
        return View.MeasureSpec.makeMeasureSpec(measureSpec, mode);
    }

    @Override
    public void draw(Canvas canvas) {
        setDrawingCacheEnabled(mDrawingCache);
        setChildrenDrawingCacheEnabled(mChildrenDrawingCache);
        setChildrenDrawnWithCacheEnabled(mChildrenDrawingCache);
        super.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMeasureByItems) {
            widthMeasureSpec = measureWidth(widthMeasureSpec);
            heightMeasureSpec = measureHeight(heightMeasureSpec);
        }
        LogUtil.d(LOG_TAG, "widthMeasureSpec:" + widthMeasureSpec + "|heightMeasureSpec:" + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setMeasureByItems(boolean measure) {
        mMeasureByItems = measure;
    }

    public void setOnScrollListener(OnSuperListViewScrollListener l) {
        mScrollListener = l;
    }

    public void setmIsChildrenDrawingCache(boolean enabled) {
        mDrawingCache = enabled;
    }

    public void setmIsDrawingCache(boolean enabled) {
        mDrawingCache = enabled;
    }

    public interface OnSuperListViewScrollListener {
        /**
         * 滑动状态改变
         * @param view
         * @param scrollState
         */
        void onSuperScrollStateChanged(AbsListView view, int scrollState);

        /**
         * 当前滑动过程中
         * @param view
         * @param firstVisibleItem
         * @param visibleItemCount
         * @param totalItemCount
         */
        void onSuperScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    /**
     * 处理事件分发
     */
    public class SuperListViewHandler extends Handler {
        public SuperListViewHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WAHT:
                    requestLayout();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);

        }
    }

}
