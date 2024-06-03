package woowacourse.shopping.presentation.ui.shoppingcart.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Order
import woowacourse.shopping.presentation.common.hideIf
import woowacourse.shopping.presentation.common.showIf
import woowacourse.shopping.presentation.ui.shoppingcart.ShoppingCartViewModel

@BindingAdapter("recyclerViewVisible")
fun RecyclerView.bindRecyclerViewVisible(orderList: List<Order>?) {
    orderList?.let { items ->
        hideIf(items.isEmpty())
    }
}

@BindingAdapter("emptyTextVisible")
fun TextView.binEmptyTextVisible(orderList: List<Order>?) {
    orderList?.let { items ->
        showIf(items.isEmpty())
    }
}

@BindingAdapter("prePageBtnSelect")
fun TextView.bindPrePageBtnSelect(first: Boolean?) {
    first?.let { value ->
        this.isEnabled = value != true
    }
}

@BindingAdapter("nextPageBtnSelect")
fun TextView.bindNextPageBtnSelect(last: Boolean?) {
    last?.let { value ->
        this.isEnabled = value != true
    }
}

@BindingAdapter("currentPage", "orderList")
fun TextView.bindPageNavigationTextVisible(
    currentPage: Int?,
    orderList: List<Order>?,
) {
    if (currentPage == null || orderList == null) return
    hideIf(currentPage == 0 && orderList.size < ShoppingCartViewModel.PAGE_SIZE)
}
