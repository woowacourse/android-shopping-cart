package woowacourse.shopping

import woowacourse.shopping.domain.Product

interface RecentlyViewedProductRepositoryInterface {
    fun recentTenProducts(): List<Product>

    fun lastlyViewedProduct(): Product?

    fun addRecentlyViewedProduct(product: Product)
}
