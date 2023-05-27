package woowacourse.shopping.presentation.model

sealed interface ProductViewType {

    val number: Int

    data class ProductItem(
        val cartProductModel: CartProductModel,
    ) : ProductViewType {
        override val number: Int
            get() = PRODUCT_VIEW_TYPE_NUMBER
    }

    data class RecentProductModels(
        val recentProducts: List<ProductModel>,
    ) : ProductViewType {
        override val number: Int
            get() = RECENT_PRODUCTS_VIEW_TYPE_NUMBER
    }

    object MoreItem : ProductViewType {
        override val number: Int
            get() = MORE_ITEM_VIEW_TYPE_NUMBER
    }

    companion object {
        const val RECENT_PRODUCTS_VIEW_TYPE_NUMBER = 0
        const val PRODUCT_VIEW_TYPE_NUMBER = 1
        const val MORE_ITEM_VIEW_TYPE_NUMBER = 2
    }
}
