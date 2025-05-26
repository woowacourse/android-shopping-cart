package woowacourse.shopping.data.cart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import woowacourse.shopping.data.cart.CartDao
import woowacourse.shopping.data.toDomain
import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts
import kotlin.concurrent.thread

class CartRepositoryImpl(
    private val dao: CartDao,
) : CartRepository {
    override fun getAll(callback: (Carts) -> Unit) {
        thread {
            val cartList = dao.getAll().map { it.toDomain() }
            val totalQuantity = cartList.sumOf { it.quantity }
            callback(Carts(carts = cartList, totalQuantity = totalQuantity))
        }
    }

    override fun insert(cart: Cart) {
        thread {
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
            dao.updateQuantity(cart.toEntity())
        }
    }

    override fun delete(cart: Cart) {
        thread {
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

    override fun deleteAll(cart: Cart) {
        thread {
            dao.delete(cart.toEntity())
        }
    }

    override fun getPage(
        limit: Int,
        offset: Int,
    ): LiveData<Carts> =
        dao.getPage(limit, offset).map { entities ->
            val cartList = entities.map { it.toDomain() }
            val totalQuantity = cartList.sumOf { it.quantity }
            Carts(carts = cartList, totalQuantity = totalQuantity)
        }

    override fun getAllItemsSize(callback: (Int) -> Unit) {
        thread {
            val itemsSize = dao.getAllItemsSize()
            callback(itemsSize)
        }
    }

    override fun getTotalQuantity(callback: (Int) -> Unit) {
        thread {
            val totalQuantity = dao.getTotalQuantity()
            callback(totalQuantity)
        }
    }
}
