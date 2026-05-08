package woowacourse.shopping.presentation.cart

import woowacourse.shopping.domain.model.cart.Cart
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class CartUiState(
    val cart: Cart = Cart(),
    val currentPageIndex: Int = 0,
    val deleteProductId: Uuid? = null,
) {
    val currentPage: Int
        get() = currentPageIndex + 1
}
