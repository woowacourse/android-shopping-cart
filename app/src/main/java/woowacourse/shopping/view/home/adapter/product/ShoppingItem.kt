package woowacourse.shopping.view.home.adapter.product

import woowacourse.shopping.data.db.cart.CartRepository.Companion.DEFAULT_QUANTITY
import woowacourse.shopping.domain.model.Product

sealed class ShoppingItem(open val viewType: Int) {
    data class ProductItem(
        val product: Product,
        private var _quantity: Int = DEFAULT_SHOPPING_QUANTITY,
        override val viewType: Int = PRODUCT_VIEW_TYPE,
    ) : ShoppingItem(viewType) {
        val quantity: Int
            get() = _quantity

        fun isQuantityControlVisible(): Boolean = quantity >= 1

        fun plusQuantity() {
            _quantity = this.quantity + 1
        }

        fun minusQuantity() {
            _quantity = (this.quantity - 1).coerceAtLeast(DEFAULT_QUANTITY)
        }

        companion object {
            private const val DEFAULT_SHOPPING_QUANTITY = 0
        }
    }

    data class LoadMoreItem(
        override val viewType: Int = LOAD_MORE_BUTTON_VIEW_TYPE,
    ) : ShoppingItem(viewType)

    companion object {
        const val PRODUCT_VIEW_TYPE = 0
        const val LOAD_MORE_BUTTON_VIEW_TYPE = 1
    }
}
