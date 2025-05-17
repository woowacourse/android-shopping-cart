package woowacourse.shopping.presentation.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
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
