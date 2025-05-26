package woowacourse.shopping.repository

import woowacourse.shopping.model.products.CartState
import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.ShoppingCartItem

class CartRepositoryImpl : CartRepository {
    private var _cartState = CartState()

    override fun addProduct(product: Product): CartState {
        val existingItem = _cartState.items[product.id]
        _cartState =
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                _cartState.copy(items = _cartState.items + (product.id to updatedItem))
            } else {
                _cartState.copy(
                    items =
                        _cartState.items + (
                            product.id to
                                ShoppingCartItem(
                                    product,
                                    1,
                                )
                        ),
                )
            }
        return _cartState
    }

    override fun addProduct2(cart: ShoppingCartItem) {
        _cartState = _cartState.copy(items = _cartState.items + (cart.product.id to cart))
    }

    override fun getProductQuantity(productId: String): Int = _cartState.getQuantity(productId)

    override fun updateQuantity(
        productId: String,
        quantity: Int,
    ): CartState {
        _cartState =
            if (quantity <= 0) {
                _cartState.copy(items = _cartState.items - productId)
            } else {
                val item = _cartState.items[productId]
                if (item != null) {
                    val updatedItem = item.copy(quantity = quantity)
                    _cartState.copy(items = _cartState.items + (productId to updatedItem))
                } else {
                    _cartState
                }
            }
        return _cartState
    }

    override fun removeProduct(productId: String): CartState {
        _cartState = _cartState.copy(items = _cartState.items - productId)
        return _cartState
    }

    override fun getCurrentState(): CartState = _cartState

    override fun clear(): CartState {
        _cartState = CartState()
        return _cartState
    }

    companion object {
        @Volatile
        private var INSTANCE: CartRepositoryImpl? = null

        fun getInstance(): CartRepositoryImpl =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CartRepositoryImpl().also { INSTANCE = it }
            }
    }
}
