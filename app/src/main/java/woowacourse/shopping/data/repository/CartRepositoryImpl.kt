package woowacourse.shopping.data.repository

import woowacourse.shopping.data.db.cart.CartDatabase
import woowacourse.shopping.data.db.cart.CartItemEntity
import woowacourse.shopping.data.db.cart.toDomainModel
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
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
        threadAction {
            dao.update(productId, quantity)
        }
    }

    override fun itemCount(): Int {
        var itemSize = 0
        threadAction {
            itemSize = dao.itemSize()
        }
        return itemSize
    }

    override fun totalQuantity(): Int {
        var totalQuantity = 0
        threadAction {
            totalQuantity = dao.totalQuantity()
        }
        return totalQuantity
    }

    override fun productQuantity(productId: Long): Int {
        var productQuantity = 0
        threadAction {
            productQuantity = dao.productQuantity(productId)
        }
        return productQuantity
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

    override fun findAll(): List<CartItem> {
        var cartItems: List<CartItem> = emptyList()
        threadAction {
            cartItems = dao.findAll().map { it.toDomainModel() }
        }
        return cartItems
    }

    override fun findAllPagedItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        var cartItems: List<CartItem> = emptyList()
        val offset = page * pageSize

        threadAction {
            cartItems =
                dao.findAllPaged(offset = offset, limit = pageSize)
                    .map { it.toDomainModel() }
        }

        return cartItems
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
