package woowacourse.shopping.domain

data class Cart(val product: Product, val quantity: Int) {
    init {
        require(quantity >= 0) { "장바구니 개수는 마이너스가 될 수 없습니다." }
    }

    val totalPrice = quantity * product.price.value

    fun increase() = Cart(product, quantity + 1)

    fun decrease() = Cart(product, (quantity - 1).coerceAtLeast(1))
}
