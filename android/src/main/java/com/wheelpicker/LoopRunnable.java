package com.wheelpicker;

import java.lang.ref.WeakReference;

final class LoopRunnable implements Runnable {

    private final WeakReference<LoopView> loopViewRef;

    LoopRunnable(LoopView loopview) {
        super();
        loopViewRef = new WeakReference<>(loopview);

    }

    @Override
    public final void run() {
        LoopView loopView = loopViewRef.get();
        if (loopView == null || loopView.loopListener == null || loopView.arrayList == null) {
            return;
        }
        
        int selectedItem = LoopView.getSelectedItem(loopView);
        if (selectedItem >= 0 && selectedItem < loopView.arrayList.size()) {
            loopView.arrayList.get(selectedItem);
            loopView.loopListener.onItemSelect(loopView, selectedItem);
        }
    }
}
