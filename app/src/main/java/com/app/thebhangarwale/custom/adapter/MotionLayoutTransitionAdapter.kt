package com.app.thebhangarwale.custom.adapter

import androidx.constraintlayout.motion.widget.MotionLayout

open class MotionLayoutTransitionAdapter : MotionLayout.TransitionListener {
    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}
    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        startId: Int,
        endId: Int,
        progress: Float
    ) {}
    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {}
    override fun onTransitionTrigger(
        motionLayout: MotionLayout?,
        triggerId: Int,
        positive: Boolean,
        progress: Float
    ) {}
}