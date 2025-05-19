package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.mapper.toProduct
import woowacourse.shopping.data.mapper.toProductEntity
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override fun getCartItems(
        limit: Int,
        offset: Int,
        callback: (List<Product>, Boolean) -> Unit,
    ) {
        thread {
            val products =
                cartDao
                    .getCartItemPaged(
                        limit = limit,
                        offset = offset,
                    ).map { it.toProduct() }
            val hasMore = products.lastOrNull()?.let { cartDao.existsItemAfterId(it.id) } ?: false
            callback(products, hasMore)
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
            cartDao.insert(product.toProductEntity()).let { callback() }
        }
    }
}
