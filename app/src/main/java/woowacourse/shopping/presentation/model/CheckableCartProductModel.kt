package woowacourse.shopping.presentation.model

data class CheckableCartProductModel(
    override val cartId: Long,
    override val productModel: ProductModel,
    override val count: Int,
    val isChecked: Boolean,
) : CartProductModel
