package woowacourse.shopping.model

data class CartProductUiModel(
    val product: ProductUiModel,
    var count: Int,
    var isSelected: Boolean,
)
