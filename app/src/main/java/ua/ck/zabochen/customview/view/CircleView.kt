package ua.ck.zabochen.customview.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ua.ck.zabochen.customview.R

class CircleView(
    context: Context, attributeSet: AttributeSet
) : View(context, attributeSet) {

    private var attributes: TypedArray? = null

    private val paint = Paint()
    private var isCenter = false
    private var centerOfX = 0f
    private var centerOfY = 0f
    private var radius = 100f

    init {
        this.attributes = context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CircleView,
            0,
            0
        )

        this.paint.apply {
            style = Paint.Style.FILL

            // Color
            color = attributes
                ?.getColor(R.styleable.CircleView_backgroundColor, Color.RED)
                ?: Color.RED

            // Center Position
            isCenter = attributes
                ?.getBoolean(R.styleable.CircleView_align_center, false)
                ?: false

            // Radius
            radius = attributes
                ?.getDimension(R.styleable.CircleView_radius, 300f)
                ?: 300f
        }

        this.attributes?.recycle()
    }

    override fun onDraw(canvas: Canvas?) {

        if (isCenter) {
            centerOfX = (width / 2).toFloat()
            centerOfY = (height / 2).toFloat()
        }

        canvas?.drawCircle(centerOfX, centerOfY, radius, paint)
        super.onDraw(canvas)
    }
}