package woowacourse.shopping.features.productDetail

data class ProductDetailUiState(
    val productPrice: Int = 0,
    val quantity: Int = 1,
    val minusEnabled: Boolean = false
)
