package woowacourse.shopping.presentation.ui.shoppingcart

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Order

@BindingAdapter("recyclerViewVisible")
fun RecyclerView.bindRecyclerViewVisible(orderList: List<Order>) {
    if (orderList.isEmpty()) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter("emptyTextVisible")
fun TextView.binEmptyTextVisible(orderList: List<Order>) {
    if (orderList.isEmpty()) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("prePageBtnSelect")
fun TextView.bindPrePageBtnSelect(currentPage: Int) {
    this.isEnabled = currentPage != 0
}

@BindingAdapter("nextPageBtnSelect")
fun TextView.bindNextPageBtnSelect(last: Boolean) {
    this.isEnabled = last != true
}
