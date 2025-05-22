package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.mapper.toCartItem
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class ProductRepositoryImpl(
    private val products: List<Product>,
    private val cartDao: CartDao,
) : ProductRepository {
    override fun loadProducts(
        lastItemId: Long,
        loadSize: Int,
        callback: (List<Product>, Boolean) -> Unit,
    ) {
        val products = products.filter { it.id > lastItemId }.take(loadSize)
        val lastId = products.lastOrNull()?.id ?: return callback(products, false)

        val hasMore = this.products.any { it.id > lastId }

        callback(products, hasMore)
    }

    override fun loadCartItems(callback: (List<CartItem>?) -> Unit) {
        thread {
            val cartItems = cartDao.getCartItems().map { it.toCartItem() }
            callback(cartItems)
        }
    }
}
