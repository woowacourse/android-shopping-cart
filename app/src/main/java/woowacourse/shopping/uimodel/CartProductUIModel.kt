package woowacourse.shopping.uimodel

data class CartProductUIModel(
    val count: Int,
    val product: ProductUIModel,
) {
    companion object {
        val dummy = CartProductUIModel(1, ProductUIModel.dummy)
    }
}
