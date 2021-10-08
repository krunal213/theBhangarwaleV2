package com.app.thebhangarwale.custom.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.app.thebhangarwale.R
import me.dkzwm.widget.srl.SmoothRefreshLayout
import me.dkzwm.widget.srl.extra.IRefreshView
import me.dkzwm.widget.srl.indicator.IIndicator

class BhangarwaleSmoothRefreshLayoutHeader<T : IIndicator?> @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), IRefreshView<T> {
    protected var mProgressBar: View
    override fun getType(): Int {
        return IRefreshView.TYPE_HEADER.toInt()
    }

    override fun getStyle(): Int {
        return IRefreshView.STYLE_FOLLOW_PIN.toInt()
    }

    override fun getCustomHeight(): Int {
        return 0
    }

    override fun getView(): View {
        return this
    }

    override fun onFingerUp(layout: SmoothRefreshLayout, indicator: T) {}
    override fun onReset(layout: SmoothRefreshLayout) {}
    override fun onRefreshPrepare(layout: SmoothRefreshLayout) {}
    override fun onRefreshBegin(layout: SmoothRefreshLayout, indicator: T) {}
    override fun onRefreshComplete(
        layout: SmoothRefreshLayout,
        isSuccessful: Boolean
    ) {
    }

    override fun onRefreshPositionChanged(
        layout: SmoothRefreshLayout,
        status: Byte,
        indicator: T
    ) {
    }

    override fun onPureScrollPositionChanged(
        layout: SmoothRefreshLayout,
        status: Byte,
        indicator: T
    ) {
    }

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_smooth_refresh_header, this, false) as LinearLayout
        view.setBackgroundColor(resources.getColor(R.color.color_window_background))
        this.addView(view)
        mProgressBar = view.findViewById(R.id.sr_classic_progress)
    }
}