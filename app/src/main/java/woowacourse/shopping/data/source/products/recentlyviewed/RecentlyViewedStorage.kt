package woowacourse.shopping.data.source.products.recentlyviewed

import woowacourse.shopping.domain.Product

interface RecentlyViewedStorage {
    fun getRecentlyViewed(onResult: (List<Long>) -> Unit)

    fun getLatestViewed(onResult: (Long?) -> Unit)

    fun insert(product: Product)

    fun deleteOldestViewed()

    fun getCount(onResult: (Int) -> Unit)
}
