package woowacourse.shopping.data.recent

import woowacourse.shopping.mapper.toViewedItem
import woowacourse.shopping.product.catalog.ProductUiModel

class ViewedItemRepositoryImpl(
    private val dao: ViewedItemDao,
) : ViewedItemRepository {
    override fun insertViewedItem(product: ProductUiModel) {
        dao.insertViewedProduct(product.toViewedItem())
    }
}
