package ua.ck.zabochen.customview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ua.ck.zabochen.customview.R

class CircleView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    private val paint = Paint()
    private val circleCenterOfX = 400f
    private val circleCenterOfY = 400f
    private val circleRadius = 300f

    init {
        paint.apply {
            style = Paint.Style.FILL
            color = ContextCompat.getColor(context, R.color.colorAccent)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(circleCenterOfX, circleCenterOfY, circleRadius, paint)
        super.onDraw(canvas)
    }
}