package woowacourse.shopping.model

typealias UiCartProduct = CartProduct

data class CartProduct(
    val id: Int,
    val product: UiProduct,
    val selectedCount: UiProductCount = UiProductCount(0),
    val isChecked: Boolean,
) {
    val shouldShowCounter: Boolean
        get() = selectedCount.value > 0
}
