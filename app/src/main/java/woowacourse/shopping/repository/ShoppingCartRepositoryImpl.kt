package woowacourse.shopping.repository

import woowacourse.shopping.data.ShoppingCartDataSource
import woowacourse.shopping.data.ShoppingCartEntity
import woowacourse.shopping.model.products.CartState

class ShoppingCartRepositoryImpl(
    private val shoppingCartDataSource: ShoppingCartDataSource,
) : ShoppingCartRepository {
    override fun addCart(
        productId: Int,
        quantity: Int,
    ) {
        shoppingCartDataSource.upsertCartItem(
            ShoppingCartEntity(
                productId = productId,
                quantity = quantity,
            ),
        )
    }

    override fun getProductQuantity(productId: Int): Int = _cartState.getQuantity(productId)

    override fun updateQuantity(
        productId: Int,
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

    override fun removeProduct(productId: Int): CartState {
        _cartState = _cartState.copy(items = _cartState.items - productId)
        return _cartState
    }

    override fun getCurrentState(): CartState = _cartState

    override fun clear(): CartState {
        _cartState = CartState()
        return _cartState
    }

//    companion object {
//        @Volatile
//        private var INSTANCE: CartRepositoryImpl? = null
//
//        fun getInstance(): CartRepositoryImpl =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: CartRepositoryImpl().also { INSTANCE = it }
//            }
//    }
}
