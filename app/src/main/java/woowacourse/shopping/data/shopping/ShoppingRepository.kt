package woowacourse.shopping.data.shopping

import woowacourse.shopping.domain.Product

interface ShoppingRepository {
    fun products(exceptProducts: List<Long>): List<Product>

    fun productById(id: Long): Product?

    fun canLoadMoreProducts(exceptProducts: List<Long>): Boolean
}

