package woowacourse.shopping.presentation.ui.productlist

import woowacourse.shopping.domain.model.Product

data class ProductListUiState(
    val pagingProduct: PagingProduct = PagingProduct(),
    val recentlyProductPosition: Int = 0,
    val cartCount: Int = 0,
) {
    fun checkCartCount(count: Int): Int {
        return if (cartCount + count >= MAX_CART_COUNT) {
            MAX_CART_COUNT
        } else {
            cartCount + count
        }
    }

    companion object {
        const val MAX_CART_COUNT = 99
    }
}

data class PagingProduct(
    val productList: List<Product> = emptyList(),
    val last: Boolean = false,
)
