package woowacourse.shopping.domain

data class Product(
    val imageUri: String,
    val name: String,
    val price: Int,
) {
    init {
        require(imageUri.isNotBlank()) { "imageUri는 빈 값이 될 수 없습니다." }
        require(name.isNotBlank()) { "name은 빈 값이 될 수 없습니다." }
        require(price > 0) { "가격은 0원 초과여야 합니다." }
    }
}
