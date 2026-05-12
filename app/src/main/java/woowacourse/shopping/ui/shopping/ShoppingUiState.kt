package woowacourse.shopping.ui.shopping

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import woowacourse.shopping.ui.model.ProductUiModel

data class ShoppingUiState(
    val products: ImmutableList<ProductUiModel> = persistentListOf(),
    val recentItems: ImmutableList<ProductUiModel> = persistentListOf(),
    val cartSize: Int = 0,
    val cartQuantities: Map<String, Int> = emptyMap(),
    val canLoadMore: Boolean = true,
    val isLoading: Boolean = false,
    val isNetworkAvailable: Boolean = true,
    val cartErrorMessage: String? = null,
)
