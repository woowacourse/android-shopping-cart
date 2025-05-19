package woowacourse.shopping.view.cart.vm

import woowacourse.shopping.domain.product.Product

data class CartUiState(
    val products: List<Product> = emptyList(),
    val pageState: PageState = PageState(),
)
