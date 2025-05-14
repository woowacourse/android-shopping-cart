package woowacourse.shopping.ui

import woowacourse.shopping.model.Product

sealed interface ProductItem {
    val viewType: Int
        get() = when (this) {
            is ProductType -> PRODUCT_TYPE
            is ReadMoreType -> READ_MORE_TYPE
        }

    data class ProductType(val product: Product) : ProductItem
    data object ReadMoreType : ProductItem

    companion object {
        const val PRODUCT_TYPE = 0
        const val READ_MORE_TYPE = 1
    }
}
