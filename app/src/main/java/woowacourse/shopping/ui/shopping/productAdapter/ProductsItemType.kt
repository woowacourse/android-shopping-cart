package woowacourse.shopping.ui.shopping.productAdapter

import woowacourse.shopping.model.ProductUIModel

sealed interface ProductsItemType {
    val viewType: Int
        get() = when (this) {
            is RecentProducts -> TYPE_HEADER
            is Product -> TYPE_ITEM
            is ReadMore -> TYPE_FOOTER
        }

    data class RecentProducts(val product: List<ProductUIModel>) : ProductsItemType
    data class Product(val product: ProductUIModel) : ProductsItemType
    object ReadMore : ProductsItemType

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 2
    }
}
