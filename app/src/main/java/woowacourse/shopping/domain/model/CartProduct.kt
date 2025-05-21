package woowacourse.shopping.domain.model

data class CartProduct(
    val product: Product,
    val quantity: Int,
) {
    val totalPrice: Int get() = product.price * quantity

    companion object {
        fun from(
            product: Product,
            quantity: Int,
        ): CartProduct = CartProduct(product, quantity)
    }
}
