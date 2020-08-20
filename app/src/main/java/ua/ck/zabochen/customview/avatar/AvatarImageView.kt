package ua.ck.zabochen.customview.avatar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageView
import ua.ck.zabochen.customview.R

class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defStyleAttr) {

    companion object {
        private const val DEFAULT_AVATAR_SIZE: Int = 100 // dp
        private const val DEFAULT_BORDER_COLOR: Int = Color.GREEN
        private const val DEFAULT_BORDER_WIDTH: Int = 3 // dp
        private const val DEFAULT_INITIALS: String = "??"
    }

    @Px
    private var borderWidth: Float = context.dpToPx(DEFAULT_BORDER_WIDTH)

    @ColorInt
    private var borderColor: Int = DEFAULT_BORDER_COLOR

    private var initials: String = DEFAULT_INITIALS

    init {
        if (attributeSet != null) {
            val avatarTypedArray: TypedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.AvatarImageView)

            with(avatarTypedArray) {

                borderWidth = getDimension(
                    R.styleable.AvatarImageView_aiv_borderWidth,
                    context.dpToPx(DEFAULT_BORDER_WIDTH)
                )

                borderColor = getColor(
                    R.styleable.AvatarImageView_aiv_borderColor,
                    DEFAULT_BORDER_COLOR
                )

                initials = getString(R.styleable.AvatarImageView_aiv_initials) ?: DEFAULT_INITIALS

                // Set default ScaleType
                scaleType = ScaleType.CENTER_CROP
            }
        }
    }

    // Many times
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // In our case: width = height
        val avatarSize = resolveViewSize(widthMeasureSpec)
        setMeasuredDimension(avatarSize, avatarSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    private fun resolveViewSize(measureSpec: Int): Int {
        return when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(measureSpec)
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(measureSpec)
            MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_AVATAR_SIZE).toInt()
            else -> MeasureSpec.getSize(measureSpec)
        }
    }
}