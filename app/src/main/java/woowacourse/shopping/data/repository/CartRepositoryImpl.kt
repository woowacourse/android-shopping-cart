package woowacourse.shopping.data.repository

import woowacourse.shopping.data.database.CartDatabase
import woowacourse.shopping.data.mapper.toCartItemEntity
import woowacourse.shopping.data.mapper.toDomainModel
import woowacourse.shopping.data.mapper.toNewCartItemEntity
import woowacourse.shopping.data.model.CartItemEntity
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.ProductWithQuantity
import woowacourse.shopping.domain.model.ShoppingCart
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(database: CartDatabase) : CartRepository {
    private val dao = database.cartDao()

    override fun insert(productWithQuantity: ProductWithQuantity) {
        threadAction {
            if (productWithQuantity.quantity > 0) {
                dao.insert(productWithQuantity.toCartItemEntity())
            } else {
                dao.insert(productWithQuantity.toNewCartItemEntity())
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
        threadAction {
            val currentQuantity = findWithProductId(productId).quantity
            dao.updateQuantity(productId, currentQuantity + quantity)
        }
    }

    override fun minusQuantity(
        productId: Long,
        quantity: Int,
    ) {
        threadAction {
            val currentQuantity = findWithProductId(productId).quantity
            if (currentQuantity - quantity <= 0) {
                dao.deleteByProductId(productId)
            } else {
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

    override fun findWithProductId(productId: Long): CartItem {
        var cartItemEntity: CartItemEntity? = null
        threadAction {
            cartItemEntity = dao.findWithProductId(productId)
        }
        return cartItemEntity?.toDomainModel() ?: throw Exception(PRODUCT_NOT_FOUND)
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
            cartItems = dao.findByPaged(offset = offset, limit = pageSize).map { it.toDomainModel() }
        }
        return ShoppingCart(cartItems)
    }

    override fun deleteByProductId(productId: Long) {
        threadAction {
            dao.deleteByProductId(productId)
        }
    }

    private fun threadAction(action: () -> Unit) {
        val thread = Thread(action)
        thread.start()
        thread.join()
    }

    companion object {
        private const val PRODUCT_NOT_FOUND = "해당 상품이 카트에 존재하지 않습니다."
    }
}
