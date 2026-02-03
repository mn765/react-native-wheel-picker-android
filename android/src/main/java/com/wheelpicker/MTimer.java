package com.wheelpicker;

import java.lang.ref.WeakReference;
import java.util.TimerTask;

final class MTimer extends TimerTask {

    int realTotalOffset;
    int realOffset;
    int offset;
    private final WeakReference<LoopView> loopViewRef;

    MTimer(LoopView loopview, int offset) {
        super();
        this.loopViewRef = new WeakReference<>(loopview);
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public final void run() {
        LoopView loopView = loopViewRef.get();
        if (loopView == null) {
            return;
        }
        
        if (realTotalOffset == Integer.MAX_VALUE) {
            float itemHeight = loopView.lineSpacingMultiplier * loopView.maxTextHeight;
            if (itemHeight == 0) {
                return; // Prevent divide-by-zero
            }
            offset = (int)((offset + itemHeight) % itemHeight);
            if ((float) offset > itemHeight / 2.0F) {
                realTotalOffset = (int) (itemHeight - (float) offset);
            } else {
                realTotalOffset = -offset;
            }
        }
        realOffset = (int) ((float) realTotalOffset * 0.1F);

        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }
        if (Math.abs(realTotalOffset) <= 0) {
            loopView.cancelFuture();
            if (loopView.handler != null) {
                loopView.handler.sendEmptyMessage(3000);
            }
            return;
        } else {
            loopView.totalScrollY = loopView.totalScrollY + realOffset;
            if (loopView.handler != null) {
                loopView.handler.sendEmptyMessage(1000);
            }
            realTotalOffset = realTotalOffset - realOffset;
            return;
        }
    }
}