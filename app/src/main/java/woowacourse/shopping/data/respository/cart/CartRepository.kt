package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.data.model.CartEntity

interface CartRepository {
    fun getCarts(): List<CartEntity>
    fun deleteCartByProductId(productId: Long)
    fun addCart(productId: Long)
}
