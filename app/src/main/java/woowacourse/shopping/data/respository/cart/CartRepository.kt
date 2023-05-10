package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.presentation.model.CartModel

interface CartRepository {
    fun getCarts(): List<CartModel>
    fun deleteCartByProductId(productId: Long)
    fun addCart(productId: Long)
}
