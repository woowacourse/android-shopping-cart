package woowacourse.shopping.presentation.ui.productlist

import woowacourse.shopping.domain.model.Product

data class ProductListUiState(
    val pagingProduct: PagingProduct = PagingProduct(),
    val recentlyProductPosition: Int = 0,
)

data class PagingProduct(
    val productList: List<Product> = emptyList(),
    val last: Boolean = false,
)
