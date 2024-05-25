package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ShoppingProductsRepository {
    fun loadAllProducts(page: Int): List<Product>

    fun loadProduct(id: Int): Product

    fun isFinalPage(page: Int): Boolean
}
