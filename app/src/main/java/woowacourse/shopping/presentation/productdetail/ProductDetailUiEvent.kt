package woowacourse.shopping.presentation.productdetail

sealed interface ProductDetailUiEvent {
    data class ShowMessage(
        val message: String,
    ) : ProductDetailUiEvent
}
