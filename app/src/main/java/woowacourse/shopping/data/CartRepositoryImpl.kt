package woowacourse.shopping.data

import woowacourse.shopping.data.mapper.toCartItemEntity
import woowacourse.shopping.data.mapper.toDomainModel
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.ShoppingCart
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(database: CartDatabase) : CartRepository {
    private val dao = database.cartDao()

    override fun insert(productWithQuantity: ProductWithQuantity) {
        if (findOrNullWithProductId(productWithQuantity.product.id) != null) {
            plusQuantity(productWithQuantity.product.id, productWithQuantity.quantity)
        } else {
            threadAction {
                dao.save(productWithQuantity.toCartItemEntity())
            }
        }
    }

    override fun getQuantityByProductId(productId: Long): Int? {
        var quantity: Int? = null
        threadAction {
            quantity = dao.getQuantityByProductId(productId)
        }
        return quantity
    }

    override fun plusQuantity(
        productId: Long,
        quantity: Int,
    ) {
        val currentQuantity = findOrNullWithProductId(productId)?.quantity ?: 0
        threadAction {
            dao.updateQuantity(productId, currentQuantity + quantity)
        }
    }

    override fun minusQuantity(
        productId: Long,
        quantity: Int,
    ) {
        val currentQuantity = findOrNullWithProductId(productId)?.quantity ?: 0
        if (currentQuantity - quantity <= 0) {
            delete(productId)
        } else {
            threadAction {
                dao.updateQuantity(productId, currentQuantity - quantity)
            }
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

    override fun sumQuantity(): Int {
        var sum = 0
        threadAction {
            sum = dao.sumQuantity()
        }
        return sum
    }

    override fun findCartItemsByPage(
        page: Int,
        pageSize: Int,
    ): ShoppingCart {
        var cartItems: List<CartItem> = emptyList()
        val offset = page * pageSize

        threadAction {
            cartItems =
                dao.findByPaged(offset = offset, limit = pageSize)
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
