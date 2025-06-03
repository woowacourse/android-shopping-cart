package woowacourse.shopping.data.repository.products.recentlyviewed

import woowacourse.shopping.data.source.products.catalog.ProductStorage
import woowacourse.shopping.data.source.products.recentlyviewed.RecentlyViewedStorage
import woowacourse.shopping.domain.Product

class RecentlyViewedRepositoryImpl(
    private val recentlyViewedStorage: RecentlyViewedStorage,
    private val productStorage: ProductStorage,
) : RecentlyViewedRepository {
    override fun getRecentlyViewed(onResult: (List<Product>) -> Unit) {
        recentlyViewedStorage.getRecentlyViewed { productIds ->
            val products = productIds.map { productStorage.getProduct(it) }
            onResult(products)
        }
    }

    override fun getLatestViewed(onResult: (Product?) -> Unit) {
        recentlyViewedStorage.getLatestViewed { productId ->
            if (productId == null) {
                onResult(null)
                return@getLatestViewed
            }
            val product = productStorage.getProduct(productId)
            onResult(product)
        }
    }

    override fun insert(product: Product) {
        recentlyViewedStorage.insert(product)
        recentlyViewedStorage.getCount { count ->
            if (count >= MAX_COUNT_RECENTLY_VIEWED_PRODUCTS) recentlyViewedStorage.deleteOldestViewed()
        }
    }

    companion object {
        private const val MAX_COUNT_RECENTLY_VIEWED_PRODUCTS: Int = 10
        private var instance: RecentlyViewedRepositoryImpl? = null

        @Synchronized
        fun initialize(
            recentlyViewedStorage: RecentlyViewedStorage,
            productStorage: ProductStorage,
        ) = instance ?: RecentlyViewedRepositoryImpl(
            recentlyViewedStorage,
            productStorage,
        ).also { instance = it }
    }
}
