package woowacourse.shopping.presentation.ui.productdetail

import woowacourse.shopping.presentation.ui.shoppingcart.UpdatedProducts

sealed interface ProductDetailNavigateAction {
    data class NavigateToProductList(val updatedProducts: UpdatedProducts) :
        ProductDetailNavigateAction

    data class NavigateToProductDetail(val productId: Long) : ProductDetailNavigateAction

    data object NavigateToPrevious : ProductDetailNavigateAction
}
