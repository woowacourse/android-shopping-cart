package woowacourse.shopping.domain.model

sealed interface CartItemResult {
    data class Exists(
        val cartItemId: Long,
        val counter: CartItemCounter,
    ) : CartItemResult

    data object NotExists : CartItemResult
}
