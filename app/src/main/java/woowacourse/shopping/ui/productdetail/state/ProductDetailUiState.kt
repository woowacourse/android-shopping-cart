package woowacourse.shopping.ui.productdetail.state

import woowacourse.shopping.ui.model.DetailProductUiModel
import woowacourse.shopping.ui.model.LatestProductUiModel

data class ProductDetailUiState(
    val product: DetailProductUiModel? = null,
    val selectedQuantity: Int = 1,
    val totalPrice: String = "",
    val latestProduct: LatestProductUiModel? = null,
    val isError: Boolean = false,
)
