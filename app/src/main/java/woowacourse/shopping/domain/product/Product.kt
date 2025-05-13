package woowacourse.shopping.domain.product

data class Product(
    val imageUrl: String,
    val name: String,
    val price: Int
) {
    init {
        require(price >= 0) { ERROR_INVALID_PRICE }
    }

    companion object {
        private const val ERROR_INVALID_PRICE = "금액은 0 이상 이어야 합니다."
    }
}