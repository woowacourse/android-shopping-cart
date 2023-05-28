package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.ProductUIModel

sealed interface ProductsItemType {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 2
    }
}

data class RecentProductsItem(val product: List<ProductUIModel>) : ProductsItemType
data class ProductItem(val product: ProductUIModel, val count: Int) : ProductsItemType
object ProductReadMore : ProductsItemType
