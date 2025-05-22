package woowacourse.shopping.data.repository

import woowacourse.shopping.data.entity.CartProductEntity

interface CartProductRepository {
    fun insertCartProduct(cartProduct: CartProductEntity)

    fun deleteCartProduct(cartProduct: CartProductEntity)

    fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<CartProductEntity>

    fun updateProduct(product: CartProductEntity)

    fun getAllProductsSize(callback: (Int) -> Unit)

    fun getCartItemSize(callback: (Int) -> Unit)
}
