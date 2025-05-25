package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dao.CartDao
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.entity.CartEntity
import woowacourse.shopping.data.entity.toDomain
import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product
import android.os.Handler
import android.os.Looper


class CartRepositoryImpl(
    private val cartDao: CartDao,
    private val productDao: ProductDao
) : CartRepository {

    private val loadItemCount = 5
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun fetchCartProducts(page: Int, callback: (List<CartProduct>) -> Unit) {
        Thread {
            val offset = (page - 1) * loadItemCount
            val result = cartDao.fetchPagedCart(loadItemCount, offset)
                .mapNotNull { it.toDomain(productDao) }

            mainHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun fetchAllProduct(callback: (List<CartProduct>) -> Unit) {
        Thread {
            val result = cartDao.getAll().mapNotNull { it.toDomain(productDao) }
            mainHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun fetchMaxPageCount(callback: (Int) -> Unit) {
        Thread {
            val total = cartDao.getTotalCount()
            val pageCount = (total + loadItemCount - 1) / loadItemCount
            mainHandler.post {
                callback(pageCount)
            }
        }.start()
    }

    override fun upsertCartProduct(product: Product, count: Int) {
        Thread {
            val existing = cartDao.getAll().find { it.productId == product.id.toLong() }
            val newCount = (existing?.count ?: 0) + count

            if (newCount >= 1) {
                cartDao.upsert(CartEntity(productId = product.id.toLong(), count = newCount))
            } else {
                cartDao.deleteById(product.id)
            }
        }.start()
    }

    override fun removeCartProduct(id: Int) {
        Thread {
            cartDao.deleteById(id)
        }.start()
    }

    override fun clearCart() {
        Thread {
            cartDao.clear()
        }.start()
    }
}