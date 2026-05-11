package woowacourse.shopping.productdetail

import woowacourse.shopping.productlist.ProductUiModel
import woowacourse.shopping.productlist.ViewedProductUiModel

data class DetailProductUiState(
    val productUiModel: ProductUiModel,
    val lastViewedProductUiModel: ViewedProductUiModel?,
)
