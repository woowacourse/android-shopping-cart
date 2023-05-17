package woowacourse.shopping.presentation.model

data class CartProductModel(
    val productModel: ProductModel,
    val count: Int,
    val isSelected: Boolean,
)
