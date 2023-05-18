package woowacourse.shopping.presentation.model

data class CheckableCartProductModel(
    override val productModel: ProductModel,
    override val count: Int,
    val isChecked: Boolean,
) : CartProductModel
