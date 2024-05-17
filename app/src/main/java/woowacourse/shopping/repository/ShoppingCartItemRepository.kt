package woowacourse.shopping.repository

import woowacourse.shopping.db.Product

interface ShoppingCartItemRepository {
    fun addCartItem(product: Product): Int

    fun findById(id: Int): Product

    fun loadPagedCartItems(page: Int): List<Product>

    fun removeCartItem(productId: Int): Product

    fun clearAllCartItems()

    fun isFinalPage(page: Int): Boolean
}
