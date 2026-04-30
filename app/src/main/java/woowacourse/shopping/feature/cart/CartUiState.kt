package woowacourse.shopping.feature.cart

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.feature.cart.model.CartInfo

data class CartUiState(
    val cartItems: ImmutableList<CartInfo> = emptyList<CartInfo>().toImmutableList(),
    val displayPageNumber: Int = 1,
    val showControls: Boolean = false,
    val isFirstPage: Boolean = true,
    val isLastPage: Boolean = false,
)