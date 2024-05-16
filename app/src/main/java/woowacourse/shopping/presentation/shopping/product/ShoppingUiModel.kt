package woowacourse.shopping.presentation.shopping.product


sealed class ShoppingUiModel(val viewType: Int) {
    data class Product(
        val id: Long,
        val name: String,
        val price: Int,
        val imageUrl: String,
    ) : ShoppingUiModel(ITEM_VIEW_TYPE_PRODUCT)

    data object Plus : ShoppingUiModel(ITEM_VIEW_TYPE_PLUS)

    companion object {
        const val ITEM_VIEW_TYPE_PRODUCT = 0
        const val ITEM_VIEW_TYPE_PLUS = 1
        const val PRODUCT_SPAN_COUNT = 1
        const val PLUS_SPAN_COUNT = 2
    }
}