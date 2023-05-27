package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.presentation.model.CartProductModel

interface CartRepository {
    fun getCarts(startPosition: Int, cartItemCount: Int): List<CartProductModel>
    fun updateCartSelected(productId: Long, isSelected: Boolean)
    fun updateCartsSelected(productsId: List<Long>, isSelected: Boolean)
    fun deleteCartByProductId(productId: Long)
    fun insertCart(productId: Long, productCount: Int)
    fun getTotalPrice(): Int
}
