package woowacourse.shopping.data.repository

import woowacourse.shopping.data.db.cart.CartDatabase
import woowacourse.shopping.data.db.cart.CartItemEntity
import woowacourse.shopping.data.db.cart.toDomainModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.ShoppingCart
import woowacourse.shopping.domain.model.toEntity
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(database: CartDatabase) : CartRepository {
    private val dao = database.cartDao()

    override fun insert(
        product: Product,
        quantity: Int,
    ) {
        if (findOrNullWithProductId(product.id) != null) {
            update(product.id, quantity)
        } else {
            threadAction {
                dao.save(product.toEntity(quantity))
            }
        }
    }

    override fun update(
        productId: Long,
        quantity: Int,
    ) {
        val currentQuantity = findOrNullWithProductId(productId)?.quantity ?: 0
        threadAction {
            dao.update(productId, currentQuantity + quantity)
        }
    }

    override fun size(): Int {
        var size = 0
        threadAction {
            size = dao.size()
        }
        return size
    }

    override fun findOrNullWithProductId(productId: Long): CartItem? {
        var cartItemEntity: CartItemEntity? = null
        threadAction {
            cartItemEntity = dao.findWithProductId(productId)
        }

        return cartItemEntity?.toDomainModel()
    }

    override fun find(cartItemId: Long): CartItem {
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
