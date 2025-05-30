package woowacourse.shopping.data.repository

import woowacourse.shopping.data.entity.CartProductEntity

interface CartProductRepository {
    fun insertCartProduct(
        cartProduct: CartProductEntity,
        callback: (Unit) -> Unit,
    )

    fun deleteCartProduct(
        productId: Int,
        callback: (Unit) -> Unit,
    )

    fun getCartProductsInRange(
        startIndex: Int,
        endIndex: Int,
        callback: (List<CartProductEntity>) -> Unit,
    )

    fun updateProductQuantity(
        productId: Int,
        diff: Int,
        callback: (Unit) -> Unit,
    )

    fun getProduct(
        id: Int,
        callback: (CartProductEntity) -> Unit,
    )

    fun getProductQuantity(
        id: Int,
        callback: (Int?) -> Unit,
    )

    fun getAllProductsSize(callback: (Int) -> Unit)

    fun getCartItemSize(callback: (Int) -> Unit)
}
