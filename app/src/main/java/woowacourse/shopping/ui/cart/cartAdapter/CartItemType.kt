package woowacourse.shopping.ui.cart.cartAdapter

import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel

sealed class CartItemType {
    val viewType: Int
        get() = when (this) {
            is Cart -> TYPE_ITEM
            is Navigation -> TYPE_FOOTER
        }

    data class Cart(val product: ProductUIModel) : CartItemType()

    data class Navigation(val cart: CartUIModel) : CartItemType()

    companion object {
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 2
    }
}
