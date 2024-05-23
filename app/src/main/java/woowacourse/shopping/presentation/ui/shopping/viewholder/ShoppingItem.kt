package woowacourse.shopping.presentation.ui.shopping.viewholder

import woowacourse.shopping.domain.model.ProductWithQuantity

sealed class ShoppingItem {
    data class ProductType(val productWithQuantity: ProductWithQuantity) : ShoppingItem()

    data object LoadMoreType : ShoppingItem()
}
