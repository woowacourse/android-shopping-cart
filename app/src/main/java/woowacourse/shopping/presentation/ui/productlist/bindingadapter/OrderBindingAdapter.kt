package woowacourse.shopping.presentation.ui.productlist.bindingadapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.presentation.ui.productlist.uistates.OrderUiState

@BindingAdapter("showOrderQuantity")
fun TextView.showOrderQuantity(orderUiState: OrderUiState) {
    visibility = View.GONE
    if (orderUiState is OrderUiState.Success && orderUiState.orderSum > 0) {
        visibility = View.VISIBLE
        text = orderUiState.orderSum.toString()
    }
}
