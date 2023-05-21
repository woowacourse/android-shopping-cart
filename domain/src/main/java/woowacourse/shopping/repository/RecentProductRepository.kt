package woowacourse.shopping.repository

import woowacourse.shopping.Product

interface RecentProductRepository {

    fun addRecentProductId(recentProductId: Int)

    fun deleteRecentProductId(recentProductId: Int)
    fun getRecentProducts(size: Int): List<Product>
    fun getMostRecentProduct(): Product
}
