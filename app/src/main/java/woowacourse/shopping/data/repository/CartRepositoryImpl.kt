package woowacourse.shopping.data.repository

import android.content.Context
import woowacourse.shopping.data.database.ShoppingDatabase
import woowacourse.shopping.data.mapper.toDomainModel
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.data.model.mapper
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingCart
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(context: Context) : CartRepository {
    private val dao = ShoppingDatabase.getInstance(context).cartDao()

    override fun insert(
        product: Product,
        quantity: Int,
    ) {
        if (findOrNullWithProductId(product.id) != null) {
            update(product.id, quantity)
        } else {
            threadAction {
                dao.save(product.mapper(quantity))
            }
        }
    }

    override fun update(
        productId: Long,
        quantity: Int,
    ) {
        threadAction {
            dao.update(productId, quantity)
        }
    }

    override fun updateQuantity(
        cartItemId: Long,
        quantity: Int,
    ) {
        threadAction {
            dao.updateQuantity(cartItemId, quantity)
        }
    }

    override fun updateQuantityWithProductId(
        productId: Long,
        quantity: Int,
    ) {
        threadAction {
            dao.updateQuantityWithProductId(productId, quantity)
        }
    }

    override fun findQuantityWithProductId(productId: Long): Int {
        var quantity = 0
        threadAction {
            quantity = dao.findQuantityWithProductId(productId)
        }
        return quantity
    }

    override fun size(): Int {
        var size: Int = 0
        threadAction {
            size = dao.size()
        }
        return size
    }

    override fun sumOfQuantity(): Int {
        var sum = 0
        threadAction {
            sum = dao.sumOfQuantity()
        }
        return sum
    }

    override fun findOrNullWithProductId(productId: Long): CartItem? {
        var cartItemEntity: CartItemEntity? = null
        threadAction {
            cartItemEntity = dao.findWithProductId(productId)
        }

        return cartItemEntity?.toDomainModel()
    }

    override fun findWithCartItemId(cartItemId: Long): CartItem {
        var cartItemEntity: CartItemEntity? = null
        threadAction {
            cartItemEntity = dao.find(cartItemId)
        }

        return cartItemEntity?.toDomainModel() ?: throw IllegalArgumentException("데이터가 존재하지 않습니다.")
    }

    override fun findAll(): ShoppingCart {
        var cartItems: List<CartItem> = emptyList()
        threadAction {
            cartItems = dao.findAll().map { it.toDomainModel() }
        }
        return ShoppingCart(cartItems)
    }

    override fun findAllPagedItems(
        page: Int,
        pageSize: Int,
    ): ShoppingCart {
        var cartItems: List<CartItem> = emptyList()
        val offset = page * pageSize

        threadAction {
            cartItems =
                dao.findAllPaged(offset = offset, limit = pageSize)
                    .map { it.toDomainModel() }
        }

        return ShoppingCart(cartItems)
    }

    override fun delete(cartItemId: Long) {
        threadAction {
            dao.delete(cartItemId)
        }
    }

    override fun deleteWithProductId(productId: Long) {
        threadAction {
            dao.deleteWithProductId(productId)
        }
    }

    override fun deleteAll() {
        threadAction {
            dao.deleteAll()
        }
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }
}
