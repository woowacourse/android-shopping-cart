package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartEntity

interface CartDataSource {
    fun getCartProductCount(): Int

    fun getTotalQuantity(): Int?

    fun getCartProducts(): List<CartEntity>

    fun getCartItemById(productId: Long): CartEntity

    fun getQuantityById(productId: Long): Int

    fun getPagedCartProducts(
        limit: Int,
        page: Int,
    ): List<CartEntity>

    fun existsByProductId(productId: Long): Boolean

    fun increaseQuantity(
        productId: Long,
        quantity: Int,
    )

    fun decreaseQuantity(productId: Long)

    fun insertProduct(cartEntity: CartEntity)

    fun deleteProductById(productId: Long)
}
