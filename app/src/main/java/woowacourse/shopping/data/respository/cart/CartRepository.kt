package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.presentation.model.CartModel

interface CartRepository {
    fun getCarts(startPosition: Int, cartItemCount: Int): List<CartModel>
    fun deleteCartByProductId(productId: Long)
    fun addCart(productId: Long)
}
