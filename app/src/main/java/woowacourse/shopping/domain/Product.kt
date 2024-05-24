package woowacourse.shopping.domain

data class Product(
    val id: Long,
    val price: Int,
    val name: String,
    val imageUrl: String,
    val count: Int = 0,
) {
    val isVisible get() = count > 0
}
