package woowacourse.shopping.presentation.productdetail

import woowacourse.shopping.domain.model.product.Product

data class ProductDetailUiState(
    val quantity: Int = 1,
    val lastViewedProduct: Product? = null,
)
