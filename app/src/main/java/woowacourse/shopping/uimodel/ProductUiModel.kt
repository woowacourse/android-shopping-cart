package woowacourse.shopping.uimodel

data class ProductUiModel(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val quantity: Int = PRODUCT_DEFAULT_QUANTITY,
) {
    companion object {
        const val PRODUCT_DEFAULT_QUANTITY = 0
    }
}
