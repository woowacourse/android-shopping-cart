package woowacourse.shopping.domain

data class ProductCount(val value: Int) {
    operator fun plus(count: Int): ProductCount = copy(value = value + count)

    operator fun minus(count: Int): ProductCount =
        copy(value = (value - count).coerceAtLeast(EMPTY_COUNT))

    operator fun plus(count: ProductCount): ProductCount =
        copy(value = value + count.value)

    operator fun minus(count: ProductCount): ProductCount =
        copy(value = (value - count.value).coerceAtLeast(EMPTY_COUNT))

    fun isZero(): Boolean = value == EMPTY_COUNT

    companion object {
        private const val EMPTY_COUNT = 0
    }
}
