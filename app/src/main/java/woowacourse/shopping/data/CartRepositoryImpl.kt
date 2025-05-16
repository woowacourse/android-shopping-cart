package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.data.mapper.toCartItem
import woowacourse.shopping.data.mapper.toProductEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
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
            val products = cartDao.getCartItemPaged(limit, offset)
            callback(products.map { it.toCartItem() }, products.isHasMore())
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
        product: Product,
        callback: () -> Unit,
    ) {
        thread {
            cartDao.insertOrUpdate(product.toProductEntity()).let { callback() }
        }
    }

    private fun List<CartEntity>.isHasMore(): Boolean {
        val lastCreatedAt = this.lastOrNull()?.createdAt
        return lastCreatedAt != null && cartDao.existsItemCreatedBefore(lastCreatedAt)
    }
}
