package woowacourse.shopping.domain

data class Product(
    val id: String,
    val name: String,
    val price: Price,
    val imageUrl: String,
) {
    init {
        require(name.isNotBlank()) { "유효하지 않은 상품 이름입니다." }
    }
}
