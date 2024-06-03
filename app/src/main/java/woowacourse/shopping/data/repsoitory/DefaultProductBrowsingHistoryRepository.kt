package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.datasource.productbrowsinghistory.LocalProductBrowsingHistory
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductBrowsingHistory
import woowacourse.shopping.domain.repository.ProductBrowsingHistoryRepository

class DefaultProductBrowsingHistoryRepository(
    private val localProductBrowsingHistory: LocalProductBrowsingHistory,
) : ProductBrowsingHistoryRepository {
    override fun putProductOnHistory(product: Product) =
        runOnOtherThread {
            val timestamp = System.currentTimeMillis()
            val productBrowsingHistory = ProductBrowsingHistory(product, timestamp)
            localProductBrowsingHistory.putHistory(productBrowsingHistory)
        }

    override fun getHistories(size: Int): Result<List<ProductBrowsingHistory>> =
        runCatching {
            runOnOtherThreadAndReturn {
                localProductBrowsingHistory.getHistories(size)
            }
        }
}
