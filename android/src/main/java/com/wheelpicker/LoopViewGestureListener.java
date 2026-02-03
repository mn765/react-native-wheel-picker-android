package com.wheelpicker;

/**
 * Created by prati on 06-Jul-16 at VARAHI TECHNOLOGIES.
 * http://www.varahitechnologies.com
 */
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

final class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    private final WeakReference<LoopView> loopViewRef;

    LoopViewGestureListener(LoopView loopview) {
        super();
        loopViewRef = new WeakReference<>(loopview);
    }

    @Override
    public final boolean onDown(MotionEvent motionevent) {
        LoopView loopView = loopViewRef.get();
        if (loopView != null) {
            loopView.cancelFuture();
        }
        return true;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        LoopView loopView = loopViewRef.get();
        if (loopView != null) {
            loopView.smoothScroll(velocityY);
        }
        return true;
    }
}
