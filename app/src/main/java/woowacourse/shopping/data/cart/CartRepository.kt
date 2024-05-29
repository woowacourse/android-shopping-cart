package woowacourse.shopping.data.cart

import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.CartItemQuantity
import woowacourse.shopping.model.Product

interface CartRepository {
    fun addProduct(productId: Long)

    fun deleteProduct(productId: Long)

    fun addCartItem(
        productId: Long,
        count: Int,
    )

    fun deleteCartItem(productId: Long)

    fun deleteAll()

    fun findAll(): List<CartItem>

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun find(productId: Long): CartItem?

    fun findQuantityOfCartItems(products: List<Product>): List<CartItemQuantity>

    fun count(): Int
}
