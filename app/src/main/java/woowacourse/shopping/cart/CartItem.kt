package woowacourse.shopping.cart

import woowacourse.shopping.product.catalog.ProductUiModel

sealed class CartItem {
    data class ProductItem(
        val productItem: ProductUiModel,
    ) : CartItem()

    data object PaginationButtonItem : CartItem()
}
