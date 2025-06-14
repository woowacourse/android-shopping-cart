package woowacourse.shopping.data.repository.products.recentlyviewed

import woowacourse.shopping.domain.Product

interface RecentlyViewedRepository {
    fun getRecentlyViewed(onResult: (List<Product>) -> Unit)

    fun getLatestViewed(onResult: (Product?) -> Unit)

    fun insert(product: Product)
}
