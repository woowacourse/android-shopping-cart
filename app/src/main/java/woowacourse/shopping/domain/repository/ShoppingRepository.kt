package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.entity.Product

interface ShoppingRepository {
    fun products(exceptProducts: List<Long>): List<Product>

    fun productById(id: Long): Product?

    fun canLoadMoreProducts(exceptProducts: List<Long>): Boolean
}
