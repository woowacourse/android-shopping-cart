package woowacourse.shopping.ui.fashionlist

import woowacourse.shopping.domain.product.Product

sealed class ProductListViewType {
    data class FashionProductItemType(val product: Product) : ProductListViewType()

    data object LoadMoreType : ProductListViewType()
}
