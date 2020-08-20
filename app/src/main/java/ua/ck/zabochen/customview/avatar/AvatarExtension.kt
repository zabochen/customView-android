package ua.ck.zabochen.customview.avatar

import android.content.Context

fun Context.dpToPx(dp: Int): Float {
    return dp.toFloat() * resources.displayMetrics.density
}