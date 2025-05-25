package woowacourse.shopping.data.recent

import woowacourse.shopping.product.catalog.ProductUiModel

interface ViewedItemRepository {
    fun insertViewedItem(product: ProductUiModel)
}
