package woowacourse.shopping.ui.cart.state

import woowacourse.shopping.ui.model.DetailProductUiModel

data class CartUiState(
    val cartItems: List<DetailProductUiModel> = emptyList(),
    val page: Int = 1,
    val totalPage: Int = 1,
) {
    val isStartPage: Boolean = page == 1
    val isEndPage: Boolean = page >= totalPage
}
