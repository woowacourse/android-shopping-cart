package woowacourse.shopping.presentation.bindingadapter

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import woowacourse.shopping.R
import woowacourse.shopping.presentation.cart.CartClickHandler

@BindingAdapter("onClickPrevious")
fun setOnClickPrevious(
    view: View,
    handler: CartClickHandler?,
) {
    view.setOnClickListener { handler?.onClickPrevious() }
}

@BindingAdapter("onClickNext")
fun setOnClickNext(
    view: View,
    handler: CartClickHandler?,
) {
    view.setOnClickListener { handler?.onClickNext() }
}

@BindingAdapter("isEnabledCyanOrGray")
fun setCyanOrGrayTint(
    view: View,
    isEnabled: Boolean,
) {
    val colorRes = if (isEnabled) R.color.cyan else R.color.gray1
    view.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(view.context, colorRes))
}
