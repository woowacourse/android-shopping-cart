package woowacourse.shopping.view

interface QuantityController {
    fun increaseQuantity(
        productId: Long,
        quantityIncrease: Int = 1,
    )

    fun decreaseQuantity(
        productId: Long,
        quantityDecrease: Int = 1,
        minQuantity: Int = 1,
    )

    fun updateQuantity()
}
