package woowacourse.shopping.shopping

import woowacourse.shopping.model.ProductUIModel

sealed interface ProductsItemType {
    companion object {
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 2
    }
}

data class ProductItem(val product: ProductUIModel) : ProductsItemType
object ProductReadMore : ProductsItemType
