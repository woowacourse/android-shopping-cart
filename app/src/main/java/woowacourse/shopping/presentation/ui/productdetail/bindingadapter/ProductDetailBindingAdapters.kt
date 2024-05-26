package woowacourse.shopping.presentation.ui.productdetail.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import woowacourse.shopping.domain.model.History
import woowacourse.shopping.domain.model.Product

@BindingAdapter(value = ["hideIfRecentHistory", "hideIfRecentProduct"], requireAll = true)
fun View.hideIfCountLessThanZero(
    hideIfRecentHistory: History?,
    hideIfRecentProduct: Product,
) {
    visibility =
        if (
            hideIfRecentHistory == null ||
            hideIfRecentHistory.product == hideIfRecentProduct
        ) {
            View.GONE
        } else {
            View.VISIBLE
        }
}
