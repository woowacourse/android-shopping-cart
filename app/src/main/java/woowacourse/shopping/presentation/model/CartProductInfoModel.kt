package woowacourse.shopping.presentation.model

data class CartProductInfoModel(
    val productModel: ProductModel,
    val count: Int,
    val isOrdered: Boolean = false,
)
