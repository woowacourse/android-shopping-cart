package woowacourse.shopping.presentation.ui.productlist

sealed interface ProductListNavigateAction {
    data class NavigateToProductDetail(val productId: Int) : ProductListNavigateAction
}
