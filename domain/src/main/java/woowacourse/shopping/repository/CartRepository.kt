package woowacourse.shopping.repository

import woowacourse.shopping.CartProductInfo
import woowacourse.shopping.CartProductInfoList

interface CartRepository {
    fun putProductInCart(productId: Int)
    fun deleteCartProductId(productId: Int)

    fun getCartProductInfoById(productId: Int): CartProductInfo?
    fun getCartProductsInfo(limit: Int, offset: Int): CartProductInfoList
    fun updateCartProductCount(productId: Int, count: Int)
    fun getAllCartProductsInfo(): CartProductInfoList
}
