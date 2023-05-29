package woowacourse.shopping.repository

import woowacourse.shopping.CartProductInfo

interface CartRepository {
    fun putProductInCart(productId: Int)
    fun deleteCartProductId(productId: Int)
    fun updateCartProductCount(productId: Int, count: Int)

    fun getCartProductInfoById(
        productId: Int,
        onSuccess: (CartProductInfo?) -> Unit,
    )

    fun getCartProductsInfo(
        limit: Int,
        offset: Int,
        onSuccess: (List<CartProductInfo>) -> Unit,
    )

    fun getAllCartProductsInfo(
        onSuccess: (List<CartProductInfo>) -> Unit,
    )
}
