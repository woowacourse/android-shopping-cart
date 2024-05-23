package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.entity.Product

interface ShoppingRepository {
    fun products(
        currentPage: Int,
        size: Int,
    ): Result<List<Product>>

    fun productById(id: Long): Result<Product>

    fun canLoadMore(
        page: Int,
        size: Int,
    ): Result<Boolean>

    fun recentProducts(size: Int): Result<List<Product>>

    fun saveRecentProduct(id: Long): Result<Long>
}
