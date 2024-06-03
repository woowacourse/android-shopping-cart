package woowacourse.shopping.domain

sealed interface QuantityUpdate {
    data object CantChange : QuantityUpdate

    data class Success(val value: ShoppingCartItem) : QuantityUpdate
}
