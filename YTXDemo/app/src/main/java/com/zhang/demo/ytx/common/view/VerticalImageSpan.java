package com.zhang.demo.ytx.common.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Created by Administrator on 2016/7/8.
 */
public class VerticalImageSpan extends ImageSpan {
    private int mPadding = 0;

    public VerticalImageSpan(Drawable d) {
        super(d, 1);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable drawable = getDrawable();
        canvas.save();

        int length = 0;
        int dy = bottom - drawable.getBounds().bottom + mPadding;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            length = text.length();
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isLetterOrDigit(text.charAt(i))) {
                continue;
            }
            dy -= paint.getFontMetricsInt().descent;
            canvas.translate(x, dy);
            drawable.draw(canvas);
            canvas.restore();
        }
    }

    public final void setPadding(int padding) {
        mPadding = padding;
    }
}
