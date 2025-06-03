package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.mapper.toCartEntity
import woowacourse.shopping.data.mapper.toCartItem
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override fun getCartItems(
        limit: Int,
        offset: Int,
        callback: (List<CartItem>, Boolean) -> Unit,
    ) {
        thread {
            val cartItems =
                cartDao
                    .getCartItemPaged(
                        limit = limit,
                        offset = offset,
                    ).map { it.toCartItem() }
            val hasMore =
                cartItems.lastOrNull()?.let { cartDao.existsItemAfterId(it.product.id) } ?: false
            callback(cartItems, hasMore)
        }
    }

    override fun deleteCartItem(
        id: Long,
        callback: (Long) -> Unit,
    ) {
        thread {
            cartDao.delete(id).let { callback(id) }
        }
    }

    override fun addCartItem(
        cartItem: CartItem,
        callback: () -> Unit,
    ) {
        thread {
            cartDao.upsert(cartItem.toCartEntity()).let { callback() }
        }
    }

    override fun increaseCartItem(
        productId: Long,
        callback: (CartItem?) -> Unit,
    ) {
        thread {
            cartDao.increaseAmount(productId)
            val item = cartDao.getByProductId(productId)
            if (item != null) {
                callback(item.toCartItem())
            }
        }
    }

    override fun decreaseCartItem(
        productId: Long,
        callback: (CartItem?) -> Unit,
    ) {
        thread {
            cartDao.decreaseAmountOrDelete(productId)

            val item = cartDao.getByProductId(productId)
            callback(item?.toCartItem())
        }
    }
}
