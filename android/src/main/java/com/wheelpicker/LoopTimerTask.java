package com.wheelpicker;

import java.lang.ref.WeakReference;
import java.util.TimerTask;

final class LoopTimerTask extends TimerTask {

    float a;
    final float velocityY;
    private final WeakReference<LoopView> loopViewRef;

    LoopTimerTask(LoopView loopview, float velocityY) {
        super();
        loopViewRef = new WeakReference<>(loopview);
        this.velocityY = velocityY;
        a = Integer.MAX_VALUE;
    }

    @Override
    public final void run() {
        LoopView loopView = loopViewRef.get();
        if (loopView == null) {
            return;
        }
        
        if (a == Integer.MAX_VALUE) {
            if (Math.abs(velocityY) > 2000F) {
                if (velocityY > 0.0F) {
                    a = 2000F;
                } else {
                    a = -2000F;
                }
            } else {
                a = velocityY;
            }
        }
        if (Math.abs(a) >= 0.0F && Math.abs(a) <= 20F) {
            loopView.cancelFuture();
            if (loopView.handler != null) {
                loopView.handler.sendEmptyMessage(2000);
            }
            return;
        }
        int i = (int) ((a * 10F) / 1000F);
        loopView.totalScrollY = loopView.totalScrollY - i;
        if (!loopView.isLoop) {
            float itemHeight = loopView.lineSpacingMultiplier * loopView.maxTextHeight;
            if (itemHeight != 0 && loopView.arrayList != null) {
                if (loopView.totalScrollY <= (int) ((float) (-loopView.initPosition) * itemHeight)) {
                    a = 40F;
                    loopView.totalScrollY = (int) ((float) (-loopView.initPosition) * itemHeight);
                } else if (loopView.totalScrollY >= (int) ((float) (loopView.arrayList.size() - 1 - loopView.initPosition) * itemHeight)) {
                    loopView.totalScrollY = (int) ((float) (loopView.arrayList.size() - 1 - loopView.initPosition) * itemHeight);
                    a = -40F;
                }
            }
        }
        if (a < 0.0F) {
            a = a + 20F;
        } else {
            a = a - 20F;
        }
        if (loopView.handler != null) {
            loopView.handler.sendEmptyMessage(1000);
        }
    }
}
