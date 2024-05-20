package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.domain.model.Product

sealed class ShoppingItem {
    data class ProductType(val product: Product) : ShoppingItem()

    data object LoadMoreType : ShoppingItem()
}
