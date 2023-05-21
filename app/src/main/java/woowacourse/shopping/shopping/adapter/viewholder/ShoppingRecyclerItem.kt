package woowacourse.shopping.shopping.adapter.viewholder

import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel

sealed interface ShoppingRecyclerItem {

    data class RecentViewedProducts(val values: List<RecentViewedProductUiModel>) :
        ShoppingRecyclerItem

    data class ShoppingProduct(val value: ProductUiModel) : ShoppingRecyclerItem

    data class ReadMoreDescription(val value: String = DEFAULT_DESCRIPTION) : ShoppingRecyclerItem

    companion object {
        private const val DEFAULT_DESCRIPTION = "더 보기"
    }
}
