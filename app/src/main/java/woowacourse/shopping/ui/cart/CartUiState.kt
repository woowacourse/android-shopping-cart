package woowacourse.shopping.ui.cart

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import woowacourse.shopping.ui.model.CartItemUiModel

data class CartUiState(
    val items: ImmutableList<CartItemUiModel> = persistentListOf(),
    val page: Int = 0,
    val isCanMoveNext: Boolean = false,
    val totalCartSize: Int = 0,
    val totalPrice: Int = 0,
    val errorMessage: String? = null,
)
