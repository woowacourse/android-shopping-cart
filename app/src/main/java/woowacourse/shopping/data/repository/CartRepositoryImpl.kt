package woowacourse.shopping.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import woowacourse.shopping.data.CartDatabase
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.model.Cart
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartDatabase: CartDatabase,
) : CartRepository {
    override suspend fun getAll(): List<Cart> = cartDatabase.cartDao().getAll().map { it.toDomain() }

    override fun insert(cart: Cart) {
        thread {
            val dao = cartDatabase.cartDao()
            val existing = dao.findById(cart.goods.id)
            if (existing != null) {
                val updated =
                    existing.copy(
                        quantity = existing.quantity + 1,
                        price = existing.price + cart.goods.price,
                    )
                dao.update(updated)
            } else {
                dao.insertAndUpdateQuantity(cart.toEntity())
            }
        }
    }

    override fun delete(cart: Cart) {
        thread {
            val dao = cartDatabase.cartDao()
            val existing = dao.findById(cart.goods.id)
            if (existing != null) {
                if (existing.quantity > 1) {
                    val updated =
                        existing.copy(
                            quantity = existing.quantity - 1,
                            price = existing.price - cart.goods.price,
                        )
                    dao.update(updated)
                } else {
                    dao.delete(cart.toEntity())
                }
            }
        }
    }

    override fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<List<Cart>> =
        cartDatabase.cartDao().getPage(limit, offset).map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }

    override fun getAllItemsSize(): LiveData<Int> = cartDatabase.cartDao().getAllItemsSize()
}
