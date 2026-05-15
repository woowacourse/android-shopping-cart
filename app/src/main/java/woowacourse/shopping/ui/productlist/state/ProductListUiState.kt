package woowacourse.shopping.ui.productlist.state

import woowacourse.shopping.ui.model.DetailProductUiModel
import woowacourse.shopping.ui.model.SimpleProductUiModel

data class ProductListUiState(
    val products: List<DetailProductUiModel> = emptyList(),
    val recentProducts: List<SimpleProductUiModel> = emptyList(),
    val cartCount: Int = 0,
    val errorMessage: String? = null,
    val isEnd: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
