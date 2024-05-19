package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ShoppingItemsRepository {
    fun findProductItem(id: Long): Product?

    fun findProductsByPage(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
