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

@BindingAdapter("currentPage", "last")
fun TextView.bindPageNavigationTextVisible(
    currentPage: Int?,
    last: Boolean?,
) {
    if (currentPage == null || last == null) return
    if (currentPage == 0 && last == true) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}
