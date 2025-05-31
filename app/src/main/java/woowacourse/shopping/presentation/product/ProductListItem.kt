package woowacourse.shopping.presentation.product

import woowacourse.shopping.domain.model.CartItem

sealed class ProductListItem {
    data class Product(
        val item: CartItem,
    ) : ProductListItem()

    data object LoadMore : ProductListItem()
}
