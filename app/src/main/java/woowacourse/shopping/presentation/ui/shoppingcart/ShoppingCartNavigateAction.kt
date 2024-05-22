package woowacourse.shopping.presentation.ui.shoppingcart

sealed interface ShoppingCartNavigateAction {
    data class NavigateToProductList(val updatedProducts: UpdatedProducts) : ShoppingCartNavigateAction
}
