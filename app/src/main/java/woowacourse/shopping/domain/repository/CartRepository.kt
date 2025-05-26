package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartProduct

interface CartRepository {
    fun fetchCartProductDetail(productId: Int): CartProduct?

    fun fetchCartProducts(
        page: Int,
        size: Int,
    ): List<CartProduct>

    fun fetchCartItemCount(): Int

    fun saveCartProduct(
        productId: Int,
        quantity: Int,
    )

    fun deleteCartProduct(productId: Int)
}
