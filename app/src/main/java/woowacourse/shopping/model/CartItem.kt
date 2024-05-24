package woowacourse.shopping.model

import woowacourse.shopping.db.model.CartItemEntity

data class CartItem(
    val productId: Int,
    var quantity: Int
) {
    fun toCartItemEntity(): CartItemEntity {
        return CartItemEntity(
            productId = this.productId,
            quantity = this.quantity
        )
    }
}
