package woowacourse.shopping.view.detail

import woowacourse.shopping.view.cart.ShoppingCartEvent

sealed interface ProductDetailEvent {
    sealed interface ErrorEvent : ProductDetailEvent {
        data object NotKnownError : ErrorEvent
    }

    sealed interface SuccessEvent : ProductDetailEvent

    sealed interface AddShoppingCart : ProductDetailEvent {
        data class Success(val productId: Long, val count: Int) : AddShoppingCart, SuccessEvent

        data object Fail : AddShoppingCart, ErrorEvent
    }

    sealed interface LoadProductItem : ProductDetailEvent {
        data object Success : LoadProductItem

        data object Fail : LoadProductItem, ErrorEvent
    }

    sealed interface LoadRecentlyProductItem: ProductDetailEvent {
        data object Success: LoadRecentlyProductItem

        data object Fail : LoadRecentlyProductItem,ErrorEvent
    }
}
