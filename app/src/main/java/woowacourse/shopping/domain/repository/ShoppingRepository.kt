package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.entity.Product

interface ShoppingRepository {
    fun products(
        currentPage: Int,
        size: Int,
    ): Result<List<Product>>

    fun filterCarProducts(
        ids: List<Long>,
    ): Result<List<CartProduct>>

    fun productById(id: Long): Result<Product>

    fun canLoadMore(
        page: Int,
        size: Int,
    ): Result<Boolean>

    fun recentProducts(size: Int): Result<List<Product>>

    fun saveRecentProduct(product: Product): Result<Long>
}
