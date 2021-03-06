package ua.ck.zabochen.customview.avatar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import ua.ck.zabochen.customview.R

class AvatarMaskImageView @JvmOverloads constructor(
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

    // Avatar
    private val avatarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private lateinit var avatarBitmap: Bitmap
    private val avatarRect = Rect()

    // Border
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        if (attributeSet != null) {
            val avatarTypedArray: TypedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.AvatarMaskImageView)

            // Get view attrs from xml
            with(avatarTypedArray) {

                borderWidth = getDimension(
                    R.styleable.AvatarMaskImageView_amiv_borderWidth,
                    context.dpToPx(DEFAULT_BORDER_WIDTH)
                )

                borderColor = getColor(
                    R.styleable.AvatarMaskImageView_amiv_borderColor,
                    DEFAULT_BORDER_COLOR
                )

                initials = getString(R.styleable.AvatarMaskImageView_amiv_initials) ?: DEFAULT_INITIALS
            }

            // Recycle res
            avatarTypedArray.recycle()
        }

        // Set default ScaleType
        scaleType = ScaleType.CENTER_CROP

        setup()
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
        if (w == 0) return
        with(avatarRect) {
            left = 0 // x
            top = 0 // y
            right = w // x
            bottom = h // y
        }
        prepareShader(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        //super.onDraw(canvas)
        canvas?.let {
            // Avatar
            it.drawOval(avatarRect.toRectF(), avatarPaint)

            // Border
            // Reduce rectangle horizontal and vertical
            val borderHalf: Int = borderWidth.toInt() / 2
            avatarRect.inset(borderHalf, borderHalf)
            it.drawOval(avatarRect.toRectF(), borderPaint)
        }
    }

    private fun setup() {
        // Border Paint
        with(borderPaint) {
            style = Paint.Style.STROKE
            color = borderColor
            strokeWidth = borderWidth
        }
    }

    private fun prepareShader(width: Int, height: Int) {
        this.avatarBitmap = drawable.toBitmap(width, height, Bitmap.Config.ARGB_8888)
        this.avatarPaint.shader = BitmapShader(avatarBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
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