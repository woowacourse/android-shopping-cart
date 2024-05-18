package woowacourse.shopping.presentation.ui.cart

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import woowacourse.shopping.presentation.ui.UiState
import woowacourse.shopping.presentation.ui.cart.CartActivity.Companion.OFFSET_BASE

@BindingAdapter("cartPagingIsVisible")
fun View.setCartPagingVisible(viewModel: CartViewModel) {
    if (viewModel.carts.value is UiState.Success) this.isVisible = viewModel.maxOffset > 0
}

@BindingAdapter("cartRightBtnIsEnable")
fun Button.setCartRightBtnIsEnable(viewModel: CartViewModel) {
    if (viewModel.carts.value is UiState.Success) this.isEnabled = viewModel.offSet < viewModel.maxOffset
}

@BindingAdapter("cartLeftBtnIsEnable")
fun Button.setCartLeftBtnIsEnable(viewModel: CartViewModel) {
    if (viewModel.carts.value is UiState.Success) this.isEnabled = viewModel.offSet > 0
}

@BindingAdapter("cartPageCount")
fun TextView.setCartPageCount(viewModel: CartViewModel) {
    if (viewModel.carts.value is UiState.Success) this.text = (viewModel.offSet + OFFSET_BASE).toString()
}
