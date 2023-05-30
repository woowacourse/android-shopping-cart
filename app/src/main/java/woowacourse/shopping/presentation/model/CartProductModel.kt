package woowacourse.shopping.presentation.model

data class CartProductModel(
    val product: ProductModel,
    val count: Int,
    val isChecked: Boolean
)
