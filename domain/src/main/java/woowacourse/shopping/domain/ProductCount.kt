package woowacourse.shopping.domain

data class ProductCount(
    val value: Int,
    private val minCount: Int = EMPTY_COUNT,
) {
    operator fun plus(count: Int): ProductCount = copy(value = value + count)

    operator fun minus(count: Int): ProductCount =
        copy(value = (value - count).coerceAtLeast(minCount))

    operator fun plus(count: ProductCount): ProductCount =
        copy(value = value + count.value)

    operator fun minus(count: ProductCount): ProductCount =
        copy(value = (value - count.value).coerceAtLeast(minCount))

    companion object {
        private const val EMPTY_COUNT = 0
    }
}
