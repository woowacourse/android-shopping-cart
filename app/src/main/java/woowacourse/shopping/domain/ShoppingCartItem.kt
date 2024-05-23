package woowacourse.shopping.domain

data class ShoppingCartItem(
    val product: Product,
    val quantity: Int = MIN_QUANTITY,
) {
    val totalPrice: Price = product.price.times(quantity)

    fun increaseQuantity(): QuantityUpdate = QuantityUpdate.Success(value = copy(quantity = quantity + COUNT_INTERVAL))

    fun decreaseQuantity(): QuantityUpdate {
        if (quantity <= MIN_QUANTITY) {
            return QuantityUpdate.Failure
        }
        return QuantityUpdate.Success(value = copy(quantity = quantity - COUNT_INTERVAL))
    }

    companion object {
        const val COUNT_INTERVAL = 1
        const val MIN_QUANTITY = 1
    }
}
