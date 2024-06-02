package woowacourse.shopping.domain

data class ShoppingCartItem(
    val product: Product,
    val totalQuantity: Int = MIN_QUANTITY,
) {
    val totalPrice: Price = product.price.times(totalQuantity)

    fun increaseQuantity(): QuantityUpdate = QuantityUpdate.Success(value = copy(totalQuantity = totalQuantity + COUNT_INTERVAL))

    fun decreaseQuantity(): QuantityUpdate {
        if (totalQuantity <= MIN_QUANTITY) {
            return QuantityUpdate.CantChange
        }
        return QuantityUpdate.Success(value = copy(totalQuantity = totalQuantity - COUNT_INTERVAL))
    }

    companion object {
        const val COUNT_INTERVAL = 1
        const val MIN_QUANTITY = 1
    }
}
