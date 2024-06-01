package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ProductBrowsingHistory
import woowacourse.shopping.domain.repository.ProductBrowsingHistoryRepository

object DummyHistory : ProductBrowsingHistoryRepository {
    private val histories: MutableList<ProductBrowsingHistory> = mutableListOf()

    override fun putProductOnHistory(product: Product) {
        val productBrowsingHistory = ProductBrowsingHistory(product, System.currentTimeMillis())
        histories.add(productBrowsingHistory)
    }

    override fun getHistories(size: Int): List<ProductBrowsingHistory> {
        val from = 0
        val to = if (size > histories.size) histories.size else size
        return histories.subList(from, to)
    }
}
