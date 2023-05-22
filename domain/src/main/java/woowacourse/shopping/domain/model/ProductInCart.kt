package woowacourse.shopping.domain.model

data class ProductInCart(
    val product: Product,
    val quantity: Int,
    val isChecked: Boolean = true,
) {
    init {
        require(quantity >= 0) { "수량은 음수일 수 없습니다. 현재 수량: $quantity" }
    }

    fun increase(): ProductInCart = ProductInCart(product, quantity + 1)

    fun decrease(): ProductInCart {
        val decreasedQuantity = if (quantity < 1) 0 else quantity - 1
        return ProductInCart(product, decreasedQuantity)
    }
}
