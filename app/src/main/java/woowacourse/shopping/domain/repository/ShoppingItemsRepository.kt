package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ShoppingItemsRepository {
    fun fetchProductsSize(): Int

    fun fetchProductsWithIndex(
        start: Int = 0,
        end: Int,
    ): List<Product>

    fun getAllProducts(): List<Product>

    fun findProductItem(id: Long): Product?
}
