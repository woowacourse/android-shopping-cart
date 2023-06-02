package woowacourse.shopping.uimodel

data class CartProductUIModel(
    val isPicked: Boolean,
    val count: Int,
    val product: ProductUIModel,
) {
    companion object {
        val dummy = CartProductUIModel(true, 1, ProductUIModel.dummy)
    }
}
