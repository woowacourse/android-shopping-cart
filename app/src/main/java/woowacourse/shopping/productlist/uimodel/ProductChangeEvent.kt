package woowacourse.shopping.productlist.uimodel

sealed interface ProductChangeEvent {
    val result: List<ProductUiModel>

    data class AddProducts(
        override val result: List<ProductUiModel>,
    ) : ProductChangeEvent

    data class ChangeItemCount(
        override val result: List<ProductUiModel>,
    ) : ProductChangeEvent
}
