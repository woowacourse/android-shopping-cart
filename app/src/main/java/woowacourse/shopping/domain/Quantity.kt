package woowacourse.shopping.domain

@JvmInline
value class Quantity(private val quantity: Int) {
    init {
        require(quantity > 0) { "수량은 1보다 작을 수 없습니다. 수량 : $quantity" }
    }

    operator fun plus(other: Quantity): Quantity = Quantity(this.quantity + other.quantity)
}
