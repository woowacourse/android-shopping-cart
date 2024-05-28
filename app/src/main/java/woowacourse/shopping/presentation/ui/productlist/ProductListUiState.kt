package woowacourse.shopping.presentation.ui.productlist

import woowacourse.shopping.domain.model.Product

data class ProductListUiState(
    val pagingProduct: PagingProduct = PagingProduct(),
    val productHistorys: List<Product> = emptyList(),
    val cartCount: Int = 0,
) {
    val cartTotalCount: Int
        get() =
            if (cartCount >= MAX_CART_COUNT) {
                MAX_CART_COUNT
            } else {
                cartCount
            }

    companion object {
        const val MAX_CART_COUNT = 99
    }
}

data class PagingProduct(
    val productList: List<Product> = emptyList(),
    val last: Boolean = false,
)
