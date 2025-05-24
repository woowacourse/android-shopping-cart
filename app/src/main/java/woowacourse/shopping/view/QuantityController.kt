package woowacourse.shopping.view

interface QuantityController {
    fun increaseQuantity(productId: Long)

    fun decreaseQuantity(productId: Long)

    fun updateQuantity()
}
