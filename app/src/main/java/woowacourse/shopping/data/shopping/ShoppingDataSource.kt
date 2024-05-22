package woowacourse.shopping.data.shopping

import woowacourse.shopping.domain.entity.Product

interface ShoppingDataSource {
    fun products(
        exceptProducts: List<Long>,
        amount: Int,
    ): List<Product>

    fun productById(id: Long): Product?

    fun canLoadMoreProducts(exceptProducts: List<Long>): Boolean
}
