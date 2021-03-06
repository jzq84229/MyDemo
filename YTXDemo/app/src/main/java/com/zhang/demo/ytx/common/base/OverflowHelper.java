package com.zhang.demo.ytx.common.base;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zhang.demo.ytx.R;
import com.zhang.demo.ytx.common.utils.DensityUtil;
import com.zhang.demo.ytx.common.view.PopupMenuListView;

/**
 * 自定义Overflow ActionBar菜单，显示下拉更多选项
 * Created by Administrator on 2016/7/7.
 */
public class OverflowHelper {

    private Context mContext;
    /**菜单承载区域View*/
    private PopupWindow mPopupWindow;
    /**菜单选项*/
    private LinearLayout mPopupLayout;
    /**下拉菜单显示区域*/
    private PopupMenuListView mListView;
    /**下拉菜单数据适配器*/
    private OverflowAdapter mAdapter;
    /**菜单颜色状态*/
    private int mNormalColor;
    private int mDisabledColor;

    public OverflowHelper(Context context) {
        this.mContext = context;
        mNormalColor = mContext.getResources().getColor(R.color.white);
        mDisabledColor = mContext.getResources().getColor(R.color.text_disabled);
        mPopupLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.comm_popup_menu, null, true);
        mListView = (PopupMenuListView) mPopupLayout.findViewById(R.id.comm_popup_list);
        mAdapter = new OverflowAdapter(context, this);
        mListView.setAdapter(mAdapter);
        mListView.setOnKeyListener(mOnKeyListener);
        mPopupWindow = new PopupWindow(mPopupLayout, -2, -2, true);
        mPopupWindow.setContentView(mPopupLayout);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setWidth(414);
        mPopupWindow.getContentView().setOnTouchListener(mOnTouchListener);
        mPopupWindow.update();
    }

    /**
     * 显示菜单
     * @param anchor
     * @param xoff
     * @param yoff
     */
    public void showAsDropDown(View anchor, float xoff, float yoff) {
        mPopupWindow.showAsDropDown(anchor, DensityUtil.dip2px(xoff), DensityUtil.dip2px(yoff));
    }

    public void showAsDropDown(View anchor) {
        if (mPopupWindow != null && anchor != null) {
            mPopupWindow.showAsDropDown(anchor, DensityUtil.dip2px(0.0F), DensityUtil.dip2px(-11.0F));
        }
    }

    /**
     * Item点击事件监听
     * @param itemClickListener
     */
    public void setOnOverflowItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        mListView.setOnItemClickListener(itemClickListener);
    }

    /**
     * 设置菜单显示数据
     * @param items
     */
    public void setOverflowItems(OverflowAdapter.OverflowItem[] items) {
        mAdapter.setOverflowItem(items);
        mAdapter.notifyDataSetChanged();
        mListView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int measuredWidth = mListView.getMeasuredWidth();
        mPopupWindow.setWidth(measuredWidth);
        mPopupWindow.update();
    }

    /**
     * 菜单是否显示
     * @return
     */
    public boolean isOverflowShowing() {
        return mPopupWindow.isShowing();
    }

    /**
     * 关闭菜单
     */
    public void dismiss() {
        if (mPopupWindow.isShowing()) {
            return ;
        }
        mPopupWindow.dismiss();
    }

    /**
     * 获取normal时文本颜色
     */
    public int getNormalColor() {
        return mNormalColor;
    }

    /**
     * 获取disable时文本颜色
     */
    public int getDisabledColor() {
        return mDisabledColor;
    }

    private final View.OnKeyListener mOnKeyListener = new View.OnKeyListener(){
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_MENU && event.getAction() != KeyEvent.ACTION_DOWN) {
                return false;
            }
            dismiss();
            return true;
        }
    };

    private final View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            dismiss();
            return false;
        }
    };
}
