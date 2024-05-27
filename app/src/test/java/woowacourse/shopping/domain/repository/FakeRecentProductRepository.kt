package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.db.producthistory.ProductHistory

class FakeRecentProductRepository(
    private var productHistories: MutableList<ProductHistory>,
) : ProductHistoryRepository {
    override fun getProductHistories(): List<ProductHistory>? {
        return productHistories
    }

    override fun getMostRecentProductHistory(): ProductHistory? {
        return productHistories.last()
    }

    override fun setProductHistory(productId: Long) {
        productHistories.add(ProductHistory(6, productId))
    }
}
