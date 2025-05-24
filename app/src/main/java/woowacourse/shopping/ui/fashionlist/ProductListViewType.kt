package woowacourse.shopping.ui.fashionlist

import woowacourse.shopping.domain.product.Product

sealed class ProductListViewType {
    data class FashionProductItemType(val product: Product,
                                      val isButtonVisible: Boolean = true,
                                      val quantity: Int = 0) : ProductListViewType()

    data object LoadMoreType : ProductListViewType()
}
