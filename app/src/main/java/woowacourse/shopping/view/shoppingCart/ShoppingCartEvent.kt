package woowacourse.shopping.view.shoppingCart

import woowacourse.shopping.domain.product.Product

sealed interface ShoppingCartEvent {
    data object CartRemovedFailed : ShoppingCartEvent

    data object CartFetchFailed : ShoppingCartEvent

    data object CartIncreasingFailed : ShoppingCartEvent

    data object CartDecreasingFailed : ShoppingCartEvent

    data class UpdatedProductRequested(
        val products: Array<Product>,
    ) : ShoppingCartEvent
}
