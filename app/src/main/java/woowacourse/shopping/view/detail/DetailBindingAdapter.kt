package woowacourse.shopping.view.detail

import android.view.View
import androidx.databinding.BindingAdapter
import woowacourse.shopping.domain.model.Product

@BindingAdapter("app:recentViewedProductId", "app:productId")
fun setVisibility(
    view: View,
    recentProductId: Long,
    productId: Long,
) {
    view.visibility =
        if (recentProductId == Product.INVALID_PRODUCT.id || recentProductId == productId) View.GONE else View.VISIBLE
}
