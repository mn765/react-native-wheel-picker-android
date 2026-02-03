package com.wheelpicker;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

// Referenced classes of package com.wheelpicker:
//            LoopView

final class MessageHandler extends Handler {

    private final WeakReference<LoopView> loopViewRef;

    MessageHandler(LoopView loopview) {
        super(Looper.getMainLooper());
        this.loopViewRef = new WeakReference<>(loopview);
    }

    @Override
    public final void handleMessage(Message paramMessage) {
        LoopView loopView = loopViewRef.get();
        if (loopView == null) {
            return;
        }
        
        switch (paramMessage.what) {
            case 1000:
                loopView.invalidate();
                break;
            case 2000:
                LoopView.smoothScroll(loopView);
                break;
            case 3000:
                loopView.itemSelected();
                break;
        }
        super.handleMessage(paramMessage);
    }

}
