package woowacourse.shopping.data.db.cart

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.toCartItemEntity

class CartRepositoryImpl(database: CartDatabase) : CartRepository {
    private val dao = database.cartDao()

    override fun save(
        product: Product,
        quantity: Int,
    ) {
        if (findOrNullByProductId(product.id) != null) {
            update(product.id, quantity)
        } else {
            threadAction {
                dao.save(product.toCartItemEntity(quantity))
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

    override fun cartItemSize(): Int {
        var itemSize = 0
        threadAction {
            itemSize = dao.cartItemSize()
        }
        return itemSize
    }

    override fun productQuantity(productId: Long): Int {
        var productQuantity = 0
        threadAction {
            productQuantity = dao.productQuantity(productId)
        }
        return productQuantity
    }

    override fun findOrNullByProductId(productId: Long): CartItem? {
        var cartItemEntity: CartItemEntity? = null
        threadAction {
            cartItemEntity = dao.findByProductId(productId)
        }

        return cartItemEntity?.toCartItem()
    }

    override fun find(cartItemId: Long): CartItem {
        var cartItemEntity: CartItemEntity? = null
        threadAction {
            cartItemEntity = dao.find(cartItemId)
        }

        return cartItemEntity?.toCartItem() ?: throw IllegalArgumentException("데이터가 존재하지 않습니다.")
    }

    override fun findAll(): List<CartItem> {
        var cartItems: List<CartItem> = emptyList()
        threadAction {
            cartItems = dao.findAll().map { it.toCartItem() }
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
                    .map { it.toCartItem() }
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
