package com.think.guoyh.widget.page.anim;

import android.graphics.Canvas;
import android.view.View;

/**
 * Created by newbiechen on 17-7-24.
 */

public class NonePageAnim extends HorizonPageAnim {

    public NonePageAnim(int w, int h, View view, OnPageChangeListener listener) {
        super(w, h, view, listener);
    }

    @Override
    public void drawStatic(Canvas canvas) {
        if (isCancel){
            canvas.drawBitmap(mCurBitmap, 0, top, null);
        }else {
            canvas.drawBitmap(mNextBitmap, 0, top, null);
        }
    }

    @Override
    public void drawMove(Canvas canvas) {
        if (isCancel){
            canvas.drawBitmap(mCurBitmap, 0, top, null);
        }else {
            canvas.drawBitmap(mNextBitmap, 0, top, null);
        }
    }

    @Override
    public void startAnim() {
    }
}
