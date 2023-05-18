package woowacourse.shopping.model

data class CartProductUiModel(
    val product: ProductUiModel,
    val count: Int,
    val isSelected: Boolean,
)
