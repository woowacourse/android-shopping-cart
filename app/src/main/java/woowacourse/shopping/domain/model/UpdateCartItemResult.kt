package woowacourse.shopping.domain.model

sealed interface UpdateCartItemResult {
    data class UPDATED(val cartItemResult: CartItemResult) : UpdateCartItemResult

    data class DELETE(val cartItemId: Long) : UpdateCartItemResult

    data object ADD : UpdateCartItemResult
}
