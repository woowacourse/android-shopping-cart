package woowacourse.shopping.repository

import woowacourse.shopping.data.Product

interface ShoppingProductsRepository {
    fun loadPagedItems(page: Int): List<Product>

    fun findById(findId: Int): Product

    fun isFinalPage(page: Int): Boolean
}
