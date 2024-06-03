package woowacourse.shopping.productlist.uimodel

sealed interface ProductChangeEvent {
    data class AddProducts(
        val result: List<ProductUiModel>,
    ) : ProductChangeEvent

    data class ChangeItemCount(
        val result: List<ProductUiModel>,
    ) : ProductChangeEvent

    data object PlusFail : ProductChangeEvent
}
