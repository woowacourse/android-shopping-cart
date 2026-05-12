package woowacourse.shopping.ui.productList

import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.product.Product
import kotlin.collections.associate

data class PagingState(
    val products: List<Product> = emptyList(),
    val currentPage: Int = 0,
    val canLoadMore: Boolean = true,
    val isLoading: Boolean = false,
    val loadError: Throwable? = null,
) {
    fun toUiState(cart: Cart, recents: List<Product>): ProductListUiState {
        loadError?.let { return ProductListUiState.Error.from(it) }
        if (products.isEmpty() && isLoading) return ProductListUiState.Loading

        val quantities = products.associate { it.id to cart.findQuantity(it.id).value }
        return ProductListUiState.Success(
            products = products,
            recentProducts = recents,
            quantitiesByProductId = quantities,
            canLoadMore = canLoadMore,
            isLoadingMore = isLoading,
            totalCartCount = cart.totalQuantity,
        )
    }
}
