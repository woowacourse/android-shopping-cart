package woowacourse.shopping.presentation.ui.productlist.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.presentation.common.hideIf
import woowacourse.shopping.presentation.common.showIf

@BindingAdapter("loadMoreBtnVisible")
fun TextView.binLoadMoreBtnVisible(last: Boolean?) {
    last?.let { value ->
        hideIf(value)
    }
}

@BindingAdapter("emptyLoadMoreTextVisible")
fun TextView.bindEmptyLoadMoreTextVisible(last: Boolean?) {
    last?.let { value ->
        showIf(value)
    }
}
