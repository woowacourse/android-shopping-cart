package woowacourse.shopping.data.cart

import woowacourse.shopping.model.CartProduct
import woowacourse.shopping.model.Product
import woowacourse.shopping.presentation.model.CartProductModel

interface CartRepository {
    fun insertCartProduct(productId: Long, count: Int)
    fun updateCartProductCount(cartId: Long, count: Int)
    fun deleteCartProduct(cartId: Long)
    fun getCartProducts(): List<CartProduct>
    fun getCartProduct(productId: Long): CartProduct
    fun getProductsByRange(startIndex: Int, size: Int): List<CartProductModel>
    fun findProductById(productId: Long): Product
}
