package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("shrinkSizeCount")
fun View.shrinkSize(count: Int) {
}

@BindingAdapter("hideIfCountLessThanZero")
fun View.hideIfCountLessThanZero(count: Int) {
    if (count <= 0) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
    }
}

@BindingAdapter("showIfCountLessThanZero")
fun View.showIfCountLessThanZero(count: Int) {
    if (count <= 0) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}

/*
val animator: ValueAnimator = ValueAnimator.ofInt(measuredWidth, 0)
animator.duration = 10000
animator.addUpdateListener {
    val params = layoutParams
    params.width = it.getAnimatedValue() as Int
    layoutParams = params
}
if (count > 0) animator.reverse() else animator.start()
 */
