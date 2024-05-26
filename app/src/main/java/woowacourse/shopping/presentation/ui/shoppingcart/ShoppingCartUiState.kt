package woowacourse.shopping.presentation.ui.shoppingcart

import woowacourse.shopping.domain.model.Product

data class ShoppingCartUiState(
    val pagingCartProduct: PagingCartProduct = PagingCartProduct(),
)

data class PagingCartProduct(
    val products: List<Product> = emptyList(),
    val currentPage: Int = 0,
    val last: Boolean = true,
)
