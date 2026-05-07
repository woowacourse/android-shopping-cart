package woowacourse.shopping.domain

@JvmInline
value class Quantity(val count: Int) {
    init {
        require(count > 0) { "수량은 1보다 작을 수 없습니다. 수량 : $count" }
    }

    operator fun plus(other: Quantity): Quantity = Quantity(this.count + other.count)

    operator fun minus(other: Quantity): Quantity = Quantity(this.count - other.count)

    fun isLessThan(other: Quantity): Boolean = count < other.count
}
