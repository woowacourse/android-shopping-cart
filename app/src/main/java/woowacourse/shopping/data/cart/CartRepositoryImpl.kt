import woowacourse.shopping.domain.cart.CartDataSource
import woowacourse.shopping.domain.cart.CartProduct
import woowacourse.shopping.domain.cart.CartRepository
import woowacourse.shopping.providers.ThreadProvider

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource
) : CartRepository {
    override fun fetchInRange(
        limit: Int,
        offset: Int,
        onResult: (Result<List<CartProduct>>) -> Unit
    ) {
        ThreadProvider.execute {
            runCatching {
                onResult(cartDataSource.findInRange(limit, offset))
            }
        }
    }

    override fun fetchByProductId(productId: Long, onResult: (Result<CartProduct?>) -> Unit) {
        ThreadProvider.execute {
            runCatching {
                cartDataSource.findByProductId(productId)
            }.mapCatching { cartProduct -> onResult(cartProduct) }
        }
    }

    override fun insert(productId: Long, quantity: Int, onResult: (Result<Long>) -> Unit) {
        ThreadProvider.execute {
            runCatching {
                onResult(cartDataSource.insertByProductId(productId, quantity))
            }
        }
    }

    override fun insertOrAddQuantity(
        productId: Long,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit
    ) {
        ThreadProvider.execute {
            runCatching {
                onResult(cartDataSource.insertOrAddQuantity(productId, quantity))
            }
        }
    }

    override fun updateQuantity(productId: Long, delta: Int, onResult: (Result<Unit>) -> Unit) {
        ThreadProvider.execute {
            runCatching {
                onResult(cartDataSource.updateQuantityByProductId(productId, delta))
            }
        }
    }

    override fun delete(cartItemId: Long, onResult: (Result<Unit>) -> Unit) {
        ThreadProvider.execute {
            runCatching {
                onResult(cartDataSource.deleteByCartItemId(cartItemId))
            }
        }
    }
}
