package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ShoppingItemsRepository {
    fun findProductsByPage(): List<Product>

    fun findProductById(id: Long): Product

    fun canLoadMore(): Boolean
}
