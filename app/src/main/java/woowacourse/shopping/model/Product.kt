package woowacourse.shopping.model

data class Product(
    val id: Long = 0,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val count: Int = DEFAULT_COUNT,
) {
    val totalPrice: Int = price * count

    operator fun inc(): Product {
        return this.copy(count = count + OFFSET)
    }

    operator fun dec(): Product {
        val newCount = (count - OFFSET).coerceAtLeast(DEFAULT_COUNT)
        return this.copy(count = newCount)
    }

    companion object {
        private const val DEFAULT_COUNT = 1
        private const val OFFSET = 1
    }
}
