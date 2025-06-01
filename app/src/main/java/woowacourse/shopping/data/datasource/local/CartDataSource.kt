package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.entity.CartEntity

interface CartDataSource {
    fun getCartProductCount(): Int

    fun getTotalQuantity(): Int?

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
