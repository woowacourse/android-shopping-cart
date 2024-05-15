package woowacourse.shopping.presentation.ui.shoppingcart

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Order

@BindingAdapter("recyclerViewVisible")
fun RecyclerView.bindRecyclerViewVisible(orderList: List<Order>?) {
    orderList?.let { items ->
        if (items.isEmpty()) {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("emptyTextVisible")
fun TextView.binEmptyTextVisible(orderList: List<Order>?) {
    orderList?.let { items ->
        if (items.isEmpty()) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }
}

@BindingAdapter("prePageBtnSelect")
fun TextView.bindPrePageBtnSelect(currentPage: Int?) {
    currentPage?.let { page ->
        this.isEnabled = page != 0
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
    if (currentPage == 0 && orderList.size < ShoppingCartViewModel.PAGE_SIZE) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}
