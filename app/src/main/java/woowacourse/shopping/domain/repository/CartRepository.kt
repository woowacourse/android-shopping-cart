package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.CartProducts

interface CartRepository {
    fun getCartProduct(
        productId: Int,
        callback: (CartProduct?) -> Unit,
    )

    fun getCartProducts(
        page: Int,
        size: Int,
        callback: (CartProducts) -> Unit,
    )

    fun increaseProductQuantity(
        productId: Int,
        quantity: Int = 1,
        callback: (Int) -> Unit,
    )

    fun decreaseProductQuantity(
        productId: Int,
        quantity: Int = 1,
        callback: (Int) -> Unit,
    )

    fun removeCartProduct(productId: Int)
}
