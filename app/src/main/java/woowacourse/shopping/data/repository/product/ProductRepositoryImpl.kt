package woowacourse.shopping.data.repository.product

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.datasource.ProductHistoryDataSource
import woowacourse.shopping.data.model.history.ProductHistory
import woowacourse.shopping.data.model.history.RecentProduct
import woowacourse.shopping.data.model.product.CartableProduct
import woowacourse.shopping.data.model.product.Product
import woowacourse.shopping.domain.repository.product.ProductRepository
import java.lang.IllegalStateException
import kotlin.concurrent.thread

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val productHistoryDataSource: ProductHistoryDataSource,
) : ProductRepository {
    override fun fetchSinglePage(page: Int): List<CartableProduct> {
        var products = emptyList<CartableProduct>()
        thread {
            products = productDataSource.fetchSinglePage(page)
        }.join()
        return products
    }

    override fun fetchProduct(id: Long): CartableProduct {
        var product =
            CartableProduct(Product(name = "", imageSource = "", price = 0))
        thread {
            product = productDataSource.fetchProduct(id)
        }.join()
        return product
    }

    override fun addProductHistory(productHistory: ProductHistory) {
        thread {
            productHistoryDataSource.addProductHistory(productHistory)
        }.join()
    }

    override fun fetchProductHistory(size: Int): List<RecentProduct> {
        var products = emptyList<RecentProduct>()
        thread {
            products = productHistoryDataSource.fetchProductHistory(size)
        }.join()
        return products
    }

    override fun fetchLatestHistory(): RecentProduct? {
        var recentProduct: RecentProduct? = null
        thread {
            recentProduct = productHistoryDataSource.fetchLatestHistory()
        }.join()
        return recentProduct
    }
}
