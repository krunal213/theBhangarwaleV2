package com.app.thebhangarwale.custom.view;

import android.content.Context;
import android.util.AttributeSet;

import me.dkzwm.widget.srl.SmoothRefreshLayout;

public class BhangarwaleSmoothRefreshLayout extends SmoothRefreshLayout {

    public BhangarwaleSmoothRefreshLayout(Context context) {
        super(context);
    }

    public BhangarwaleSmoothRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BhangarwaleSmoothRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean autoRefresh(boolean atOnce) {
        return false;
    }
}
