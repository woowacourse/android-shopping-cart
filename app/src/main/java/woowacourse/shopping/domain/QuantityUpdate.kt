package woowacourse.shopping.domain

sealed interface QuantityUpdate {
    data object Failure : QuantityUpdate

    data class Success(val value: ShoppingCartItem) : QuantityUpdate
}
