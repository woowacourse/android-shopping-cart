package woowacourse.shopping.model

data class ProductWithQuantity(
    val product: Product,
    val quantity: Quantity = Quantity(),
) {
    val totalPrice: Int = product.price * quantity.value

    operator fun inc(): ProductWithQuantity {
        return this.copy(quantity = quantity.inc())
    }

    operator fun dec(): ProductWithQuantity {
        return this.copy(quantity = quantity.dec())
    }
}
