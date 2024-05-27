package woowacourse.shopping.ui.cart

interface CartListener {
    fun deleteCartItem(productId: Long)

    fun increaseQuantity(productId: Long)

    fun decreaseQuantity(productId: Long)
}
