package woowacourse.shopping.data.repository.product

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.ProductHistoryDataSource
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.domain.repository.product.ProductRepository

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val productHistoryDataSource: ProductHistoryDataSource,
) : ProductRepository {
    override fun fetchSinglePage(page: Int): List<CartableProduct> {
        return productDataSource.fetchSinglePage(page)
    }

    override fun fetchProduct(id: Long): CartableProduct {
        return productDataSource.fetchProduct(id)
    }

    override fun addProductHistory(productHistory: ProductHistory) {
        return productHistoryDataSource.addProductHistory(productHistory)
    }

    override fun fetchProductHistory(size: Int): List<RecentProduct> {
        return productHistoryDataSource.fetchProductHistory(size)
    }

    override fun fetchLatestHistory(): RecentProduct? {
        return productHistoryDataSource.fetchLatestHistory()
    }
}
