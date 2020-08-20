package ua.ck.zabochen.customview.avatar

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView

class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {

    companion object {
        private const val DEFAULT_SIZE = 100 // dp
    }

    // Many times
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.i(
            "AvatarImageView",
            "onMeasure: W: ${MeasureSpec.toString(widthMeasureSpec)}, H: ${MeasureSpec.toString(heightMeasureSpec)}"
        )

        // In our case: width = height
        val avatarSize = resolveViewSize(widthMeasureSpec)
        setMeasuredDimension(avatarSize, avatarSize)

        Log.i("AvatarImageView", "onMeasure: avatarSize: $avatarSize")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i("AvatarImageView", "onSizeChanged: $w, $h")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("AvatarImageView", "onDraw: ")
    }

    private fun resolveViewSize(measureSpec: Int): Int {
        return when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(measureSpec)
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(measureSpec)
            MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt()
            else -> MeasureSpec.getSize(measureSpec)
        }
    }
}