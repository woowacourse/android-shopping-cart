package woowacourse.shopping.data.products

import woowacourse.shopping.model.cart.CartItem

interface ProductRepository {
    fun getAll(): List<CartItem>

    fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<CartItem>
}
