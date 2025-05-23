package woowacourse.shopping.data.cart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.CartDatabase
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val cartDatabase: CartDatabase,
) : CartRepository {
    override suspend fun getAll(): Carts {
        val cartList = cartDatabase.cartDao().getAll().map { it.toDomain() }
        val totalQuantity = cartList.sumOf { it.quantity }
        return Carts(carts = cartList, totalQuantity = totalQuantity)
    }

    override fun insert(cart: Cart) {
        thread {
            val dao = cartDatabase.cartDao()
            val existing = dao.findById(cart.goods.id)
            if (existing != null) {
                val updated =
                    existing.copy(
                        quantity = existing.quantity + 1,
                    )
                dao.update(updated)
            } else {
                dao.insertAndUpdateQuantity(cart.toEntity())
            }
        }
    }

    override fun insertAll(cart: Cart) {
        thread {
            val dao = cartDatabase.cartDao()
            dao.updateQuantity(cart.toEntity())
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
    ): LiveData<Carts> =
        cartDatabase.cartDao().getPage(limit, offset).map { entities ->
            val cartList = entities.map { it.toDomain() }
            val totalQuantity = cartList.sumOf { it.quantity }
            Carts(carts = cartList, totalQuantity = totalQuantity)
        }

    override fun getAllItemsSize(): LiveData<Int> = cartDatabase.cartDao().getAllItemsSize()

    override fun getTotalQuantity(callback: (Int) -> Unit) {
        thread {
            val totalQuantity = cartDatabase.cartDao().getTotalQuantity()
            callback(totalQuantity)
        }
    }
}
