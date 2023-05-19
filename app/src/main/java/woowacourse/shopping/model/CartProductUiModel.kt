package woowacourse.shopping.model

data class CartProductUiModel(
    val product: ProductUiModel,
    val count: Int,
    var isSelected: Boolean,
)
