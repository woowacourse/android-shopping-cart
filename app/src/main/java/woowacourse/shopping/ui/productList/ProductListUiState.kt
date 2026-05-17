package woowacourse.shopping.ui.productList

import woowacourse.shopping.domain.product.Product
import java.io.IOException

sealed interface ProductListUiState {
    data object Loading : ProductListUiState

    data class Success(
        val products: List<Product>,
        val recentProducts: List<Product> = emptyList(),
        val quantitiesByProductId: Map<Int, Int> = emptyMap(),
        val canLoadMore: Boolean,
        val isLoadingMore: Boolean = false,
        val totalCartCount: Int = 0,
    ) : ProductListUiState

    data class Error(
        val message: String,
    ) : ProductListUiState {
        companion object {
            fun from(throwable: Throwable): Error =
                if (throwable is IOException) {
                    Error(message = "네트워크 연결을 확인해주세요.")
                } else {
                    Error(message = throwable.message ?: "상품 목록을 불러오지 못했어요.")
                }
        }
    }
}
