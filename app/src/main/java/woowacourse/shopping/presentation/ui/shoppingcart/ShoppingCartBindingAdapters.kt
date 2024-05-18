package woowacourse.shopping.presentation.ui.shoppingcart

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

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
