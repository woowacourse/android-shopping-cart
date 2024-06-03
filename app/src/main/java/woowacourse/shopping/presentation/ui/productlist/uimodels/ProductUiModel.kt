package woowacourse.shopping.presentation.ui.productlist.uimodels

import woowacourse.shopping.domain.model.Product

data class ProductUiModel(
    val product: Product,
    val quantity: Int,
)
