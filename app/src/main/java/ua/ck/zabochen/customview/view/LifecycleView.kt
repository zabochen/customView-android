package ua.ck.zabochen.customview.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View

class LifecycleView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    // Called when the view is attached to a window
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i("LifecycleView", "onAttachedToWindow")
    }

    // Called to determine the size requirements for this view and all of its children
    // Called many times
    // Each View tries to calculate its dimensions specifications down the tree(View hierarchy)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i("LifecycleView", "onMeasure")
        Log.i("LifecycleView", "onMeasure => W: $widthMeasureSpec, H: $heightMeasureSpec")
    }

    // Called when this view should assign a size and position to all of its children
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.i("LifecycleView", "onLayout")
        Log.i("LifecycleView", "onLayout => L: $left, T: $top, R: $right, B: $bottom")
    }

    // Called when the view should render its content.
    // It provides canvas as an argument, we draw anything on canvas using Paint class Instance.
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("LifecycleView", "onDraw")
    }

    // Called when the view is detached from a window
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i("LifecycleView", "onDetachedFromWindow")
    }
}