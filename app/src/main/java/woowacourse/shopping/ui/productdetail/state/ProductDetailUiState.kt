package woowacourse.shopping.ui.productdetail.state

import woowacourse.shopping.ui.model.DetailProductUiModel
import woowacourse.shopping.ui.model.SimpleProductUiModel

data class ProductDetailUiState(
    val product: DetailProductUiModel? = null,
    val selectedQuantity: Int = 1,
    val totalPrice: String = "",
    val latestProduct: SimpleProductUiModel? = null,
)
