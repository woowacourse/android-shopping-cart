package woowacourse.shopping.model

data class CartProductUIModel(
    val product: ProductUIModel,
    val count: Int,
    val isChecked: Boolean = true,
)
