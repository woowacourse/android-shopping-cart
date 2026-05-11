package woowacourse.shopping.presentation.shopping

sealed interface ProductListUiEvent {
    data class ShowMessage(
        val message: String,
    ) : ProductListUiEvent
}
