package woowacourse.shopping.features.productDetail

import woowacourse.shopping.domain.product.model.Product

data class ProductDetailUiState(
    val productName: String = "",
    val productImageUrl: String = "",
    val productPrice: Int = 0,
    val quantity: Int = 1,
    val minusEnabled: Boolean = false,
    val latestProduct: Product? = null,
    val isLastRecentlyProduct: Boolean = true,
)
