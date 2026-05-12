package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.Product

data class ShoppingUiState(
    val products: List<ShoppingProductUiState> = emptyList(),
    val recentProducts: List<Product> = emptyList(),
    val cartQuantity: Int = 0,
    val hasNext: Boolean = false,
    val isLoading: Boolean = false,
    val isNetworkConnected: Boolean = true,
    val errorMessage: String? = null,
)
