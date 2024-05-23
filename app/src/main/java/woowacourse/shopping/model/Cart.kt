package woowacourse.shopping.model

data class Cart(
    val id: Long = 0,
    val productId: Long,
    val quantity: Quantity = Quantity(),
) {
    operator fun inc(): Cart {
        return this.copy(quantity = quantity.inc())
    }

    operator fun dec(): Cart {
        return this.copy(quantity = quantity.dec())
    }
}
