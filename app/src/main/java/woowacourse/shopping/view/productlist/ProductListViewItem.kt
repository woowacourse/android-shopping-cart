package woowacourse.shopping.view.productlist

import woowacourse.shopping.model.ProductModel

sealed interface ProductListViewItem {
    val type: ProductListViewType
    data class RecentViewedItem(val products: List<ProductModel>) : ProductListViewItem {
        override val type = ProductListViewType.RECENT_VIEWED_ITEM
    }
    data class ProductItem(val product: ProductModel) : ProductListViewItem {
        override val type = ProductListViewType.PRODUCT_ITEM
    }
    class ShowMoreItem : ProductListViewItem {
        override val type = ProductListViewType.SHOW_MORE_ITEM
    }
}
