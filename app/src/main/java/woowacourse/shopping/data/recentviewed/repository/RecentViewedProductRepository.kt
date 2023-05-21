package woowacourse.shopping.data.recentviewed.repository

import model.Product
import model.RecentViewedProduct

interface RecentViewedProductRepository {

    fun addToRecentViewedProduct(id: Int)

    fun getRecentViewedProducts(): List<RecentViewedProduct>

    fun removeRecentViewedProduct()

    fun getRecentViewedProductById(id: Int): RecentViewedProduct

    fun getLatestViewedProduct(): Product?
}
