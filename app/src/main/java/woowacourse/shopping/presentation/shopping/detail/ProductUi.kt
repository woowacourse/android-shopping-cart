package woowacourse.shopping.presentation.shopping.detail

data class ProductUi(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val count: Int = 0,
) {
    val isVisible get() = count > 0
}
