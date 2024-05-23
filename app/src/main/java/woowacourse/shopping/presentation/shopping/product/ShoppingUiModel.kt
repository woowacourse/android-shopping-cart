package woowacourse.shopping.presentation.shopping.product

sealed class ShoppingUiModel(val viewType: Int) {
    data class Product(
        val id: Long,
        val name: String,
        val price: Int,
        val imageUrl: String,
        val count: Int = 0,
    ) : ShoppingUiModel(ITEM_VIEW_TYPE_PRODUCT) {
        val isVisible: Boolean
            get() = count > 0
    }

    data object LoadMore : ShoppingUiModel(ITEM_VIEW_TYPE_PLUS)

    companion object {
        const val ITEM_VIEW_TYPE_PRODUCT = 1
        const val ITEM_VIEW_TYPE_PLUS = 2
        const val PRODUCT_SPAN_COUNT = 1
        const val PLUS_SPAN_COUNT = 2
    }
}
