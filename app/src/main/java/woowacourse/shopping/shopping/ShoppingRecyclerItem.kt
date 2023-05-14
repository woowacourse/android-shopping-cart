package woowacourse.shopping.shopping

import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentViewedProductUiModel

sealed interface ShoppingRecyclerItem {

    class RecentViewedProducts(val values: List<RecentViewedProductUiModel>) : ShoppingRecyclerItem

    class ShoppingProduct(val value: ProductUiModel) : ShoppingRecyclerItem

    class ReadMoreDescription(val value: String) : ShoppingRecyclerItem
}
