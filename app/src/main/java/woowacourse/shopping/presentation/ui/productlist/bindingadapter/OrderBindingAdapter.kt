package woowacourse.shopping.presentation.ui.productlist.bindingadapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.presentation.ui.productlist.uistates.OrderUiState

@BindingAdapter("hideAndShowOrderQuantity")
fun TextView.hideAndShowOrderQuantity(orderUiState: OrderUiState) {
    visibility = View.GONE
    if (orderUiState is OrderUiState.Success && orderUiState.orderSum > 0) {
        visibility = View.VISIBLE
    }
}

@BindingAdapter("setOrderQuantity")
fun TextView.setOrderQuantity(orderUiState: OrderUiState) {
    if (orderUiState is OrderUiState.Success && orderUiState.orderSum > 0) {
        text = orderUiState.orderSum.toString()
    }
}
