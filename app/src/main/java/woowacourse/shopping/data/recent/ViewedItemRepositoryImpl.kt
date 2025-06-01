package woowacourse.shopping.data.recent

import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.mapper.toViewedItem
import woowacourse.shopping.product.catalog.ProductUiModel
import kotlin.concurrent.thread

class ViewedItemRepositoryImpl(
    private val dao: ViewedItemDao,
) : ViewedItemRepository {
    override fun insertViewedItem(
        product: ProductUiModel,
        onComplete: () -> Unit,
    ) {
        thread {
            dao.insertViewedProduct(product.toViewedItem())
            onComplete()
        }
    }

    override fun getViewedItems(callback: (List<ProductUiModel>) -> Unit) {
        thread {
            val recentItems: List<ViewedItem> = dao.getRecentViewedItems()
            val uiModels = recentItems.map { it.toUiModel() }
            callback(uiModels)
        }
    }

    override fun getLastViewedItem(callback: (ProductUiModel?) -> Unit) {
        thread {
            val lastViewed = dao.getLastViewedItem()
            val uiModel = lastViewed?.toUiModel()
            callback(uiModel)
        }
    }
}
