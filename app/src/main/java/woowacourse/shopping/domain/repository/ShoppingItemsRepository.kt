package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ShoppingItemsRepository {
    fun getAllProducts(): List<Product>

    fun findProductItem(id: Long): Product?

    fun findProductsByPage(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
