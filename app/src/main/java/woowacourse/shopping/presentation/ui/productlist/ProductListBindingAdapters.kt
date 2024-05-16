package woowacourse.shopping.presentation.ui.productlist

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("loadMoreBtnVisible")
fun TextView.binLoadMoreBtnVisible(last: Boolean?) {
    last?.let { value ->
        if (value) {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("emptyLoadMoreTextVisible")
fun TextView.bindEmptyLoadMoreTextVisible(last: Boolean?) {
    last?.let { value ->
        if (value) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }
}
