package woowacourse.shopping.database.recentviewed.repository

import model.Product
import model.RecentViewedProduct

interface RecentViewedProductRepository {

    fun addToRecentViewedProduct(id: Int)

    fun getRecentViewedProducts(): List<RecentViewedProduct>

    fun removeRecentViewedProduct()

    fun getRecentViewedProductById(id: Int): RecentViewedProduct

    fun getLatestViewedProduct(): Product?
}
