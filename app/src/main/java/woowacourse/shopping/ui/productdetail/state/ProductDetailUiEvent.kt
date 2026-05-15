package woowacourse.shopping.ui.productdetail.state

sealed interface ProductDetailUiEvent {
    data class ShowToast(val message: String) : ProductDetailUiEvent
    object CartAddSuccess : ProductDetailUiEvent
}
