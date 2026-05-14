package woowacourse.shopping.ui.productlist.state

sealed interface ProductListUiEvent {
    data class ShowToast(val message: String) : ProductListUiEvent
}
