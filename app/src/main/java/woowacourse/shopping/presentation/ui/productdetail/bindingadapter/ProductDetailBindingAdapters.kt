package woowacourse.shopping.presentation.ui.productdetail.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductBrowsingHistory

@BindingAdapter(value = ["hideIfRecentHistory", "hideIfRecentProduct"], requireAll = true)
fun View.hideIfRecent(
    hideIfRecentProductBrowsingHistory: ProductBrowsingHistory?,
    hideIfRecentProduct: Product,
) {
    visibility =
        if (
            hideIfRecentProductBrowsingHistory == null ||
            hideIfRecentProductBrowsingHistory.product == hideIfRecentProduct
        ) {
            View.GONE
        } else {
            View.VISIBLE
        }
}
