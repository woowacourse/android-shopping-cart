package woowacourse.shopping.model

typealias UiBasketProduct = BasketProduct

data class BasketProduct(
    val id: Int,
    val product: UiProduct,
    val selectedCount: UiProductCount = UiProductCount(0),
) {
    val shouldShowCounter: Boolean
        get() = selectedCount.value > 0
}
