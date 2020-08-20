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

    private val avatarPaint = Paint()
    private val avatarRect = Rect()

    private lateinit var srcBitmap: Bitmap
    private lateinit var maskBitmap: Bitmap
    private lateinit var resultBitmap: Bitmap

    init {
        if (attributeSet != null) {
            val avatarTypedArray: TypedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.AvatarImageView)

            // Get view attrs from xml
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
            setup()
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
        if (w == 0) return
        with(avatarRect) {
            left = 0 // x
            top = 0 // y
            right = w // x
            bottom = h // y
        }
        prepareBitmaps(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        //super.onDraw(canvas)
        canvas?.drawBitmap(resultBitmap, avatarRect, avatarRect, null)
    }

    private fun setup() {
        with(avatarPaint) {
            color = Color.RED
            style = Paint.Style.FILL
        }
    }

    private fun prepareBitmaps(width: Int, height: Int) {
        // For mask we use only alpha channel
        this.maskBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)

        // For result we use alpha channel with all colors
        this.resultBitmap = maskBitmap.copy(Bitmap.Config.ARGB_8888, true)

        // Image scr drawable to bitMap
        this.srcBitmap = drawable.toBitmap(width, height, Bitmap.Config.ARGB_8888)

        // Edit maskBitmap
        Canvas(maskBitmap).also {
            it.drawOval(avatarRect.toRectF(), avatarPaint)
        }

        // Edit resultBitmap
        Canvas(resultBitmap).also {
            // DST
            it.drawBitmap(maskBitmap, avatarRect, avatarRect, null)

            // SRC
            val paintXfer = Paint()
            paintXfer.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            it.drawBitmap(srcBitmap, avatarRect, avatarRect, paintXfer)
        }
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