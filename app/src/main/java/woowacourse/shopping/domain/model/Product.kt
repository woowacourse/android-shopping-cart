package woowacourse.shopping.domain.model

data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
) {
    companion object {
        val EMPTY_PRODUCT = Product(0, "", "", 0)
    }
}
