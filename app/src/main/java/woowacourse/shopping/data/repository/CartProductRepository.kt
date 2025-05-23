package woowacourse.shopping.data.repository

import woowacourse.shopping.data.entity.CartProductEntity

interface CartProductRepository {
    fun insertCartProduct(cartProduct: CartProductEntity)

    fun deleteCartProduct(cartProduct: CartProductEntity)

    fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
        callback: (List<CartProductEntity>) -> Unit,
    )

    fun updateProduct(
        cartProduct: CartProductEntity,
        diff: Int,
        callback: (CartProductEntity?) -> Unit,
    )

    fun getProductQuantity(
        id: Int,
        callback: (Int?) -> Unit,
    )

    fun getAllProductsSize(callback: (Int) -> Unit)

    fun getCartItemSize(callback: (Int) -> Unit)
}
